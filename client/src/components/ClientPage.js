import React, { Component } from 'react';
import { connect } from 'react-redux';

import BeerList from './BeerList';
import { fetchBeers } from '../actions/getBeers';

class ClientPage extends Component {

    componentDidMount() {
        this.props.dispatch(fetchBeers(true));
    }

    render() {
        return(
            <div className="ClientPage">
                <h2>Client page</h2>
                <BeerList />
            </div>
        );
    }
}

export default connect()(ClientPage);