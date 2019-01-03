import React, { Component } from 'react';
import { connect } from 'react-redux';
import { updatePrice } from '../actions/updatePrice';

class PriceUpdate extends Component {
    
    handlePriceUpdate = (e) => {
        e.preventDefault();
        this.props.dispatch(updatePrice(this.props.beerId, this.getPrice.value));
    }
    
    render(){
        return(
            <div>
                <form className="price_form" onSubmit={this.handlePriceUpdate}>
                    <input required type="text" 
                    ref={(input) => this.getPrice = input}
                    placeholder = "Enter new price"
                    />
                    <button>Submit</button>
                </form>
            </div>
        );
    }
}

export default connect()(PriceUpdate);