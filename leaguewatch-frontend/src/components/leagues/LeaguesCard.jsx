import React, { useState } from "react";
import PropTypes from "prop-types";
import { Button, Card, Col, Container, Row } from "react-bootstrap";
import { AddToWishList } from "./LeaguesHttpRequest";
import OffcanvasInfo from "../dashboard/OffcanvasInfo";

const LeaguesCard = ({ league }) => {

    const [show, setShow] = useState(false);
    const [infoText, setInfoText] = useState("More info");

    const onMoreInfoClick = () => {
        setShow(!show);
        show ? setInfoText("More info") : setInfoText("Less info")
    }

    const onAddToWishlistClick = async () => {
        try {
            const addToWishlist_response = await AddToWishList(league);
            window.alert(addToWishlist_response.message);
        } catch (error) {
            let errorMessage = error.response.data;
            window.alert(errorMessage);
        }
    }

    return (<React.Fragment>
        <Col xs={12} md={12} lg={6} className="p-4" key={league.idLeague}>
            <Card className="bg-light mb-2 shadow">
                <Card.Header className="fw-lighter">{league.strSport}
                </Card.Header>
                <Card.Body>
                    <Card.Title className="lead mb-3">{league.strLeague}</Card.Title>
                    <Card.Subtitle className="fst-italic mb-3 text-muted">{league.strCountry}</Card.Subtitle>
                    <Container fluid>
                        <Row xs={1} md={2} lg={2}>
                            {/* More info button */}
                            <Col><Button className="mb-2 rounded-pill" variant="outline-success" onClick={onMoreInfoClick}>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-info-circle-fill" viewBox="0 0 16 16">
                                    <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16m.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2" />
                                </svg>
                                &ensp;{infoText}
                            </Button></Col>
                            {/* Add to wishlist button */}
                            <Col><Button className="mb-2 rounded-pill" variant="outline-primary" onClick={onAddToWishlistClick}>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-bookmark-star-fill" viewBox="0 0 16 16">
                                    <path fillRule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5M8.16 4.1a.178.178 0 0 0-.32 0l-.634 1.285a.178.178 0 0 1-.134.098l-1.42.206a.178.178 0 0 0-.098.303L6.58 6.993c.042.041.061.1.051.158L6.39 8.565a.178.178 0 0 0 .258.187l1.27-.668a.178.178 0 0 1 .165 0l1.27.668a.178.178 0 0 0 .257-.187L9.368 7.15a.178.178 0 0 1 .05-.158l1.028-1.001a.178.178 0 0 0-.098-.303l-1.42-.206a.178.178 0 0 1-.134-.098z" />
                                </svg>
                                &ensp;Add to wishlist
                            </Button></Col>
                        </Row>
                    </Container>
                </Card.Body>
            </Card>
        </Col>
        <OffcanvasInfo show={show} setShow={onMoreInfoClick} league={league} />
    </React.Fragment >);
}

LeaguesCard.propTypes = {
    league: PropTypes.object
}

export default LeaguesCard;