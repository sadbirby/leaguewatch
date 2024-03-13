export const validateUser = async (schema, values) => {

    const validations = {
        hasErrors: false,
        errors: schema
    }

    // Validate username
    if (!values.username || !values.username.trim()) {
        validations.hasErrors = true;
        validations.errors.username = "Username is required";
    } else if (values.username.length < 3) {
        validations.hasErrors = true;
        validations.errors.username = "Username must be at least 3 characters long";
    }

    // Validate email
    if (!values.email || !values.email.trim()) {
        validations.hasErrors = true;
        validations.errors.email = "Email is required";
    } else if (!isValidEmail(values.email)) {
        validations.hasErrors = true;
        validations.errors.email = "Invalid email format";
    }

    // Validate password
    if (!values.password || !values.password.trim()) {
        validations.hasErrors = true;
        validations.errors.password = "Password is required";
    } else if (values.password.length < 8) {
        validations.hasErrors = true;
        validations.errors.password = "Password must be at least 8 characters long";
    }

    // Validate confirmPassword
    if (!values.confirmPassword || !values.confirmPassword.trim()) {
        validations.hasErrors = true;
        validations.errors.confirmPassword = "Confirm Password is required";
    } else if (values.confirmPassword !== values.password) {
        validations.hasErrors = true;
        validations.errors.confirmPassword = "Passwords do not match";
    }

    return validations;
}

export const validateAuth = async (schema, values) => {
    const validations = {
        hasErrors: false,
        errors: schema
    }

    // Validate email
    if (!values.email || !values.email.trim()) {
        validations.hasErrors = true;
        validations.errors.email = "Email is required";
    } else if (!isValidEmail(values.email)) {
        validations.hasErrors = true;
        validations.errors.email = "Invalid email format";
    }

    // Validate password
    if (!values.password || !values.password.trim()) {
        validations.hasErrors = true;
        validations.errors.password = "Password is required";
    } else if (values.password.length < 8) {
        validations.hasErrors = true;
        validations.errors.password = "Password must be at least 8 characters long";
    }

    return validations;
}

// Validate email format
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}