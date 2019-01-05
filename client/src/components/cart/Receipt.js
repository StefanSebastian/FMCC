import React, { Component } from 'react';
import { connect } from 'react-redux';
import { List, ListItemIcon, ListItem, ListItemText, Divider } from '@material-ui/core';
import InboxIcon from '@material-ui/icons/Inbox';

class Receipt extends Component {
    render(){
        return(
            <div>
            {this.props.receipt.description !== "" &&
                <div>
                <h2>Order Receipt</h2>
                <List component="nav">
                    <ListItem>
                        <ListItemIcon>
                            <InboxIcon />
                        </ListItemIcon>
                        <ListItemText primary={this.props.receipt.address} secondary="Delivery address"/>
                    </ListItem>
                    <Divider />
                    <ListItem>
                        <ListItemText primary={this.props.receipt.description} secondary="Items ordered"/>
                    </ListItem>
                    <Divider />
                    <ListItem>
                        <ListItemText primary={this.props.receipt.totalPrice} secondary="Total price"/>
                    </ListItem>
                </List>
                </div>
            }
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        receipt: state.receipt
    }
}

export default connect(mapStateToProps)(Receipt);