import { BASE_URI, REGISTER } from "../../constants/endpoints";
import { HttpPost } from "../../services/api-services";

const RegisterHttpRequest = async (props) => {
    try {
        let apiUrl = BASE_URI + REGISTER;
        let response = await HttpPost(apiUrl, {
            username: props.username,
            email: props.email,
            password: props.password
        });
        return response.data;
    } catch (error) {
        console.log('error', error);
        throw error;
    }
}

export default RegisterHttpRequest;