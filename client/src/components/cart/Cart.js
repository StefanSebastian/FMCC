import React, { Component } from 'react';
import { connect } from 'react-redux';

import CartItem from './CartItem';
import { sendOrder } from '../../actions/orderBeer';

class Cart extends Component {

    handleOrder = (e) => {
        e.preventDefault();
        this.props.dispatch(sendOrder(this.getAddress.value, this.props.items))
    }

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
                {this.props.items.length !== 0 && 
                    <div>
                        <form className="order_form" onSubmit={this.handleOrder}>
                            <input required type="text"
                            ref={(input) => this.getAddress = input}
                            placeholder = "Enter address"/>
                            <button>Submit order</button>
                        </form>
                    </div>
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