import React, { Component } from 'react';
import { connect } from 'react-redux';

class Receipt extends Component {
    render(){
        return(
            <div>
            {this.props.receipt.description !== "" &&
                <div>
                    <h2>Order Receipt</h2>
                    <p>{this.props.receipt.address}</p>
                    <p>{this.props.receipt.description}</p>
                </div>
            }
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        receipt: state.receipt
    }
}

export default connect(mapStateToProps)(Receipt);