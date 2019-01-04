import { handleError } from "../utils/utils";

export const UPDATE_STOCK_STARTED = 'UPDATE_STOCK_STARTED';
export const UPDATE_STOCK_SUCCESS = 'UPDATE_STOCK_SUCCESS';
export const UPDATE_STOCK_FAILED = 'UPDATE_STOCK_FAIL';

function updateStockStarted(beerId, additionalStock) {
    return {
        type: UPDATE_STOCK_STARTED,
        beerId,
        additionalStock
    }
}

function updateStockSuccess() {
    return {
        type: UPDATE_STOCK_SUCCESS,
    }
}

function updateStockFailed(message) {
    return {
        type: UPDATE_STOCK_FAILED,
        message
    }
}

export function updateStock(beerId, additionalStock) {
    return dispatch => {
        dispatch(updateStockStarted(beerId, additionalStock))
        return fetch(`http://localhost:8080/shop/beer/stock`, {
            method: 'PUT',
            body: JSON.stringify({'beerId': beerId, 'additionalStock': additionalStock}),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        .then(handleError)
        .then(json => dispatch(updateStockSuccess()))
        .catch(function(error) {dispatch(updateStockFailed(error.message))})
    }
}