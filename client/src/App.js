import React, { Component } from 'react';
import { Route, Link, BrowserRouter as Router } from 'react-router-dom';
import ClientPage from './components/ClientPage';
import AdminPage from './components/AdminPage';

class App extends Component {
  render() {
    return (
      <Router>
        <div>
          <ul>
            <li><Link to='/client'>Client menu</Link></li>
            <li><Link to='/admin'>Admin menu</Link></li>
          </ul>

          <Route path='/client' component={ClientPage} />
          <Route path='/admin' component={AdminPage} />
        </div>
      </Router>
    );
  }
}

export default App;
