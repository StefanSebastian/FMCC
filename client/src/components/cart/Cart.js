import React, { Component } from 'react';
import { connect } from 'react-redux';

import { sendOrder } from '../../actions/orderBeer';
import Receipt from './Receipt';
import { Table, TableHead, TableRow, TableCell, TableBody } from '@material-ui/core';
import { demoDeadlock } from '../../actions/demoDeadlock';

class Cart extends Component {

    handleOrder = (e) => {
        e.preventDefault();
        this.props.dispatch(sendOrder(this.getAddress.value, this.props.items))
    }

    handleDemoDeadlock = (e) => {
        e.preventDefault();
        this.props.dispatch(demoDeadlock())
    }

    render() {
        return(
            <div>
                <form onSubmit={this.handleDemoDeadlock}>
                <button>Demo deadlock request</button>
                </form>

                <h1 className="cart_heading">Cart</h1>
                
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Beer Name</TableCell>
                            <TableCell align="right">Amount</TableCell>
                            <TableCell align="right">Price</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.props.items.map((item) => (
                            <TableRow key={item.beerId}>
                                <TableCell component="th" scope="row">{item.beerName}</TableCell>
                                <TableCell align="right">{item.amount}</TableCell>
                                <TableCell align="right">{item.amount * item.price}</TableCell>
                            </TableRow>
                        ))
                        }
                    </TableBody>
                </Table>

                {this.props.items.length === 0 && <p>Cart is empty</p>}

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
                <Receipt />
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