import { Col, Image, Row } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import Container from "react-bootstrap/Container";
import { LinkContainer } from 'react-router-bootstrap';

const Home = () => {
    return (<Container className="w-75 mb-5 p-4 bg-white border border-2 rounded-4 shadow">
            <p className="display-6 text-center">Welcome to LeagueWatch</p>
            <br />
            <Row xs={1} sm={1} md={1} lg={2}>
                <Col className="mb-4">
                    <p className="lead px-5 text-justify">LeagueWatch acts as a centralized interface where you
                        can effortlessly access and manage you favorite leagues
                        without the hassle of switching between different sources.
                    </p>
                    <Image fluid src="fans.svg" rounded />

                </Col>
                <Container className="mb-4 w-50">
                    <Image fluid src="goal.svg" rounded />
                    <br />
                    <br />
                    <p className="lead text-center">
                        Go ahead. Sign up today.
                    </p>
                    <div className="d-grid">
                        <LinkContainer to="register">
                            <Button className="fw-lighter px-4 rounded-5" variant="outline-dark" size="lg">Create your account</Button>
                        </LinkContainer>
                        <br />
                        <p className="lead text-center">
                            Already have an account?
                        </p>
                        <LinkContainer to="login">
                            <Button className="fw-lighter px-4 rounded-5" variant="outline-dark" size="lg">Sign in</Button>
                        </LinkContainer>
                    </div>
                </Container>
            </Row>
        </Container>);
}

export default Home;