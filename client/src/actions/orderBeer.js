export const ADD_TO_CART = 'ADD_TO_CART';
export const ORDER_SENT = 'ORDER_SENT';
export const ORDER_SUCCESS = 'ORDER_SUCCESS';
export const ORDER_FAILED = 'ORDER_FAILED';

export function addToCart(item) {
    return {
        type: ADD_TO_CART,
        item
    }
}

function orderSent() {
    return {
        type: ORDER_SENT
    }
}

function orderSuccessful() {
    return {
        type: ORDER_SUCCESS
    }
}

function orderFailed(message) {
    return {
        type: ORDER_FAILED,
        message
    }
}

export function sendOrder(address, items) {
    return dispatch => {
        dispatch(orderSent())
        return fetch(`http://localhost:8080/shop/order`, {
            method: 'POST',
            body: JSON.stringify({address: address, orderItems: items}),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        .then(dispatch(orderSuccessful()))
        .catch(function(error) {dispatch(orderFailed(error.message))})
    }
}