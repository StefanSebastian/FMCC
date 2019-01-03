import React, { Component } from 'react';
import { connect } from 'react-redux';

import Beer from './Beer';

class BeerList extends Component {
    render() {
        return(
            <div>
                <h1 className="beer_list_heading">Beers</h1>
                {this.props.beers.map((beer) => (
                    <div key={beer.id}>
                        <Beer key={beer.id} beer={beer} role={this.props.role} />
                    </div>
                ))}
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        beers: state.beers
    }
}

export default connect(mapStateToProps)(BeerList);