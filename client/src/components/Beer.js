import React, { Component } from 'react';
import { ADMIN_ROLE, CLIENT_ROLE } from '../constants/roles';
import StockUpdate from './StockUpdate';

class Beer extends Component {
    render() {
        return(
            <div className="beer">
                <h2 className="beer_name">{this.props.beer.name}</h2>
                <p className="beer_producer">{this.props.beer.producer}</p>
                <p className="beer_style">{this.props.beer.style}</p>
                <p className="beer_description">{this.props.beer.description}</p>
                <p className="beer_price">{this.props.beer.price}</p>
                <p className="beer_available">{this.props.beer.available}</p>
                
                {this.props.role === ADMIN_ROLE && <StockUpdate beerId={this.props.beer.id} />}
                {this.props.role === CLIENT_ROLE && <p>client role</p>}
            </div>
        );
    }
}

export default Beer;