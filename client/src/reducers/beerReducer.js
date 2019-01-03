import { LOAD_BEERS_STARTED, LOAD_BEERS_FAILED, LOAD_BEERS_SUCCESS } from "../actions/getBeers";
import { UPDATE_STOCK_STARTED, UPDATE_STOCK_FAILED, UPDATE_STOCK_SUCCESS } from "../actions/updateStock";

//const demoData =  [{id: 1, name: 'Easy Rider', style: 'Session IPA', description:'Fruity, Light, Floral, Subtle, Grassy', producer: 'Ground Zero'},
//{id: 2, name: 'Morning Glory', style: 'American IPA', description:'Grapefruit, Citrus, Mango, Tropical', producer: 'Ground Zero'}];

const beerReducer = (
    state = {
        beers: [],
        isLoading: false,
        errorMessage: ''
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
                return {...state, isLoading: false, errorMessage: ''}
            
            default:
                return state;
    }
}

export default beerReducer;