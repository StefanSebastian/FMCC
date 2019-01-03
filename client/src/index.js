import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';

import { createStore } from 'redux';
import beerReducer from './reducers/beerReducer';
import { Provider } from 'react-redux';

const store = createStore(beerReducer);

ReactDOM.render(
<Provider store={store}>
<App />
</Provider>, 
document.getElementById('root')
);

