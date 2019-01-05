export const DEMO_DEADLOCK = 'DEMO_DEADLOCK';

export const demoDeadlock = () => async(dispatch, getState) => {
    const state = getState();
    console.log(state.beers);
    const firstBeer = state.beers[0];
    const secondBeer = state.beers[1];

    const firstReqBody = {
        address: "dummy",
        demoSleep: 3000,
        orderItems: [
            {
                beerId: firstBeer.id,
                beerName: firstBeer.name,
                amount: 1,
                price: firstBeer.price
            }, 
            {
                beerId: secondBeer.id,
                beerName: secondBeer.name,
                amount: 1,
                price: secondBeer.price
            }

        ]
    }

    const secondReqBody = {
        address: "dummy",
        demoSleep: 3000,
        orderItems: [
            {
                beerId: secondBeer.id,
                beerName: secondBeer.name,
                amount: 1,
                price: secondBeer.price
            },
            {
                beerId: firstBeer.id,
                beerName: firstBeer.name,
                amount: 1,
                price: firstBeer.price
            } 
        ]
    }

    try {  
        console.log("Sending first req")
        fetch(`http://localhost:8080/shop/order`, {
           method: 'POST',
           headers:  {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
            },
            body: JSON.stringify(firstReqBody) 
        })
        console.log("Sending second req")
        fetch(`http://localhost:8080/shop/order`, {
           method: 'POST',
           headers:  {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
            },
            body: JSON.stringify(secondReqBody) 
        })
    } catch(err) {
        console.log("Error in demo " + err.message)
    }

}