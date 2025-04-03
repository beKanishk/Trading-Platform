import api from "@/config/Api";
import { ADD_PAYMENT_DETAILS_FAILURE, ADD_PAYMENT_DETAILS_REQUEST, ADD_PAYMENT_DETAILS_SUCCESS, GET_PAYMENT_DETAILS_FAILURE, GET_PAYMENT_DETAILS_REQUEST, GET_PAYMENT_DETAILS_SUCCESS, GET_WITHDRAWAL_HISTORY_FAILURE, GET_WITHDRAWAL_HISTORY_REQUEST, GET_WITHDRAWAL_HISTORY_SUCCESS, GET_WITHDRAWAL_REQUEST_REQUEST, GET_WITHDRAWAL_REQUEST_SUCCESS, WITHDRAWAL_FAILURE, WITHDRAWAL_PROCEED_FAILURE, WITHDRAWAL_PROCEED_REQUEST, WITHDRAWAL_PROCEED_SUCCESS, WITHDRAWAL_REQUEST, WITHDRAWAL_SUCCESS } from "./ActionType";

export const withdrawalRequest = ({amount, jwt}) => async(dispatch) =>{
    dispatch({type: WITHDRAWAL_REQUEST})

    try {
        const response = await api.post(`/api/withdrawal/${amount}`, null, {
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });

        console.log("Withdrawal..", response.data);
        dispatch({
            type: WITHDRAWAL_SUCCESS,
            payload: response.data
        });
    } catch (error) {
        console.log("error", error);
        dispatch({
            type: WITHDRAWAL_FAILURE,
            payload: error.message
        });
    }
};

export const proceedWithdrawal = ({id, jwt, accept}) => async(dispatch) =>{
    dispatch({type: WITHDRAWAL_PROCEED_REQUEST})

    try {
        const response = await api.post(`/api/admin/withdrawal/${id}/proceed/${accept}`, null, {
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });

        console.log("Proceed Withdrawal..", response.data);
        dispatch({
            type: WITHDRAWAL_PROCEED_SUCCESS,
            payload: response.data
        });
    } catch (error) {
        console.log("error", error);
        dispatch({
            type: WITHDRAWAL_PROCEED_FAILURE,
            payload: error.message
        });
    }
};

export const getWithdrawalHistory = ({jwt}) => async(dispatch) =>{
    dispatch({type: GET_WITHDRAWAL_HISTORY_REQUEST})

    try {
        const response = await api.get(`/api/withdrawal`, {
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });

        console.log("Withdrawal history..", response.data);
        dispatch({
            type: GET_WITHDRAWAL_HISTORY_SUCCESS,
            payload: response.data
        });
    } catch (error) {
        console.log("error", error);
        dispatch({
            type: GET_WITHDRAWAL_HISTORY_FAILURE,
            payload: error.message
        });
    }
};

export const getAllWithdrawalRequest = ({jwt}) => async(dispatch) =>{
    dispatch({type: GET_WITHDRAWAL_REQUEST_REQUEST})

    try {
        const response = await api.get(`/api/admin/withdrawal`, {
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });

        console.log("Get Withdrawal Request..", response.data);
        dispatch({
            type: GET_WITHDRAWAL_REQUEST_SUCCESS,
            payload: response.data
        });
    } catch (error) {
        console.log("error", error);
        dispatch({
            type: GET_WITHDRAWAL_REQUEST_FAILURE,
            payload: error.message
        });
    }
};

export const addPaymentDetails = ({paymentDetails, jwt}) => async(dispatch) =>{
    dispatch({type: ADD_PAYMENT_DETAILS_REQUEST})

    try {
        const response = await api.post(`/api/add/payment-details`, paymentDetails, {
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });

        console.log("Add Payment details..", response.data);
        dispatch({
            type: ADD_PAYMENT_DETAILS_SUCCESS,
            payload: response.data
        });
    } catch (error) {
        console.log("error", error);
        dispatch({
            type: ADD_PAYMENT_DETAILS_FAILURE,
            payload: error.message
        });
    }
};

export const getPaymentDetails = ({jwt}) => async(dispatch) =>{
    dispatch({type: GET_PAYMENT_DETAILS_REQUEST})

    try {
        const response = await api.get(`/api/payment-details`, {
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });

        console.log("Get Payment details..", response.data);
        
        localStorage.setItem('paymentDetails', JSON.stringify(response.data));
        dispatch({
            type: GET_PAYMENT_DETAILS_SUCCESS,
            payload: response.data
        });
    } catch (error) {
        console.log("error", error);
        dispatch({
            type: GET_PAYMENT_DETAILS_FAILURE,
            payload: error.message
        });
    }
};