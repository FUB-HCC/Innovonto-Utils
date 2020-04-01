import React from 'react';
import {
  BrowserRouter as Router,
  Route,
  Switch,
} from "react-router-dom";
import './App.css';
import StartView from "./views/StartView"
import SubmitView from "./views/SubmitView"


//TODO remove this
const App = () => (
  <Router>
    <Switch>
      <Route exact path="/" component={StartView}/>
      <Route exact path="/submit" component={SubmitView}/>
    </Switch>
  </Router>
);

export default App;
