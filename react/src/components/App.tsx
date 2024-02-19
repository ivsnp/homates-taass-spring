import React, {useEffect} from 'react';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import AppUser from "./user-logged/AppUser";
import AppGuest from "./guest/AppGuest";
import './App.css';
import Header from "./user-logged/header/Header";
import HeaderGuest from "./guest/headerGuest/HeaderGuest";
import Footer from "./footer/Footer";
import Login from "./guest/login/Login";
import { gapi } from 'gapi-script';

const clientId = "903884998155-d5fqjb5mj7n5202e7qbdj3r9d3citfgj.apps.googleusercontent.com"
function App() {
    const logged: boolean = true;
    useEffect(() => {
        function start(){
            gapi.client.init({
                clientId: clientId,
                scope: ""
            })
        }
        gapi.load('client:auth2', start)
    });
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
