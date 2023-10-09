import React from 'react';
import './AppGuest.css';
import HeaderGuest from "./headerGuest/HeaderGuest";
import Footer from "../footer/Footer";
import HomeGuest from "./home/HomeGuest";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import About from "../about/About";
import Contacts from "../contacts/Contacts";
import Terms from "../terms/Terms";
import Login from "./login/Login";
import NotFound from "../notFound/NotFound";

function AppGuest() {
    return (
        <Router>
        <div className="App">
            <Switch>
                <Route exact path="/about">
                    <div className="Content mx-auto p-2">
                        <div className="ContentCenter">
                            <div className="ContentWrap">
                                <About />
                            </div>
                        </div>
                    </div>
                </Route>
                <Route exact path="/contacts">
                    <div className="Content mx-auto p-2">
                        <div className="ContentCenter">
                            <div className="ContentWrap">
                                <Contacts />
                            </div>
                        </div>
                    </div>
                </Route>
                <Route exact path="/terms">
                    <div className="Content mx-auto p-2">
                        <div className="ContentCenter">
                            <div className="ContentWrap">
                                <Terms />
                            </div>
                        </div>
                    </div>
                </Route>
                <Route exact path="/">
                    <HomeGuest />
                </Route>
                <Route path="*">
                    <NotFound/>
                </Route>
            </Switch>
        </div>
        </Router>
    );
}

export default AppGuest;
