import React, { Component } from 'react';
import Card from '@material-ui/core/Card';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import { CardContent, CardActions, IconButton, Collapse } from '@material-ui/core';
import classnames from 'classnames';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

import { ADMIN_ROLE, CLIENT_ROLE } from '../constants/roles';
import StockUpdate from './StockUpdate';
import PriceUpdate from './PriceUpdate';
import AddToCart from './cart/AddToCart';

class Beer extends Component {
    constructor(props) {
        super(props);
        this.state = { expanded: false };
    }

    handleExpandClick = () => {
        this.setState(state => ({expanded: !state.expanded}))
    }

    render() {
        const { classes } = this.props;

        return(
            <Card className={classes.card}>
                <CardContent>
                    <Typography className={classes.title} color="textSecondary" gutterBottom>
                        {this.props.beer.style}
                    </Typography>
                        
                    <Typography className={classes.pos} variant="h5" component="h2">
                        {this.props.beer.name}
                    </Typography>
                   
                    <Typography className={classes.pos} color="textSecondary">
                        Producer: {this.props.beer.producer}
                    </Typography>
                   
                    <Typography component="p">
                        {this.props.beer.description}
                    </Typography>

                    <Typography component="p">
                        Price: {this.props.beer.price}
                    </Typography>

                    <Typography component="p">
                        Available: {this.props.beer.available}
                    </Typography>

                    <CardActions className={classes.actions} disableActionSpacing>
                        <IconButton
                            className={classnames(classes.expand, {
                            [classes.expandOpen]: this.state.expanded,
                            })}
                            onClick={this.handleExpandClick}
                            aria-expanded={this.state.expanded}
                            aria-label="Show more"
                        >
                        <ExpandMoreIcon />
                        </IconButton>
                    </CardActions>
                    
                    <Collapse in={this.state.expanded} timeout="auto" unmountOnExit>
                        {this.props.role === ADMIN_ROLE && 
                        <div>
                            <StockUpdate beerId={this.props.beer.id} />
                            <PriceUpdate beerId={this.props.beer.id} />
                        </div>
                        }
                        {this.props.role === CLIENT_ROLE && 
                        <AddToCart beerId={this.props.beer.id}
                                beerName={this.props.beer.name} 
                                price={this.props.beer.price} />
                        }
                    </Collapse>
                </CardContent>
            </Card>
        );
    }
}

const styles = theme => ({
    card: {
      //height: "50vh",
     // width: "30vw",
     // float: "left"
    },
    bullet: {
      display: 'inline-block',
      margin: '0 2px',
      transform: 'scale(0.8)',
    },
    title: {
      fontSize: 14,
    },
    pos: {
      marginBottom: 12,
    },
    actions: {
        display: 'flex',
    },
    expand: {
        transform: 'rotate(0deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
          duration: theme.transitions.duration.shortest,
        }),
      },
      expandOpen: {
        transform: 'rotate(180deg)',
      }
});

export default withStyles(styles)(Beer);