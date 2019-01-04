import React, { Component } from 'react';
import { connect } from 'react-redux';

import BeerList from './BeerList';
import { fetchBeers } from '../actions/getBeers';
import { CLIENT_ROLE } from '../constants/roles';
import RequestStatus from './RequestStatus';
import Cart from './cart/Cart';

class ClientPage extends Component {

    componentDidMount() {
        this.props.dispatch(fetchBeers(true));
    }

    render() {
        return(
            <div className="ClientPage">
                <h2>Client page</h2>
                <RequestStatus />
                <BeerList role={CLIENT_ROLE} />
                <Cart />
            </div>
        );
    }
}

export default connect()(ClientPage);