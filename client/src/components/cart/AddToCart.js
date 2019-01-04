import React, { Component } from 'react';
import { connect } from 'react-redux';

import { addToCart } from '../../actions/orderBeer';

class AddToCart extends Component {

    handleAddToCart = (e) => {
        e.preventDefault();
        this.props.dispatch(addToCart(
            {
                beerId: this.props.beerId, 
                beerName: this.props.beerName, 
                amount: this.getAmount.value,
                price: this.props.price
            })
        );
    }

    render(){
        return(
            <div>
                <form className="form" onSubmit={this.handleAddToCart}>
                    <input required type="text" 
                    ref={(input) => this.getAmount = input}
                    placeholder="Enter amount"/>
                    <button>Add to cart</button>
                </form>
            </div>
        );
    }
}
export default connect()(AddToCart);
