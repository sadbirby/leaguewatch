import { ADD_TO_WISHLIST, BASE_URI, FETCH_LEAGUES } from "../../constants/endpoints";
import { HttpGet, HttpPost } from "../../services/api-services";

export const FetchLeagues = async (values) => {
    try {
        let credentials = "Bearer " + localStorage.getItem("token");
        let apiUrl = values.sport === "" ?
            BASE_URI + FETCH_LEAGUES + values.country :
            BASE_URI + FETCH_LEAGUES + values.country + "/" + values.sport
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

export const AddToWishList = async (league) => {
    try {
        let credentials = "Bearer " + localStorage.getItem("token");
        let userId = localStorage.getItem("id");
        let apiUrl = BASE_URI + ADD_TO_WISHLIST + userId;
        let headers = {
            "Authorization": credentials
        }
        let response = await HttpPost(apiUrl, league, headers);
        return response.data;
    } catch (error) {
        console.log('error', error);
        throw error;
    }
}