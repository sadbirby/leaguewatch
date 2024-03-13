import { BrowserRouter } from "react-router-dom";
import { expect, test } from '@jest/globals';
import '@testing-library/jest-dom';
import Login from "../src/components/login/Login";
import { act, fireEvent, render, screen } from "@testing-library/react";

jest.mock('../src/services/form-validation', () => ({
    validateAuth: jest.fn(() => ({ hasErrors: false, errors: {} })),
}));

jest.mock('../src/components/login/LoginHttpRequest', () => ({
    __esModule: true,
    default: jest.fn(() => ({ token: 'mockedToken', message: 'Login successful' })),
}));

jest.mock('../src/services/jwt-decoder', () => ({
    JwtGetId: jest.fn(() => 'mockedUserId'),
}));

// Mock localStorage
let localStorageMock = {};
globalThis.localStorage = {
    getItem: (key) => localStorageMock[key],
    setItem: (key, value) => (localStorageMock[key] = value),
    clear: () => (localStorageMock = {}),
};

describe('Login component', () => {
    beforeEach(() => {
        // Clear localStorage before each test
        localStorage.clear();
    });

    test('renders login form', () => {
        render(
            <BrowserRouter>
                <Login />
            </BrowserRouter>
        );

        expect(screen.getByLabelText(/Email address/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/Password/i)).toBeInTheDocument();
        expect(screen.getByText(/Login/i)).toBeInTheDocument();
    });

    test('submits form with valid data', async () => {
        const { getByLabelText, getByText } = render(
            <BrowserRouter>
                <Login />
            </BrowserRouter>
        );

        // Mock user input
        fireEvent.change(getByLabelText(/Email address/i), { target: { value: 'test@example.com' } });
        fireEvent.change(getByLabelText(/Password/i), { target: { value: 'password123' } });

        // Mock form submission
        await act(async () => {
            fireEvent.click(getByText('Login'));
        });

        // Check if localStorage is updated
        expect(localStorage.getItem('isAuthenticated')).toBe('true');
        expect(localStorage.getItem('token')).toBe('mockedToken');
        expect(localStorage.getItem('id')).toBe('mockedUserId');
        expect(localStorage.getItem('email')).toBe('test@example.com');
    });
}); 