import { BASE_URI, DELETE_WISHLIST_ITEM, FETCH_WISHLIST } from "../../constants/endpoints";
import { HttpDelete, HttpGet } from "../../services/api-services";


export const FetchWishlist = async (userId) => {
    try {
        let credentials = "Bearer " + localStorage.getItem("token");
        let apiUrl = BASE_URI + FETCH_WISHLIST + userId;
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

export const DeleteFromWishlist = async (props) => {
    const { userId, idLeague } = props;
    try {
        let credentials = "Bearer " + localStorage.getItem("token");
        let apiUrl = BASE_URI + DELETE_WISHLIST_ITEM + userId + "/" + idLeague;
        let headers = {
            "Authorization": credentials
        }
        let response = await HttpDelete(apiUrl, {}, headers);
        return response.data;
    } catch (error) {
        console.log('error', error);
        throw error;
    }

}