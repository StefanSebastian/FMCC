import React, { Component } from 'react';
import BeerList from './BeerList';

class ClientPage extends Component {
    render() {
        return(
            <div className="ClientPage">
                <h2>Client page</h2>
                <BeerList />
            </div>
        );
    }
}

export default ClientPage;