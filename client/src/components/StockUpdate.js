import React, { Component } from 'react';
import { connect } from 'react-redux';
import { updateStock } from '../actions/updateStock';

class StockUpdate extends Component {
    
    handleStockUpdate = (e) => {
        e.preventDefault();
        this.props.dispatch(updateStock(this.props.beerId, this.getStock.value));
    }
    
    render(){
        return(
            <div>
                <form className="stock_form" onSubmit={this.handleStockUpdate}>
                    <input required type="text" 
                    ref={(input) => this.getStock = input}
                    placeholder = "Enter additional stock"
                    />
                    <button>Submit</button>
                </form>
            </div>
        );
    }
}

export default connect()(StockUpdate);