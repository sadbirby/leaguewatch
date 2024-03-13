import { useEffect, useState } from "react";
import { FetchUserDetails, UpdateUserDetails } from "./UserHttpRequest";
import { Button, Container, FloatingLabel } from "react-bootstrap";
import Form from 'react-bootstrap/Form';
import { validateUser } from "../../services/form-validation";

const UserProfile = () => {

    const userEmail = localStorage.getItem("email");

    // Form object
    const userObj = {
        username: "",
        email: "",
        password: "",
        confirmPassword: ""
    };

    // User profile object state
    const [userProfile, setUserProfile] = useState(userObj);

    // Error object state
    const [errors, setErrors] = useState(userObj);
    const [isEditing, setIsEditing] = useState(false);
    const [editedProfile, setEditedProfile] = useState(userObj);

    useEffect(() => {
        const onLoadFetchUserDetaills = async () => {
            try {
                let user_details_response = await FetchUserDetails(userEmail);
                setUserProfile(user_details_response.data);
                setEditedProfile(user_details_response.data);
            } catch (error) {
                console.log('error', error);
            }
        }
        onLoadFetchUserDetaills();
    }, [userEmail]);

    const handleEditClick = () => {
        setIsEditing(true);
    };

    const handleSaveClick = async (event) => {
        event.preventDefault();
        const validations = await validateUser(userObj, editedProfile);
        if (!validations.hasErrors) {
            setErrors(userObj);
            if (window.confirm('Confirm profile update?')) {
                try {
                    const register_response = await UpdateUserDetails(editedProfile);
                    window.alert(register_response.message);
                    localStorage.setItem("email", editedProfile.email)
                    setIsEditing(false);
                } catch (error) {
                    let errorMessage = error.response.data;
                    console.log('error', error);
                    window.alert(errorMessage);
                }
            }
        } else {
            setErrors(prevErrors => ({
                ...prevErrors,
                ...validations.errors
            }));
        }
    };

    const handleCancelClick = () => {
        // Reset the edited profile to the initial values
        setEditedProfile(userProfile);
        setIsEditing(false);
    };

    // Handle user object state
    const handleChange = (prop) => (event) => {
        setErrors({ ...errors, [prop]: "" });
        setEditedProfile({ ...editedProfile, [prop]: event.target.value });
    }

    return (
        <Container className="w-50 mb-5 p-4 bg-white border border-2 rounded-4 shadow">
            <p className="display-6 mb-5 text-center">Edit Profile Details</p>

            <div className="px-5">
                <Form noValidate onSubmit={handleSaveClick}>
                    <FloatingLabel className="fw-lighter mb-4" controlId="formBasicEmail" label="Email address">
                        <Form.Control className="fw-lighter" size="lg"
                            type="email" placeholder="Enter email"
                            onChange={handleChange("email")} required
                            defaultValue={userProfile.email} disabled={!isEditing}
                            isInvalid={!!errors.email} />
                        <Form.Control.Feedback type="invalid">{errors.email}</Form.Control.Feedback>
                    </FloatingLabel>

                    <FloatingLabel className="fw-lighter mb-4" controlId="formBasicUsername" label="Username">
                        <Form.Control className="fw-lighter" size="lg"
                            type="text" placeholder="Enter username"
                            onChange={handleChange("username")} required
                            defaultValue={userProfile.username} disabled={!isEditing}
                            isInvalid={!!errors.username} />
                        <Form.Control.Feedback type="invalid">{errors.username}</Form.Control.Feedback>
                    </FloatingLabel>

                    <FloatingLabel className="fw-lighter mb-4" controlId="formBasicPassword" label="Password">
                        <Form.Control className="fw-lighter" size="lg"
                            type="password" placeholder="Enter password"
                            onChange={handleChange("password")} required
                            defaultValue={userProfile.password} disabled={!isEditing}
                            isInvalid={!!errors.password} />
                        <Form.Control.Feedback type="invalid">{errors.password}</Form.Control.Feedback>
                    </FloatingLabel>

                    {isEditing ?
                        (<>
                            <FloatingLabel className="fw-lighter mb-4" controlId="formBasicConfirmPassword" label="Confirm password">
                                <Form.Control className="fw-lighter" size="lg"
                                    type="password" placeholder="Confirm password"
                                    onChange={handleChange("confirmPassword")} required
                                    isInvalid={!!errors.confirmPassword} />
                                <Form.Control.Feedback type="invalid">{errors.confirmPassword}</Form.Control.Feedback>
                            </FloatingLabel>


                            <div className="d-grid gap-3">
                                <Button className="fw-lighter px-4 rounded-5" variant="outline-dark"
                                    size="lg" type="submit">
                                    Save
                                </Button>
                                <Button className="fw-lighter px-4 rounded-5" variant="outline-dark"
                                    size="lg" type="button" onClick={handleCancelClick}>
                                    Cancel
                                </Button>
                            </div>
                        </>)
                        :
                        (<div className="d-grid">
                            <Button className="fw-lighter px-4 rounded-5" variant="outline-dark"
                                size="lg" type="button" onClick={handleEditClick}>
                                Edit
                            </Button>
                        </div>)
                    }
                </Form>
            </div>
        </Container >
    );
}

export default UserProfile;