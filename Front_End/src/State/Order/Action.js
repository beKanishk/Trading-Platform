import { GET_ALL_ORDERS_FAILURE, GET_ALL_ORDERS_REQUEST, GET_ALL_ORDERS_SUCCESS, GET_ORDER_FAILURE, GET_ORDER_REQUEST, GET_ORDER_SUCCESS, PAY_ORDER_FAILURE, PAY_ORDER_REQUEST, PAY_ORDER_SUCCESS } from "./ActionType";
import api from "@/config/Api";

export const payOrder = ({jwt, orderData, amount}) => async(dispatch) =>{
    dispatch({type: PAY_ORDER_REQUEST})

    try {
        const response = await api.post(`/api/orders/pay`, orderData, {
            headers:{
                Authorization: `Bearer ${jwt}`
            }
        });

        dispatch({
            type: PAY_ORDER_SUCCESS,
            payload: response.data,
            amount
        });
        console.log("Order Success: ", response.data);
        
    } catch (error) {
        console.log("error", error);
        dispatch({
            type: PAY_ORDER_FAILURE,
            payload: error.message
        });
    }
};


// export const getOrderById = ({jwt, orderId}) => async(dispatch) =>{

//     dispatch({type: GET_ORDER_REQUEST})

//     try {
//         const response = await api.post(`/api/orders/${orderId}`, {
//             headers:{
//                 Authorization: `Bearer ${jwt}`
//             }
//         });

//         dispatch({
//             type: GET_ORDER_SUCCESS,
//             payload: response.data
//         });
//         //console.log("Order Success: ", response.data);
        
//     } catch (error) {
//         console.log("error", error);
//         dispatch({
//             type: GET_ORDER_FAILURE,
//             payload: error.message
//         });
//     }
// };

export const getAllOrdersForUser = ({jwt, orderType, assetSymbol}) => async(dispatch) =>{
    dispatch({type: GET_ALL_ORDERS_REQUEST})

    try {
        const response = await api.get(`/api/orders`, {
            headers:{
                Authorization: `Bearer ${jwt}`
            },
            params: {
                orderType: orderType,
                assetSymbol: assetSymbol
            }
        });

        dispatch({
            type: GET_ALL_ORDERS_SUCCESS,
            payload: response.data
        });
        console.log("Order Success: ", response.data);
        
    } catch (error) {
        console.log("error", error);
        dispatch({
            type: GET_ALL_ORDERS_FAILURE,
            payload: error.message
        });
    }
};