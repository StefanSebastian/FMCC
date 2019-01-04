import { handleError } from "../utils/utils";

export const UPDATE_PRICE_STARTED = 'UPDATE_PRICE_STARTED';
export const UPDATE_PRICE_SUCCESS = 'UPDATE_PRICE_SUCCESS';
export const UPDATE_PRICE_FAILED = 'UPDATE_PRICE_FAIL';

function updatePriceStarted(beerId, newPrice) {
    return {
        type: UPDATE_PRICE_STARTED,
        beerId,
        newPrice
    }
}

function updatePriceSuccess() {
    return {
        type: UPDATE_PRICE_SUCCESS,
    }
}

function updatePriceFailed(message) {
    return {
        type: UPDATE_PRICE_FAILED,
        message
    }
}

export function updatePrice(beerId, newPrice) {
    return dispatch => {
        dispatch(updatePriceStarted(beerId, newPrice))
        return fetch(`http://localhost:8080/shop/beer/price`, {
            method: 'PUT',
            body: JSON.stringify({'beerId': beerId, 'newPrice': newPrice}),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        .then(handleError)
        .then(json => dispatch(updatePriceSuccess()))
        .catch(function(error) {dispatch(updatePriceFailed(error.message))})
    }
}