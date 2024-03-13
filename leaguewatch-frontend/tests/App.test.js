// import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { expect, test } from '@jest/globals';
import '@testing-library/jest-dom';
import App from '../src/App';

test('renders pre-login components when not authenticated', () => {
    render(<App />, { wrapper: Router });

    // Ensure pre-login components are rendered
    expect(screen.getByText(/Welcome to LeagueWatch/i)).toBeInTheDocument();
});