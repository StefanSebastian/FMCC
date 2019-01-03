import React, { Component } from 'react';
import { connect } from 'react-redux';

import { fetchBeers } from '../actions/getBeers';
import BeerList from './BeerList';
import RequestStatus from './RequestStatus';
import { ADMIN_ROLE } from '../constants/roles';

class AdminPage extends Component {

    componentDidMount() {
        this.props.dispatch(fetchBeers(false));
    }

    render() {
        return(
            <div className="AdminPage">
                <h2>Admin page</h2>
                <RequestStatus />
                <BeerList role={ADMIN_ROLE} />
            </div>
        );
    }
}

export default connect()(AdminPage);