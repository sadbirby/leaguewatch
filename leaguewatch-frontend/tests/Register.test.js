import { render, screen } from "@testing-library/react";
import { expect, test } from '@jest/globals';
import '@testing-library/jest-dom';
import Register from "../src/components/register/Register";
import RegisterHttpRequest from "../src/components/register/RegisterHttpRequest";
import { HttpPost } from "../src/services/api-services";
import { MemoryRouter } from "react-router-dom";

jest.mock('../src/services/api-services');

describe("Register Test", () => {

    beforeAll(() => {
        Object.defineProperty(window, 'matchMedia', {
            writable: true,
            value: jest.fn().mockImplementation(query => ({
                matches: false,
                media: query,
                onchange: null,
                addListener: jest.fn(), // Deprecated
                removeListener: jest.fn(), // Deprecated
                addEventListener: jest.fn(),
                removeEventListener: jest.fn(),
                dispatchEvent: jest.fn(),
            })),
        });
    });

    test('renders dashboard when authenticated', () => {
        render(<MemoryRouter><Register /></MemoryRouter>);

        // Ensure pre-login components are rendered
        expect(screen.getByText(/Create your account/i)).toBeInTheDocument();
    });

    it('should make a successful HTTP POST request', async () => {
        const mockResponseData = { message: 'User registered successfully' };
        HttpPost.mockResolvedValueOnce({ data: mockResponseData });
    
        const registrationData = {
          username: 'testuser',
          email: 'test@example.com',
          password: 'password123',
        };
    
        const response = await RegisterHttpRequest(registrationData);
    
        expect(HttpPost).toHaveBeenCalledWith(expect.any(String), registrationData);
        expect(response).toEqual(mockResponseData);
      });
    
      it('should handle HTTP POST request failure', async () => {
        const mockError = new Error('Failed to register user');
        HttpPost.mockRejectedValueOnce(mockError);
    
        const registrationData = {
          username: 'testuser',
          email: 'test@example.com',
          password: 'password123',
        };
    
        await expect(RegisterHttpRequest(registrationData)).rejects.toThrow(mockError);
      });
});

