import { render, screen } from "@testing-library/react";
import { expect, test } from '@jest/globals';
import '@testing-library/jest-dom';
import Dashboard from "../src/components/dashboard/Dashboard";
import { MemoryRouter } from "react-router-dom";


describe("Dashboard Test", () => {

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
        render(<MemoryRouter><Dashboard /></MemoryRouter>);

        // Ensure pre-login components are rendered
        expect(screen.getAllByText(/wishlist/i)[0]).toBeInTheDocument();
    });
});

