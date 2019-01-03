import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';

import { createStore, applyMiddleware } from 'redux';
import thunkMiddleware from 'redux-thunk';
import beerReducer from './reducers/beerReducer';
import { Provider } from 'react-redux';

const store = createStore(beerReducer, applyMiddleware(thunkMiddleware));

ReactDOM.render(
<Provider store={store}>
<App />
</Provider>, 
document.getElementById('root')
);

