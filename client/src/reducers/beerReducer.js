import { LOAD_BEERS_STARTED, LOAD_BEERS_FAILED, LOAD_BEERS_SUCCESS } from "../actions/getBeers";

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
            default:
                return state;
    }
}

export default beerReducer;