export const LOAD_BEERS_STARTED = 'LOAD_BEERS_STARTED';
export const LOAD_BEERS_SUCCESS = 'LOAD_BEERS_SUCCESS';
export const LOAD_BEERS_FAILED = 'LOAD_BEERS_FAIL';

function loadBeersStarted(availableFilter) {
    return {
        type: LOAD_BEERS_STARTED,
        availableFilter
    }
}

function loadBeersSuccess(json) {
    return {
        type: LOAD_BEERS_SUCCESS,
        beers: json
    }
}

function loadBeersFailed(message) {
    return {
        type: LOAD_BEERS_FAILED,
        message
    }
}

export function fetchBeers(availableFilter) {
    return dispatch => {
        dispatch(loadBeersStarted(availableFilter))
        return fetch(`http://localhost:8080/shop/beer?availableFilter=${availableFilter}`)
            .then(response => response.json())
            .then(json => dispatch(loadBeersSuccess(json)))
            .catch (function(error) {dispatch(loadBeersFailed(error.message))})
    }
}