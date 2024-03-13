import React, { useState } from "react";
import PropTypes from "prop-types";
import { Button, Card, CloseButton, Col, Row } from "react-bootstrap";
import OffcanvasInfo from "./OffcanvasInfo";

const WishlistCard = ({ league, onClickDeleteFromWishlist }) => {

    const [show, setShow] = useState(false);
    const [infoText, setInfoText] = useState("More info");

    const handleShow = () => {
        setShow(!show);
        show ? setInfoText("More info") : setInfoText("Less info")
    }

    return (<React.Fragment>
        <Col md={12} lg={6} className="p-4" key={league.idLeague}>
            <Card className="bg-light mb-2 shadow">
                <Card.Header className="fw-lighter">
                    <Row>
                        <Col>{league.strSport}</Col>
                        <Col className="d-flex flex-row-reverse ">
                            {/* Delete from wishlist button */}
                            <CloseButton onClick={() => onClickDeleteFromWishlist(league.idLeague)} />
                        </Col>
                    </Row>
                </Card.Header>
                <Card.Body>
                    <Card.Title className="lead mb-3">{league.strLeague}</Card.Title>
                    <Card.Subtitle className="fst-italic mb-3 text-muted">{league.strCountry}</Card.Subtitle>
                    <Button className="mb-2 rounded-pill" variant="outline-success" onClick={handleShow}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-info-circle-fill" viewBox="0 0 16 16">
                            <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16m.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2" />
                        </svg>
                        &ensp;{infoText}
                    </Button>
                </Card.Body>
            </Card>
        </Col>

        <OffcanvasInfo show={show} setShow={handleShow} league={league} />
    </React.Fragment >);
}

WishlistCard.propTypes = {
    league: PropTypes.object,
    onClickDeleteFromWishlist: PropTypes.func
}

export default WishlistCard;