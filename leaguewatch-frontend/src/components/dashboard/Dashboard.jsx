import { useEffect, useState } from "react";
import { DeleteFromWishlist, FetchWishlist } from "./DashboardHttpRequest";
import { LinkContainer } from "react-router-bootstrap";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import WishlistCard from "./WishlistCard";
import UserDetails from "../user/UserDetails";
import { FloatingLabel, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { countries } from "../../constants/countries";
import { popularLeagues } from "../../constants/popular-leagues";
import LeaguesCard from "../leagues/LeaguesCard";

const Dashboard = () => {

    const navigate = useNavigate();
    const userId = localStorage.getItem("id");
    const [wishlist, setWishlist] = useState([]);
    const [searchData, setSearchData] = useState({
        country: "",
        sport: ""
    })

    useEffect(() => {
        const onLoadFetchWishlist = async () => {
            try {
                let wishlist_response = await FetchWishlist(userId);
                setWishlist(wishlist_response.data);
            } catch (error) {
                console.log('error', error);
            }
        }
        onLoadFetchWishlist();
    }, [userId, wishlist.length]);



    // Handle login object state
    const handleChange = (prop) => (event) => {
        setSearchData({ ...searchData, [prop]: event.target.value });
    }

    // Go to /leagues from dashboard
    const onSubmitGoToLeagues = async (event) => {
        event.preventDefault();
        try {
            navigate("../leagues", { state: { searchData } });
        } catch (error) {
            console.log('error', error);
        }
    }

    const onClickDeleteFromWishlist = async (idLeague) => {
        try {
            if (window.confirm('Delete this item from your wishlist?')) {
                let delete_wishlist_response = await DeleteFromWishlist({ userId, idLeague });
                window.alert(delete_wishlist_response.message);
                setWishlist(wishlist.filter((league) => league.idLeague !== idLeague));
            }
        } catch (error) {
            console.log('error', error);
        }
    }

    return (<Container fluid className="px-5">
        <Row>
            <Col className="" lg={3}>
                <UserDetails />
                <Container className="mb-4 py-3 bg-white border border-2 rounded-4 shadow">
                    <p className="h5 fw-lighter mb-4 text-center">Search</p>
                    <Form onSubmit={onSubmitGoToLeagues}>
                        <div className="d-grid gap-2">
                            <Col className="mb-2">
                                <FloatingLabel className="fw-lighter" controlId="formBasicCountry" label="Country">
                                    <Form.Control className="fw-lighter"
                                        type="text" placeholder="Country" size="lg"
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
                                <FloatingLabel className="fw-lighter" controlId="formBasicSport" label="Sport">
                                    <Form.Control className="fw-lighter"
                                        type="text" placeholder="Sport" size="lg"
                                        onChange={handleChange("sport")} />
                                </FloatingLabel>
                            </Col>
                            <Col className="d-grid">
                                <Button className="fw-lighter px-4 rounded-5"
                                    variant="outline-dark" size="lg"
                                    type="submit">
                                    Search
                                </Button>
                            </Col>
                        </div>
                    </Form>
                </Container>
            </Col>
            <Col lg={9}> {wishlist.length == 0 ?
                <>
                    <Container className="mb-4 p-4 bg-white border border-2 rounded-4 shadow">
                        <div>
                            <p className="lead text-center">Uh oh! Your wishlist seems to be empty.</p>
                            <p className="lead text-center">Click on the button below to search for a league.</p>
                            <br />
                            <Container className="w-50">
                                <div className="d-grid">
                                    <LinkContainer to="../leagues">
                                        <Button className="fw-lighter px-4 rounded-5" variant="outline-dark" size="lg">Search for a league</Button>
                                    </LinkContainer>
                                </div>
                            </Container>
                        </div>
                    </Container>
                    <Container className="mb-5 p-4 bg-white border border-2 rounded-4 shadow content-scrollable">
                        <p className="lead mb-4 text-center">Browse from our list of some popular leagues</p>
                        <div>
                            <Row>
                                {popularLeagues.map(league => (
                                    <LeaguesCard key={league.idLeague} league={league}/>
                                ))}
                            </Row>
                        </div>
                    </Container>
                </>
                :
                <>
                    <Container className="mb-5 p-4 bg-white border border-2 rounded-4 shadow content-scrollable">
                        <p className="h3 fw-lighter mb-4 text-center">Your wishlist</p>
                        <div>
                            <Row>
                                {wishlist.map(league => (
                                    <WishlistCard key={league.idLeague} league={league} onClickDeleteFromWishlist={onClickDeleteFromWishlist} />
                                ))}
                            </Row>
                        </div>
                    </Container>
                    <Container className="mb-5 p-4 bg-white border border-2 rounded-4 shadow content-scrollable">
                        <p className="lead mb-4 text-center">Browse from our list of some popular leagues</p>
                        <div>
                            <Row>
                                {popularLeagues.map(league => (
                                    <LeaguesCard key={league.idLeague} league={league}/>
                                ))}
                            </Row>
                        </div>
                    </Container>
                </>
            }</Col>
        </Row>
    </Container>)
}

export default Dashboard;