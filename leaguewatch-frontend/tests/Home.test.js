import { fireEvent, render, screen } from "@testing-library/react";
import '@testing-library/jest-dom';
import Home from "../src/components/home/Home";
import { MemoryRouter } from "react-router-dom";

describe('Home Component', () => {
  test('renders welcome message', () => {
    render(<MemoryRouter><Home /></MemoryRouter>);
    const welcomeMessage = screen.getByText(/Welcome to LeagueWatch/i);
    expect(welcomeMessage).toBeInTheDocument();
  });

  test('renders description text', () => {
    render(<MemoryRouter><Home /></MemoryRouter>);
    const descriptionText = screen.getByText(/LeagueWatch acts as a centralized interface where you can effortlessly access and manage you favorite leagues without the hassle of switching between different sources./i);
    expect(descriptionText).toBeInTheDocument();
  });

  test('renders "Create your account" button', () => {
    render(<MemoryRouter><Home /></MemoryRouter>);
    const createAccountButton = screen.getByText(/Create your account/i);
    expect(createAccountButton).toBeInTheDocument();
  });

  test('renders "Sign in" button', () => {
    render(<MemoryRouter><Home /></MemoryRouter>);
    const signInButton = screen.getByText(/Sign in/i);
    expect(signInButton).toBeInTheDocument();
  });

  test('redirects to register page on "Create your account" button click', () => {
    render(<MemoryRouter><Home /></MemoryRouter>);
    const createAccountButton = screen.getByText('Create your account');
    fireEvent.click(createAccountButton);
  });

  test('redirects to login page on "Sign in" button click', () => {
    render(<MemoryRouter><Home /></MemoryRouter>);
    const signInButton = screen.getByText('Sign in');
    fireEvent.click(signInButton);
  });
});