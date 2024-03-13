import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import { FetchLeagues } from "./LeaguesHttpRequest";
import { Col, FloatingLabel, Form } from "react-bootstrap";
import LeaguesCard from "./LeaguesCard";
import { useLocation } from "react-router-dom";
import { countries } from "../../constants/countries";

const Leagues = () => {

    const { state } = useLocation();
    const searchData = state != null ? state.searchData : "";
    const [show, setShow] = useState(false);
    const [leagues, setLeagues] = useState([]);
    const [values, setValues] = useState({
        country: "",
        sport: ""
    })

    React.useEffect(() => {
        if (searchData != "") {
            fetchLeagues(searchData);
        }
    }, [searchData, state]);

    // Handle search object state
    const handleChange = (prop) => (event) => {
        setValues({ ...values, [prop]: event.target.value });
    }

    const onSubmitFetchLeagues = async (event) => {
        event.preventDefault();
        fetchLeagues(values);
    }

    const fetchLeagues = async (data) => {
        try {
            let leagues_response = await FetchLeagues(data);
            if (leagues_response.data.countries == null) {
                window.alert("No matching leagues found for the given query");
            } else {
                setLeagues(leagues_response.data.countries);
                setShow(true);
                window.alert("Leagues fetched successfully");
            }
        } catch (error) {
            console.log('error', error);
        }
    }

    return (<React.Fragment>
        <Container className="w-50 mb-4 py-4 bg-white border border-2 rounded-4 shadow">
            <p className="h4 fw-lighter mb-4 text-center">Search for a league</p>
            <div>
                <Form onSubmit={onSubmitFetchLeagues}>
                    <Container>
                        <Row xs={1} md={3} lg={3}>
                            <Col className="mb-2">
                                <FloatingLabel className="fw-lighter" controlId="formBasicEmail" label="Country">
                                    <Form.Control className="fw-lighter"
                                        type="text" placeholder="Country"
                                        onChange={handleChange("country")}
                                        required list="country_data" autoComplete="off" />
                                    <datalist id="country_data" className="">
                                        {countries.map((country) =>
                                            <option key={country.country_name} value={country.country_name} />
                                        )}
                                    </datalist>
                                </FloatingLabel>
                            </Col>
                            <Col className="mb-2">
                                <FloatingLabel className="fw-lighter" controlId="formBasicEmail" label="Sport">
                                    <Form.Control className="fw-lighter"
                                        type="text" placeholder="Sport"
                                        onChange={handleChange("sport")} />
                                </FloatingLabel>
                            </Col>
                            <Col className="mb-2 d-grid">
                                <Button className="fw-lighter rounded-5" variant="outline-dark" type="submit">
                                    Search
                                </Button>
                            </Col>
                        </Row>
                    </Container>
                </Form>
            </div>
        </Container>
        {
            show &&
            <Container className="mb-5 p-4 bg-white border border-2 rounded-4 shadow" show={show}>
                <p className="h3 fw-lighter mb-4 text-center">Leagues</p>
                <div>
                    <Row>
                        {leagues.map(league => (
                            <LeaguesCard key={league.idLeague} league={league} />
                        ))}
                    </Row>
                </div>
            </Container>
        }

    </React.Fragment >)
}

export default Leagues;