import PropTypes from "prop-types";
import { Col, Offcanvas, Row } from "react-bootstrap";

const OffcanvasInfo = ({ show, setShow, league }) => {

    return (
        < Offcanvas className="content-offcanvas" placement="bottom"
            show={show} onHide={() => setShow()} >
            <Offcanvas.Header closeButton>
                <Offcanvas.Title>{league.strLeague}</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
                <Row>
                    <Col sm={2} className="p-2 ms-auto">
                        <p className="fs-7">Formed: <strong>{league.intFormedYear}</strong></p>
                        <p className="fs-7">First event: <strong>{league.dateFirstEvent}</strong></p>
                        <p className="fs-7">Current season: <strong>{league.strCurrentSeason}</strong></p>
                        <p className="fs-7">Players&apos; gender: <strong>{league.strGender}</strong></p>
                    </Col>
                    <Col sm={10} className="px-4 ms-auto">
                        <p className="fs-7">Description:</p>
                        <p className="fs-7 text-justify">{league.strDescriptionEN}</p>
                    </Col>
                </Row>
            </Offcanvas.Body>
        </Offcanvas >
    );
}

OffcanvasInfo.propTypes = {
    show: PropTypes.bool,
    setShow: PropTypes.func,
    league: PropTypes.object
}

export default OffcanvasInfo;