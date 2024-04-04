import React from 'react';
import './AppUser.css';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Houses from "./contents/houses/Houses";
import About from "../about/About";
import Terms from "../terms/Terms";
import Contacts from "../contacts/Contacts";
import NotFound from "../notFound/NotFound";
import Account from "./contents/account/Account";
import Contents from "./contents/Contents";

function AppUser() {

    return (
        <Router>
            <div className="App">

                <div className="Content mx-auto p-2">
                    <div className="ContentCenter">

                        <Switch>
                            <Route path="/user/task">
                                <Contents />
                            </Route>
                            <Route exact path="/user/houses">
                                    <Houses />
                            </Route>
                            <Route exact path="/user/account">
                                <Account />
                            </Route>
                            <Route exact path="/user/about">
                                <div className="ContentWrap">
                                    <About />
                                </div>
                            </Route>
                            <Route exact path="/user/contacts">
                                <div className="ContentWrap">
                                    <Contacts />
                                </div>
                            </Route>
                            <Route exact path="/user/terms">
                                <div className="ContentWrap">
                                    <Terms />
                                </div>
                            </Route>
                            <Route exact path="/user">
                                <Contents />
                            </Route>
                            <Route path="*">
                                <div className="ContentWrap">
                                    <NotFound/>
                                </div>
                            </Route>
                        </Switch>
                    </div>
                </div>


            </div>
        </Router>
    );
}

export default AppUser;
