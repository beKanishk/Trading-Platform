import api from "@/config/Api";
import { FETCH_COIN_BY_ID_FAILURE, FETCH_COIN_BY_ID_REQUEST, FETCH_COIN_BY_ID_SUCCESS, FETCH_COIN_DETAILS_FAILURE, FETCH_COIN_DETAILS_REQUEST, FETCH_COIN_DETAILS_SUCCESS, FETCH_COIN_LIST_FAILURE, FETCH_COIN_LIST_REQUEST, FETCH_COIN_LIST_SUCCESS, FETCH_MARKET_CHART_FAILURE, FETCH_MARKET_CHART_REQUEST, FETCH_MARKET_CHART_SUCCESS, FETCH_TOP_50_COINS_FAILURE, FETCH_TOP_50_COINS_REQUEST, FETCH_TOP_50_COINS_SUCCESS, FETCH_TRENDING_COINS_FAILURE, FETCH_TRENDING_COINS_REQUEST, FETCH_TRENDING_COINS_SUCCESS, SEARCH_COIN_FAILURE, SEARCH_COIN_REQUEST, SEARCH_COIN_SUCCESS } from "./ActionType";
import axios from "axios";

const baseURL = "http://localhost:8080";

export const getCoinList = (page) => async (dispatch) => {
    
    dispatch({ type: FETCH_COIN_LIST_REQUEST });

    try {
        const response = await axios.get(`${baseURL}/coins?page=${1}`);

        console.log("Coin List Response:", response.data);
        dispatch({ type: FETCH_COIN_LIST_SUCCESS, payload: response.data }); 

    } catch (error) {
        console.error("Error fetching coin list:", error.message);
        dispatch({ type: FETCH_COIN_LIST_FAILURE, payload: error.message });
    }
};


export const getTop50CoinList = () => async (dispatch) => {
    dispatch({ type: FETCH_TOP_50_COINS_REQUEST });

    try {
        const response = await axios.get(`${baseURL}/coins/top50`);

        
        console.log("top-50", response.data);

        dispatch({ type: FETCH_TOP_50_COINS_SUCCESS, payload: response.data });
        localStorage.setItem("top_50", JSON.stringify(response.data)); 

    } catch (error) {
        console.error("Error", error.response?.data || error.message);
        dispatch({ type: FETCH_TOP_50_COINS_FAILURE, payload: error.response?.data?.message || error.message });
    }
};

export const getTrending = () => async (dispatch) => {
    dispatch({ type: FETCH_TRENDING_COINS_REQUEST });

    try {
        const response = await axios.get(`${baseURL}/coins/trending`);

        
        console.log("Trending", response.data.coins);

        dispatch({ type: FETCH_TRENDING_COINS_SUCCESS, payload: response.data });
        localStorage.setItem("Trending coins", JSON.stringify(response.data)); 

    } catch (error) {
        console.error("Error", error.response?.data || error.message);
        dispatch({ type: FETCH_TRENDING_COINS_FAILURE, payload: error.response?.data?.message || error.message });
    }
};

export const fetchMarketChart = ({coinId, days, jwt}) => async (dispatch) => {
    dispatch({ type: FETCH_MARKET_CHART_REQUEST });

    try {
        const response = await api.get(`/coins/${coinId}/chart?days=${days}`,{
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });
        //console.log("market chart:", response.data);
        
        dispatch({ type: FETCH_MARKET_CHART_SUCCESS, payload: response.data });
    } catch (error) {
        console.error("Error", error);
        dispatch({ type: FETCH_MARKET_CHART_FAILURE, payload: error.message });
    }
};

export const fetchCoinById = (coinId) => async (dispatch) => {
    dispatch({ type: FETCH_COIN_BY_ID_REQUEST });

    try {
        const response = await axios.get(`${baseURL}/coins/${coinId}`);
        dispatch({ type: FETCH_COIN_BY_ID_SUCCESS, payload: response.data });
    } catch (error) {
        console.error("Error", error);
        dispatch({ type: FETCH_COIN_BY_ID_FAILURE, payload: error.message });
    }
};

export const fetchCoinDetails = ({coinId, jwt}) => async (dispatch) => {
    dispatch({ type: FETCH_COIN_DETAILS_REQUEST });

    try {
        const response = await api.get(`/coins/details/${coinId}`,{
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });

        dispatch({ type: FETCH_COIN_DETAILS_SUCCESS, payload: response.data });
        console.log("Coin details", response.data)
    } catch (error) {
        console.error("Error", error);
        dispatch({ type: FETCH_COIN_DETAILS_FAILURE, payload: error.message });
    }
};

export const searchCoin = (keyword) => async (dispatch) => {
    dispatch({ type: SEARCH_COIN_REQUEST });

    try {
        const response = await api.get(`/coins/search?q=${keyword}`);
        dispatch({ type: SEARCH_COIN_SUCCESS, payload: response.data });
        console.log("search coin", response.data)
    } catch (error) {
        console.error("Error", error);
        dispatch({ type: SEARCH_COIN_FAILURE, payload: error.message });
    }
};