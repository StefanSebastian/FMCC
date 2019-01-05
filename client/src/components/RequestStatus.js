import React, { Component } from 'react';
import { connect } from 'react-redux';

import { AppBar, Toolbar, Typography, Grid } from '@material-ui/core';

class RequestStatus extends Component {

    render(){
        if (this.props.isLoading) {
            this.statusMessage = 'Loading...';
        } else {
            this.statusMessage = 'Idle';
        }
        if (this.props.notificationMessage === ''){
            this.notificationMessage = 'No notifications';
        } else {
            this.notificationMessage = 'New notification: ' + this.props.notificationMessage;
        }
        return(
            <div style={floatBottom}>
                <AppBar position="static">
                    <Toolbar variant="dense">
                        <Grid container spacing = {16}>

                            <Grid item>
                                <Typography variant="h6" color="inherit">
                                    Request status: {this.statusMessage}
                                </Typography>
                            </Grid>

                            <Grid item>
                                <Typography color="inherit">
                                    {this.notificationMessage}
                                </Typography>
                            </Grid>

                        </Grid>
                    </Toolbar>
                </AppBar>
            </div>
        );
    }
}

const floatBottom = {
    'position': 'fixed',
    'z-index': '100',
    'bottom': '0',
    'left': '0',
    'width': '100%'
}

const mapStateToProps = (state) => {
    return {
        isLoading: state.isLoading,
        notificationMessage: state.notificationMessage,
    }
}

export default connect(mapStateToProps)(RequestStatus);