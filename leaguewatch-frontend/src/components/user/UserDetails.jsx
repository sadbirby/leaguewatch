import { useEffect, useState } from "react";
import { LinkContainer } from "react-router-bootstrap";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import { FetchUserDetails } from "./UserHttpRequest";

const UserDetails = () => {

    const userEmail = localStorage.getItem("email");
    const [username, setUsername] = useState("");

    useEffect(() => {
        const onLoadFetchUserDetaills = async () => {
            try {
                let user_details_response = await FetchUserDetails(userEmail);
                setUsername(user_details_response.data.username);
            } catch (error) {
                console.log('error', error);
            }
        }
        onLoadFetchUserDetaills();
    }, [userEmail]);



    return (
        <Container className="mb-4 p-4 bg-white border border-2 rounded-4 shadow">
            <p className="h3 fw-lighter text-center text-break">{username}</p>
                <p className="h5 fw-lighter mb-4 text-center text-break">{userEmail}</p>
            <div className="d-grid gap-2">
                <LinkContainer to="../profile">
                    <Button className="fw-lighter px-4 rounded-5" variant="outline-dark" size="lg">Edit profile</Button>
                </LinkContainer>
            </div>
        </Container>
    );
}

export default UserDetails;