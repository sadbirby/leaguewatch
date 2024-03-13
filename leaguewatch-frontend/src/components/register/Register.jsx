import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Container from "react-bootstrap/Container";
import Form from 'react-bootstrap/Form';
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import { useNavigate } from 'react-router-dom';
import RegisterHttpRequest from './RegisterHttpRequest';
import { validateUser } from '../../services/form-validation';

const Register = () => {

    const navigate = useNavigate();

    // Form object
    const userObj = {
        username: "",
        email: "",
        password: "",
        confirmPassword: ""
    };

    // Register object state
    const [values, setValues] = useState(userObj);

    // Error object state
    const [errors, setErrors] = useState(userObj);

    // Handle login object state
    const handleChange = (prop) => (event) => {
        setErrors({ ...errors, [prop]: "" });
        setValues({ ...values, [prop]: event.target.value });
    }

    // Handle Register
    const onSubmitClick = async (event) => {
        event.preventDefault();
        const validations = await validateUser(userObj, values);
        if (!validations.hasErrors) {
            setErrors(userObj);
            try {
                const register_response = await RegisterHttpRequest(values);
                window.alert(register_response.message);
                navigate("../login", { replace: true });
            } catch (error) {
                let errorMessage = error.response.data;
                console.log('error', error);
                window.alert(errorMessage);
            }
        } else {
            setErrors(prevErrors => ({
                ...prevErrors,
                ...validations.errors
            }));
        }
    }

    // Register component
    return (<Container className="w-50 mb-5 px-0 py-4 bg-white border border-2 rounded-4 shadow">
        <div>
            <p className="fs-4 fw-lighter text-center">Create your account</p>
        </div>
        <hr className="border opacity-100 mb-4"></hr>
        <div className="px-5">
            <Form noValidate onSubmit={onSubmitClick}>
                <FloatingLabel className="fw-lighter mb-4" controlId="formBasicEmail" label="Email address">
                    <Form.Control className="fw-lighter" size="lg"
                        type="email" placeholder="Enter email"
                        onChange={handleChange("email")} required
                        isInvalid={!!errors.email} />
                    <Form.Control.Feedback type="invalid">{errors.email}</Form.Control.Feedback>
                </FloatingLabel>

                <FloatingLabel className="fw-lighter mb-4" controlId="formBasicUsername" label="Username">
                    <Form.Control className="fw-lighter" size="lg"
                        type="text" placeholder="Enter username"
                        onChange={handleChange("username")} required
                        isInvalid={!!errors.username} />
                    <Form.Control.Feedback type="invalid">{errors.username}</Form.Control.Feedback>
                </FloatingLabel>

                <FloatingLabel className="fw-lighter mb-4" controlId="formBasicPassword" label="Password">
                    <Form.Control className="fw-lighter" size="lg"
                        type="password" placeholder="Enter password"
                        onChange={handleChange("password")} required
                        isInvalid={!!errors.password} />
                    <Form.Control.Feedback type="invalid">{errors.password}</Form.Control.Feedback>
                </FloatingLabel>

                <FloatingLabel className="fw-lighter mb-4" controlId="formBasicConfirmPassword" label="Confirm password">
                    <Form.Control className="fw-lighter" size="lg"
                        type="password" placeholder="Confirm password"
                        onChange={handleChange("confirmPassword")} required
                        isInvalid={!!errors.confirmPassword} />
                    <Form.Control.Feedback type="invalid">{errors.confirmPassword}</Form.Control.Feedback>
                </FloatingLabel>

                <div className="d-grid">
                    <Button className="fw-lighter px-4 rounded-5" variant="outline-dark" size="lg" type="submit">
                        Create account
                    </Button>
                </div>
            </Form>
        </div>
    </Container>);
}

export default Register;