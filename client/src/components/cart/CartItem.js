import React, { Component } from 'react';

class CartItem extends Component {
    render() {
        return(
            <div>
                <p>{this.props.item.beerName}</p>
                <p>{this.props.item.amount}</p>
            </div>
        );
    }
}

export default CartItem;