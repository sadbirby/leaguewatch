import { describe, expect, it } from '@jest/globals';
import { validateUser, validateAuth } from '../src/services/form-validation'; // Replace with the correct file path

describe('validateUser', () => {
  const schema = {
    username: "",
    email: "",
    password: "",
    confirmPassword: ""
  };

  it('returns no errors for valid input', async () => {
    const values = {
      username: "JohnDoe",
      email: "john@example.com",
      password: "strongPassword",
      confirmPassword: "strongPassword"
    };

    const result = await validateUser(schema, values);
    expect(result.hasErrors).toBe(false);
    expect(result.errors).toEqual(schema);
  });

  it('returns errors for invalid input', async () => {
    const values = {
      username: "",
      email: "invalidEmail",
      password: "short",
      confirmPassword: "passwordMismatch"
    };

    const result = await validateUser(schema, values);
    expect(result.hasErrors).toBe(true);
    expect(result.errors.username).toBeTruthy();
    expect(result.errors.email).toBeTruthy();
    expect(result.errors.password).toBeTruthy();
    expect(result.errors.confirmPassword).toBeTruthy();
  });
});

describe('validateAuth', () => {
  const schema = {
    email: "",
    password: ""
  };

  it('returns no errors for valid input', async () => {
    const values = {
      email: "john@example.com",
      password: "strongPassword"
    };

    const result = await validateAuth(schema, values);
    expect(result.hasErrors).toBe(false);
    expect(result.errors).toEqual(schema);
  });

  it('returns errors for invalid input', async () => {
    const values = {
      email: "invalidEmail",
      password: "short"
    };

    const result = await validateAuth(schema, values);
    expect(result.hasErrors).toBe(true);
    expect(result.errors.email).toBeTruthy();
    expect(result.errors.password).toBeTruthy();
  });
});