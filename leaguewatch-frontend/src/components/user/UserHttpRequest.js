import { BASE_URI, EDIT_USER, FETCH_USER_DETAILS } from "../../constants/endpoints";
import { HttpGet, HttpPut } from "../../services/api-services";

export const FetchUserDetails = async (userEmail) => {
    try {
        let credentials = "Bearer " + localStorage.getItem("token");
        let apiUrl = BASE_URI + FETCH_USER_DETAILS + userEmail;
        let headers = {
            "Authorization": credentials
        }
        let response = await HttpGet(apiUrl, {}, headers);
        return response.data;
    } catch (error) {
        console.log('error', error);
        throw error;
    }
}

export const UpdateUserDetails = async (props) => {
    try {
        let credentials = "Bearer " + localStorage.getItem("token");
        let apiUrl = BASE_URI + EDIT_USER + localStorage.getItem("email");
        let headers = {
            "Authorization": credentials
        }
        let response = await HttpPut(apiUrl, {
            username: props.username,
            email: props.email,
            password: props.password
        }, headers);
        return response.data;
    } catch (error) {
        console.log('error', error);
        throw error;
    }
}