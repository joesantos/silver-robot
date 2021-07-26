import "./App.css";
import { Route, BrowserRouter as Router, Switch } from "react-router-dom";
import Home from "./Home";
import Loja from "./Loja";
import ConfirmaCompra from "./ConfirmaCompra";

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={Home} />
        <Route exact path="/loja" component={Loja} />
        <Route exact path="/confirmaCompra" component={ConfirmaCompra} />
      </Switch>
    </Router>
  );
}

export default App;
