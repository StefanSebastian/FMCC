import React, { Component } from 'react';
import { connect } from 'react-redux';

import CartItem from './CartItem';

class Cart extends Component {
    render() {
        return(
            <div>
                <h1 className="cart_heading">Cart</h1>
                {this.props.items.length === 0 && <p>Cart is empty</p>}
                {this.props.items.length !== 0 && 
                    this.props.items.map((item) => (
                        <div key={item.beerId}>
                            <CartItem item={item} />
                        </div>
                    ))
                }

            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        items: state.cart
    }
}

export default connect(mapStateToProps)(Cart);