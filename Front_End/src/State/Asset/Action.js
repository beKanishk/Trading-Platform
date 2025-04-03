import api from "@/config/Api";
import { GET_ASSET_DETAILS_FAILURE, GET_ASSET_DETAILS_REQUEST, GET_ASSET_DETAILS_SUCCESS, GET_ASSET_FAILURE, GET_ASSET_REQUEST, GET_ASSET_SUCCESS, GET_USER_ASSETS_FAILURE, GET_USER_ASSETS_REQUEST, GET_USER_ASSETS_SUCCESS } from "./ActionType";

export const getAssetById = ({assetId, jwt}) => async(dispatch) =>{
    dispatch({type: GET_ASSET_REQUEST})

    try {
        const response = await api.get(`/api/asset/${assetId}`, {
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });

        dispatch({
            type: GET_ASSET_SUCCESS,
            payload: response.data
        });

        console.log("get assest by id", response.data);
    } catch (error) {
        console.log("error", error);
        dispatch({
            type: GET_ASSET_FAILURE,
            error: error.message
        });
    }
};

export const getAssetDetails = ({coinId, jwt}) => async(dispatch) =>{
    dispatch({type: GET_ASSET_DETAILS_REQUEST})

    try {
        const response = await api.get(`/api/asset/coin/${coinId}/user`, {
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });

        dispatch({
            type: GET_ASSET_DETAILS_SUCCESS,
            payload: response.data
        });

        console.log("Asset details: ", response.data);
    } catch (error) {
        console.log("error", error);
        dispatch({
            type: GET_ASSET_DETAILS_FAILURE,
            error: error.message
        });
    }
};

export const getUserAssets = (jwt) => async(dispatch) =>{
    dispatch({type: GET_USER_ASSETS_REQUEST})

    try {
        const response = await api.get(`/api/asset`, {
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });

        dispatch({
            type: GET_USER_ASSETS_SUCCESS,
            payload: response.data
        });

        console.log("User Asset: ", response.data);
    } catch (error) {
        console.log("error", error);
        dispatch({
            type: GET_USER_ASSETS_FAILURE,
            error: error.message
        });
    }
};