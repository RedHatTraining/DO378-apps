import * as jose from 'jose'
import { getRESTClient, ServiceName } from "./API";

const API = getRESTClient(ServiceName.PARKS_BACKEND);

export async function login(username: string, password: string): Promise<boolean> {

    const token = await API.url("auth/login")
        .post({ username, password })
        .unauthorized(error => {
            throw new Error(`Invalid credentials. Response ${error.response.status} ${error.response.statusText}`);
        })
        .notFound(error => {
            throw new Error(`Can't find "auth/login" endpoint in the backend service. Response ${error.response.status} ${error.response.statusText}`);
        })
        .text<string>()

    localStorage.setItem("jwt", token);

    return true;
}

interface UserResponse {
    username: string
}

export async function getUsername(): Promise<string | null> {
    const user = await API.url("auth/user")
        .auth(`Bearer ${getToken()}`)
        .get()
        .unauthorized(() => {
            logOut();
            return { username: null };
        })
        .json<UserResponse>();

    return user.username;



    // const token = getToken();
    // if (token !== null) {
    //     const decoded = jose.decodeJwt(token);
    //     return decoded.upn as string;
    // } else {
    //     return null;
    // }
}

export function getToken(): string | null {
    return localStorage.getItem("jwt");
}

export function logOut(): void {
    localStorage.removeItem("jwt");
}