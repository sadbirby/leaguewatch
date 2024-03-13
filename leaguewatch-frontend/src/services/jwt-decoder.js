export const JwtGetId = async (jwt) => {
    let tokens = jwt.split(".");
    let id = JSON.parse(atob(tokens[1])).id;
    return id;
}