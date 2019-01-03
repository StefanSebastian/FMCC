const beerReducer = (
    state = {
        beers: [{id: 1, name: 'Easy Rider', style: 'Session IPA', description:'Fruity, Light, Floral, Subtle, Grassy', producer: 'Ground Zero'},
                {id: 2, name: 'Morning Glory', style: 'American IPA', description:'Grapefruit, Citrus, Mango, Tropical', producer: 'Ground Zero'}]
    }, 
    action) => {
    switch(action.type) {
        default:
            return state;
    }
}

export default beerReducer;