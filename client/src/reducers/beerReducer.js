import { LOAD_BEERS_STARTED, LOAD_BEERS_FAILED, LOAD_BEERS_SUCCESS } from "../actions/getBeers";
import { UPDATE_STOCK_STARTED, UPDATE_STOCK_FAILED, UPDATE_STOCK_SUCCESS } from "../actions/updateStock";
import { UPDATE_PRICE_STARTED, UPDATE_PRICE_FAILED, UPDATE_PRICE_SUCCESS } from "../actions/updatePrice";
import { ADD_BEER_STARTED, ADD_BEER_FAILED, ADD_BEER_SUCCESS } from "../actions/addBeer";
import { ADD_TO_CART, ORDER_SENT, ORDER_FAILED, ORDER_SUCCESS } from '../actions/orderBeer';
 
//const demoData =  [{id: 1, name: 'Easy Rider', style: 'Session IPA', description:'Fruity, Light, Floral, Subtle, Grassy', producer: 'Ground Zero'},
//{id: 2, name: 'Morning Glory', style: 'American IPA', description:'Grapefruit, Citrus, Mango, Tropical', producer: 'Ground Zero'}];

const beerReducer = (
    state = {
        beers: [],
        isLoading: false,
        errorMessage: '',
        successNotification: '',
        cart: [],
        receipt: {address: "", description: ""}
    }, 
    action) => {
        switch(action.type) {
            case LOAD_BEERS_STARTED:
                console.log('load beers started');
                return {...state, isLoading: true}
            case LOAD_BEERS_FAILED:
                console.log('load beers failed; ' + action.message);
                return {...state, isLoading: false, errorMessage: action.message}
            case LOAD_BEERS_SUCCESS:
                console.log(action.beers);
                return {...state, isLoading: false, errorMessage: '', beers: action.beers}
            
            case UPDATE_STOCK_STARTED:
                console.log('update stock started')
                return {...state, isLoading: true}
            case UPDATE_STOCK_FAILED:
                console.log('update stock failed')
                return {...state, isLoading: false, errorMessage: action.message}
            case UPDATE_STOCK_SUCCESS:
                console.log('update stock success')
                return {...state, isLoading: false, errorMessage: '', successNotification: 'Stock was updated'}
            
            case UPDATE_PRICE_STARTED:
                console.log('update price started')
                return {...state, isLoading: true}
            case UPDATE_PRICE_FAILED:
                console.log('update price failed')
                return {...state, isLoading: false, errorMessage: action.message}
            case UPDATE_PRICE_SUCCESS:
                console.log('update price success')
                return {...state, isLoading: false, errorMessage: '', successNotification: 'Update price success'}
            
            case ADD_BEER_STARTED:
                console.log('add beer started')
                return {...state, isLoading: true}
            case ADD_BEER_FAILED:
                console.log('add beer failed')
                return {...state, isLoading: false, errorMessage: action.message}
            case ADD_BEER_SUCCESS:
                console.log('add beer success')
                return {...state, isLoading: false, errorMessage: '', successNotification: 'Add beer successful'}
            
            case ADD_TO_CART:
                console.log('add to cart')
                return {...state, cart: state.cart.concat(action.item)}
            case ORDER_SENT:
                console.log('order sent')
                return {...state, isLoading: true, cart: []}
            case ORDER_FAILED:
                console.log('order failed')
                return {...state, isLoading: false, errorMessage: action.message}
            case ORDER_SUCCESS:
                console.log('order success')
                return {...state, isLoading: false, errorMessage: '', successNotification: 'Order was sent', receipt: action.receipt}

            default:
                return state;
    }
}

export default beerReducer;