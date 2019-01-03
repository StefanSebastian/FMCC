import React, { Component } from 'react';
import { connect } from 'react-redux';

class RequestStatus extends Component {
    render(){
        return(
            <div>
                {this.props.isLoading && <p>Loading...</p>}
                <p>{this.props.errorMessage}</p>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        isLoading: state.isLoading,
        errorMessage: state.errorMessage
    }
}

export default connect(mapStateToProps)(RequestStatus);