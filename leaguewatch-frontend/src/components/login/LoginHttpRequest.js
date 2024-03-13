import { AUTHENTICATE, BASE_URI } from "../../constants/endpoints"
import { HttpPost } from "../../services/api-services";

const LoginHttpRequest = async (props) => {
    try {
        let apiUrl = BASE_URI + AUTHENTICATE;
        let response = await HttpPost(apiUrl, {
            email: props.email,
            password: props.password
        });
        return response.data;
    } catch (error) {
        console.log('error', error);
        throw error;
    }
}

export default LoginHttpRequest;