import React, { Component } from 'react';

class Beer extends Component {
    render() {
        return(
            <div className="beer">
                <h2 className="beer_name">{this.props.beer.name}</h2>
                <p className="beer_producer">{this.props.beer.producer}</p>
                <p className="beer_style">{this.props.beer.style}</p>
                <p className="beer_description">{this.props.beer.description}</p>
            </div>
        );
    }
}

export default Beer;