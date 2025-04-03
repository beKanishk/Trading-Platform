import { FETCH_COIN_BY_ID_FAILURE, FETCH_COIN_BY_ID_REQUEST, FETCH_COIN_BY_ID_SUCCESS, FETCH_COIN_DETAILS_FAILURE, FETCH_COIN_DETAILS_REQUEST, FETCH_COIN_DETAILS_SUCCESS, FETCH_COIN_LIST_FAILURE, FETCH_COIN_LIST_REQUEST, FETCH_COIN_LIST_SUCCESS, FETCH_MARKET_CHART_FAILURE, FETCH_MARKET_CHART_REQUEST, FETCH_MARKET_CHART_SUCCESS, FETCH_TOP_50_COINS_FAILURE, FETCH_TOP_50_COINS_REQUEST, FETCH_TOP_50_COINS_SUCCESS, FETCH_TRENDING_COINS_FAILURE, FETCH_TRENDING_COINS_SUCCESS, SEARCH_COIN_FAILURE, SEARCH_COIN_REQUEST, SEARCH_COIN_SUCCESS } from "./ActionType";

const initialState = {
    coinList: [],
    top50: [],
    searchCoinList: [],
    marketChart: { data: [], loading: false},
    coinById: null,
    coinDetails: null,
    loading: false,
    error: null,
    trending: [],
}

const coinReducer = (state = initialState,
    action) => {
    switch (action. type) {
        case FETCH_COIN_LIST_REQUEST:
        case FETCH_COIN_BY_ID_REQUEST:
        case FETCH_COIN_DETAILS_REQUEST:
        case SEARCH_COIN_REQUEST:
        case FETCH_TOP_50_COINS_REQUEST:
            return {...state, loading: true, error: null}

        case FETCH_MARKET_CHART_REQUEST:
            
            
            return {
                ...state,
                marketChart: {loading: true, data: []},
                error: null
            };
        
        case FETCH_COIN_LIST_SUCCESS:
            
            return {
                ...state,
                coinList: action.payload,
                loading: false,
                error: null
            };
            

        case FETCH_TOP_50_COINS_SUCCESS:
            return {
                ...state,
                top50: action.payload,
                loading: false,
                error: null
            };
        case FETCH_TRENDING_COINS_SUCCESS:
            return {
                ...state,
                trending: action.payload,
                loading: false,
                error: null
            };
        case FETCH_MARKET_CHART_SUCCESS:
            //console.log("market chart success :", action.payload.price);
            return {
                ...state,
                marketChart: {data: action.payload.prices, loading: false},
                error: null
            }
        // case FETCH_COIN_BY_ID_SUCCESS:
        //     return {
        //         ...state,
        //         coinDetai1s: action.payload,
        //         loading: false,
        //         error: null
        //     }

        case SEARCH_COIN_SUCCESS:
            return{
                ... state,
                searchCoinList: action.payload.coins,
                loading: false,
                error: null,
            }

        case FETCH_COIN_DETAILS_SUCCESS:
            return {
                ... state,
                coinDetails: action.payload,
                loading: false,
                error: null,
            }
        case FETCH_MARKET_CHART_FAILURE:
            return {
                ...state,
                marketChart: { loading: false, data: [] },
                error: action.payload,
            }
        case FETCH_COIN_LIST_FAILURE:
        case FETCH_TOP_50_COINS_FAILURE:
        case FETCH_COIN_BY_ID_FAILURE:
        case FETCH_COIN_DETAILS_FAILURE:
        case SEARCH_COIN_FAILURE:
        case FETCH_TRENDING_COINS_FAILURE:
            return {
                ...state,
                loading: false,
                error: action.payload
            }
        default: 
            return state;
    }
};

export default coinReducer;