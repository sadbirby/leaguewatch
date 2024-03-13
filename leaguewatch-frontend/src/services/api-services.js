import axios from 'axios';

axios.defaults.timeout=20000;

export const HttpGet = async (endPoint, queryParams = {}, headers = {}) => {
    try {
        if (typeof endPoint == 'string') {
            let result = await axios.get(endPoint, {
                params: queryParams,
                headers: headers
            });
            return result;
        }
        throw new Error('Incorrect request');
    } catch (error) {
        console.log('error', error)
        throw error;
    }
};

export const HttpPost = async (endPoint, body = {}, headers = {}) => {
    try {
        if (typeof endPoint == 'string') {
            let result = await axios.post(endPoint, body, {
                headers: headers
            });
            return result;
        }
        throw new Error('Incorrect request');
    } catch (error) {
        console.log('error', error);
        throw error;
    }
};

export const HttpPut = async (endPoint, body = {}, headers = {}) => {
    try {
        if (typeof endPoint == 'string') {
            let result = await axios.put(endPoint, body, {
                headers: headers
            });
            return result;
        }
        throw new Error('Incorrect request');
    } catch (error) {
        console.log('error', error);
        throw error;
    }
};

export const HttpDelete = async (endPoint, body = {}, headers = {}) => {
    try {
        if (typeof endPoint == 'string') {
            let result = await axios.delete(endPoint, {
                params: { ...body },
                headers: headers
            });
            return result;
        }
        throw new Error('Incorrect request');
    } catch (error) {
        console.log('error', error);
        throw error;
    }
}
