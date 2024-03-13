import Container from "react-bootstrap/Container";
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { LinkContainer } from "react-router-bootstrap";

const NavbarPreLogin = () => {

    return (<Navbar className="bg-white mb-4 border-2 border-bottom lead p-3 shadow" expand="lg" sticky="top">
        <Container fluid>
            <LinkContainer to="../"><Navbar.Brand>
                <img
                    alt=""
                    src="vite.svg"
                    width="36"
                    height="36"
                    className="d-inline-block align-top"
                />{' '}
                LeagueWatch
            </Navbar.Brand></LinkContainer>

            <Navbar.Toggle aria-controls="basic-navbar-nav">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-list-nested" viewBox="0 0 16 16">
                    <path fillRule="evenodd" d="M4.5 11.5A.5.5 0 0 1 5 11h10a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5m-2-4A.5.5 0 0 1 3 7h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m-2-4A.5.5 0 0 1 1 3h10a.5.5 0 0 1 0 1H1a.5.5 0 0 1-.5-.5" />
                </svg>
            </Navbar.Toggle>

            <Navbar.Collapse className="justify-content-end" id="basic-navbar-nav">
                <Nav fill justify className="mr-auto">
                    <LinkContainer to="../login"><Nav.Link>
                        Login
                    </Nav.Link></LinkContainer>
                    <LinkContainer to="../register"><Nav.Link>
                        Signup
                    </Nav.Link></LinkContainer>
                </Nav>
            </Navbar.Collapse>
        </Container>
    </Navbar>);
}

export default NavbarPreLogin;
