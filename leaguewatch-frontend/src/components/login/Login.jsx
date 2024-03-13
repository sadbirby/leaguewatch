import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Container from "react-bootstrap/Container";
import Form from 'react-bootstrap/Form';
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import LoginHttpRequest from './LoginHttpRequest';
import { JwtGetId } from '../../services/jwt-decoder';
import { useNavigate } from 'react-router-dom';
import { validateAuth } from '../../services/form-validation';

const Login = () => {

    const navigate = useNavigate();

    // Form object
    const authObj = {
        email: "",
        password: ""
    };

    // Login object state
    const [values, setValues] = useState(authObj);

    // Error object state
    const [errors, setErrors] = useState(authObj);

    // Handle login object state
    const handleChange = (prop) => (event) => {
        setErrors({ ...errors, [prop]: "" });
        setValues({ ...values, [prop]: event.target.value });
    }

    // Handle login
    const onLoginClick = async (event) => {
        event.preventDefault();
        const validations = await validateAuth(authObj, values);
        if (!validations.hasErrors) {
            try {
                let jwt_response = await LoginHttpRequest(values);
                let user_id = await JwtGetId(jwt_response.token);
                localStorage.setItem("isAuthenticated", true);
                localStorage.setItem("token", jwt_response.token);
                localStorage.setItem("id", user_id);
                localStorage.setItem("email", values.email);
                window.alert(jwt_response.message);
                navigate("../dashboard", { replace: true });
                navigate(0);
            } catch (error) {
                let errorMessage = error.response.data;
                window.alert(errorMessage);
            }
        } else {
            setErrors(prevErrors => ({
                ...prevErrors,
                ...validations.errors
            }));
        }
    }

    // Login component
    return (
        <Container className="w-50 mb-5 px-0 py-4 bg-white border border-2 rounded-4 shadow">
            <div>
                <p className="fs-4 fw-lighter text-center">Sign in to LeagueWatch</p>
            </div>
            <hr className="border opacity-100 mb-4"></hr>
            <div className="px-5">
                {/* Login form */}
                <Form noValidate onSubmit={onLoginClick}>
                    <FloatingLabel className="fw-lighter mb-4" controlId="formBasicEmail" label="Email address">
                        <Form.Control className="fw-lighter" size="lg"
                            type="email" placeholder="Enter email"
                            onChange={handleChange("email")} required
                            isInvalid={!!errors.email} />
                        <Form.Control.Feedback type="invalid">{errors.email}</Form.Control.Feedback>
                    </FloatingLabel>

                    <FloatingLabel className="fw-lighter mb-4" controlId="formBasicPassword" label="Password">
                        <Form.Control className="fw-lighter" size="lg"
                            type="password" placeholder="Enter password"
                            onChange={handleChange("password")} required
                            isInvalid={!!errors.password} />
                        <Form.Control.Feedback type="invalid">{errors.password}</Form.Control.Feedback>
                    </FloatingLabel>

                    <div className="d-grid">
                        <Button className="fw-lighter px-4 rounded-5" variant="outline-dark" size="lg" type="submit">
                            Login
                        </Button>
                    </div>
                </Form>
            </div>
        </Container>
    );
}

export default Login;