import { describe, expect } from '@jest/globals';
import { JwtGetId } from "../src/services/jwt-decoder";

describe('JwtGetId', () => {
    test('should extract and return the correct ID from a valid JWT', async () => {
        const validJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMzQ1Njc4OTAifQ._aG0ukzancZqhL1wvBTJh8G8d3Det5n0WKcPo5C0DCY";
        const result = await JwtGetId(validJwt);
        expect(result).toBe("1234567890");
    });

    test('should throw an error for an invalid JWT format', async () => {
        const invalidJwt = 'invalidToken';
        await expect(JwtGetId(invalidJwt)).rejects.toThrow();
    });

    test('should throw an error for a JWT with an invalid base64 payload', async () => {
        const invalidBase64Jwt = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.####.eNqq6_XfDSZpHw3kaf-wTzd8l_2xF_HR7yvl2iFpxeE';
        await expect(JwtGetId(invalidBase64Jwt)).rejects.toThrow();
    });
});