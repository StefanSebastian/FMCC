import React, { Component } from 'react';
import { connect } from 'react-redux';

import { fetchBeers } from '../actions/getBeers';
import BeerList from './BeerList';

class AdminPage extends Component {

    componentDidMount() {
        this.props.dispatch(fetchBeers(false));
    }

    render() {
        return(
            <div className="AdminPage">
                <h2>Admin page</h2>
                <BeerList />
            </div>
        );
    }
}

export default connect()(AdminPage);