import React from 'react';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import AppUser from "./user-logged/AppUser";
import AppGuest from "./guest/AppGuest";
import './App.css';
import Header from "./user-logged/header/Header";
import HeaderGuest from "./guest/headerGuest/HeaderGuest";
import Footer from "./footer/Footer";
import Login from "./guest/login/Login";

function App() {
    const logged: boolean = true;

    return (
        <Router>
            <div className="App">

                <Switch>
                    <Route path="/login">
                        <div className="Content mx-auto p-2">
                            <div className="ContentCenterLogin">
                                <div className="ContentWrapLogin">
                                    <Login />
                                </div>
                            </div>
                        </div>
                    </Route>
                    <Route path="/user">
                        {logged && <Header />}
                        {!(logged) && <HeaderGuest />}
                        <AppUser />
                    </Route>
                    <Route path="/">
                        {logged && <Header />}
                        {!(logged) && <HeaderGuest />}
                        <AppGuest/>
                    </Route>
                </Switch>
                <Footer />
            </div>
        </Router>
    );
}

export default App;
