import { handleError } from "../utils/utils";

export const ADD_BEER_STARTED = 'ADD_BEER_STARTED';
export const ADD_BEER_SUCCESS = 'ADD_BEER_SUCCESS';
export const ADD_BEER_FAILED = 'ADD_BEER_FAILED';

export function addBeerStarted() {
    return {
        type: ADD_BEER_STARTED
    }
}

export function addBeerSuccess() {
    return {
        type: ADD_BEER_SUCCESS
    }
}

export function addBeerFailed(message) {
    return {
        type: ADD_BEER_FAILED,
        message
    }
}

export function addBeer(newBeer) {
    return dispatch => {
        dispatch(addBeerStarted())
        return fetch(`http://localhost:8080/shop/store`, {
            method: 'POST',
            body: JSON.stringify(newBeer),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        .then(handleError)
        .then(json => dispatch(addBeerSuccess()))
        .catch(function(error) {dispatch(addBeerFailed(error.message))})
    }
}