import { render, screen } from "@testing-library/react";
import { expect, test } from '@jest/globals';
import '@testing-library/jest-dom';
import Leagues from "../src/components/leagues/Leagues";
import { MemoryRouter } from "react-router-dom";



test('renders search bar when authenticated', () => {
    render(<Leagues />, { wrapper: MemoryRouter });

    // Ensure pre-login components are rendered
    expect(screen.getByText(/Search for a league/i)).toBeInTheDocument();
});