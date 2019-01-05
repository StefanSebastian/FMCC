import React, { Component } from 'react';
import { connect } from 'react-redux';

import { fetchBeers } from '../actions/getBeers';
import BeerList from './BeerList';
import RequestStatus from './RequestStatus';
import { ADMIN_ROLE } from '../constants/roles';
import AddBeer from './AddBeer';

class AdminPage extends Component {

    componentDidMount() {
        console.log("Starting periodic fetch")
        this.interval = setInterval(() => this.props.dispatch(fetchBeers(false)), 1000);
    }

    componentWillUnmount() {
        console.log("Stopping periodic fetch")
        clearInterval(this.interval);
    }

    render() {
        return(
            <div className="AdminPage">
                <h2>Admin page</h2>
                <RequestStatus />
                
                <div>
                    <div style={{float: 'left', width: "48vw"}}>
                        <BeerList role={ADMIN_ROLE} />
                    </div>
                    <div style={{float: 'right', width: "48vw"}}>
                        <AddBeer />
                    </div>
                </div>
            </div>
        );
    }
}

export default connect()(AdminPage);