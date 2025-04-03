import axios from "axios";
import { 
    GET_USER_FAILURE, GET_USER_REQUEST, GET_USER_SUCCESS, 
    LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, 
    LOGOUT, 
    REGISTER_FAILURE, REGISTER_REQUEST, REGISTER_SUCCESS 
} from "./ActionType";

const baseURL = "http://localhost:8080";

export const register = (userData) => async (dispatch) => {
    dispatch({ type: REGISTER_REQUEST });

    try {
        const response = await axios.post(`${baseURL}/auth/signup`, userData, {
            headers: { "Content-Type": "application/json" } // CORS fix
        });

        const user = response.data;
        console.log("User registered:", user);

        dispatch({ type: REGISTER_SUCCESS, payload: user.jwt });
        localStorage.setItem("jwt", user.jwt);
    } catch (error) {
        console.error("Registration Error:", error.response?.data || error.message);
        dispatch({ type: REGISTER_FAILURE, payload: error.response?.data?.message || error.message });
    }
};

export const login = (userData) => async (dispatch) => {
    dispatch({ type: LOGIN_REQUEST });

    const baseURL = "http://localhost:8080";

    try {
        const response = await axios.post(`${baseURL}/auth/signin`, userData.data);
        console.log("Response from server:", response.data); // 🔍 Debugging

        const user = response.data;

        if (user.jwt) {
            dispatch({ type: LOGIN_SUCCESS, payload: user.jwt });
            localStorage.setItem("jwt", user.jwt);
            userData.navigate("/")
            console.log("JWT stored in localStorage:", user.jwt); // 🟢 Check if stored
        } else {
            console.log("No JWT token received!"); // 🛑 Debugging
        }
    } catch (error) {
        dispatch({ type: LOGIN_FAILURE, payload: error.message });
        console.log("Login error:", error);
    }
};


export const getUser = (jwt) => async (dispatch) => {
    dispatch({ type: GET_USER_REQUEST });

    try {
        const response = await axios.get(`${baseURL}/api/users/profile`, {
            headers: {
                Authorization: `Bearer ${jwt}`
            }
        });

        const user = response.data;
        console.log("Fetched user:", user);

        dispatch({ type: GET_USER_SUCCESS, payload: user });
    } catch (error) {
        console.error("Get User Error:", error.response?.data || error.message);
        dispatch({ type: GET_USER_FAILURE, payload: error.response?.data?.message || error.message });
    }
};

export const logout = () => (dispatch) => {
    localStorage.clear()
    dispatch({type:LOGOUT})
}
