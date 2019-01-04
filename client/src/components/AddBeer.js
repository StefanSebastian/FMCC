import React, { Component } from 'react';
import { connect } from 'react-redux';
import { addBeer } from '../actions/addBeer';

class AddBeer extends Component {

    handleAddBeer = (e) => {
        e.preventDefault();
        this.props.dispatch(addBeer({
            name: this.getName.value,
            style: this.getStyle.value,
            producer: this.getProducer.value,
            description: this.getDescription.value,
            available: this.getStock.value,
            price: this.getPrice.value
        }));
    }

    render(){
        return(
            <div>
                <h2>Add a new beer</h2>

                <form className="form" onSubmit={this.handleAddBeer}>
                    <input required type="text" 
                    ref={(input) => this.getName = input} 
                    placeholder="Name" />

                    <input required type="text" 
                    ref={(input) => this.getStyle = input} 
                    placeholder="Style" />

                    <input required type="text" 
                    ref={(input) => this.getProducer = input} 
                    placeholder="Producer" />

                    <input required type="text" 
                    ref={(input) => this.getDescription = input} 
                    placeholder="Description" />

                    <input required type="text" 
                    ref={(input) => this.getStock = input} 
                    placeholder="Stock" />

                    <input required type="text" 
                    ref={(input) => this.getPrice = input} 
                    placeholder="Price" />

                    <button>Add Beer</button>
                </form>
            </div>
        );
    }
}

export default connect()(AddBeer);