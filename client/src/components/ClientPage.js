import React, { Component } from 'react';
import { connect } from 'react-redux';

import BeerList from './BeerList';
import { fetchBeers } from '../actions/getBeers';
import { CLIENT_ROLE } from '../constants/roles';
import RequestStatus from './RequestStatus';
import Cart from './cart/Cart';

class ClientPage extends Component {

    componentDidMount() {
        console.log("Starting periodic fetch")
        this.interval = setInterval(() => this.props.dispatch(fetchBeers(true)), 1000);
    }

    componentWillUnmount() {
        console.log("Stopping periodic fetch")
        clearInterval(this.interval);
    }

    render() {
        return(
            <div className="ClientPage">
                <h2>Client page</h2>
                <RequestStatus />
               
                <div>
                    <div style={{float: 'left', width: "48vw"}}>
                        <BeerList role={CLIENT_ROLE} />
                    </div>
                    <div style={{float: 'right', width: "48vw"}}>
                        <Cart />
                    </div>
                </div>
            </div>
        );
    }
}

export default connect()(ClientPage);