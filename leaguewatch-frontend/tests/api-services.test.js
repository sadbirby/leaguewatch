import { expect } from '@jest/globals';
import axios from 'axios';
import {
    HttpGet,
    HttpPost,
    HttpPut,
    HttpDelete
} from '../src/services/api-services';

jest.mock('axios');

describe('Http utility functions', () => {

    beforeEach(() => {
        jest.resetAllMocks();
    });

    describe('HttpGet', () => {
        it('should make a GET request with the correct parameters', async () => {
            const mockData = { data: 'User retrieved' };
            const endPoint = 'https://example.com/api/data';
            const queryParams = { param1: 'value1' };
            const headers = { 'Authorization': 'Bearer Token' };
            axios.get = jest.fn().mockResolvedValue(mockData);

            // Act
            const result = await HttpGet(endPoint, queryParams, headers);

            // Assert
            expect(axios.get).toHaveBeenCalledWith(endPoint, { params: queryParams, headers });
            expect(result.data).toEqual(mockData.data);
        });

        it('should throw an error for invalid endpoint', async () => {
            await expect(HttpGet(123)).rejects.toThrow('Incorrect request');
        });
    });

    describe('HttpPost', () => {
        it('should make a POST request with the correct parameters', async () => {
            const mockData = { data: 'mocked response' };
            axios.post.mockResolvedValue(mockData);

            const endPoint = 'https://example.com/api';
            const requestBody = { key: 'value' };
            const headers = { Authorization: 'Bearer token' };

            const result = await HttpPost(endPoint, requestBody, headers);

            expect(axios.post).toHaveBeenCalledWith(endPoint, requestBody, {
                headers: headers,
            });
            expect(result).toEqual(mockData);
        });

        it('should throw an error for invalid endpoint', async () => {
            await expect(HttpPost(123)).rejects.toThrow('Incorrect request');
        });
    });

    describe('HttpPut', () => {
        it('should make a PUT request with the correct parameters', async () => {
            const mockData = { data: 'mocked response' };
            axios.put.mockResolvedValue(mockData);

            const endPoint = 'https://example.com/api';
            const requestBody = { key: 'value' };
            const headers = { Authorization: 'Bearer token' };

            const result = await HttpPut(endPoint, requestBody, headers);

            expect(axios.put).toHaveBeenCalledWith(endPoint, requestBody, {
                headers: headers,
            });
            expect(result).toEqual(mockData);
        });

        it('should throw an error for invalid endpoint', async () => {
            await expect(HttpPut(123)).rejects.toThrow('Incorrect request');
        });
    });

    describe('HttpDelete', () => {
        it('should make a DELETE request with the correct parameters', async () => {
            const mockData = { data: 'mocked response' };
            axios.delete.mockResolvedValue(mockData);

            const endPoint = 'https://example.com/api';
            const body = { param1: 'value1', param2: 'value2' };
            const headers = { Authorization: 'Bearer token' };

            const result = await HttpDelete(endPoint, body, headers);

            expect(axios.delete).toHaveBeenCalledWith(endPoint, {
                params: body,
                headers: headers,
            });
            expect(result).toEqual(mockData);
        });

        it('should throw an error for invalid endpoint', async () => {
            await expect(HttpDelete(123)).rejects.toThrow('Incorrect request');
        });
    });
});