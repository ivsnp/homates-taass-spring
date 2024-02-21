 import React, {useEffect, useState} from 'react';
import './Header.css';
import {FaRegQuestionCircle, FaRegUserCircle} from "react-icons/fa";
import { GrLogout } from "react-icons/gr";
import {Button, Col, Container, Dropdown, Nav, Row, NavDropdown, Navbar} from "react-bootstrap";
import { Link } from "react-router-dom";
import {CiStickyNote} from "react-icons/ci";
import {GoogleLogout} from 'react-google-login';
import { Redirect } from 'react-router-dom';


const clientId = "903884998155-d5fqjb5mj7n5202e7qbdj3r9d3citfgj.apps.googleusercontent.com"



function Header() {

    const title: string = "Mates";
    const [isLoggedOut, setIsLoggedOut] = useState(false);
    const [userName, setUserName] = useState<string | null>(null);
    //var userName = localStorage.getItem("username")?.split(" ")[0] || ""; // Ottieni solo il nome dell'utente memorizzato in localStorage


    useEffect(() => {
        const storedUserName = localStorage.getItem("username");
        if (storedUserName) {
            const name = storedUserName.split(" ")[0];
            setUserName(name);
        }
    }, []);

    const onSuccess = () => {
        console.log("Log out successfull!");
        localStorage.removeItem('username');
        setIsLoggedOut(true);
    }

    if (isLoggedOut) {
        return <Redirect to="/home-guest" />;
    }
    // @ts-ignore
    return (
        <header className="App-header">
            <div className="Header-left">
                <Navbar expand="lg">
                    <Container>
                        <Navbar.Toggle aria-controls="basic-navbar-nav" />
                        <Navbar.Brand>
                            <a href="/user/">
                                <img src="/img/logo/logo.svg" alt="HoMates logo"/>
                            </a>
                        </Navbar.Brand>
                        <Navbar.Collapse id="basic-navbar-nav">
                            <Nav className="me-auto">
                                <a href="/user/task/wallet">Wallet</a>
                                <a href="/user/task/calendar">Calendar</a>
                                <a href="/user/task/board">Board</a>
                                <a href="/user/task/shopping-list">Shopping list</a>
                                <a href="/user/houses">Your homes</a>
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
            </div>
            <div className="Header-right">

                <div className="Account">
                    <div className="subAccount">
                        <img src="/img/users/user_image_default.png" alt="User icon"  style={{width: '30px', borderRadius: '100%'}}/>
                        {userName ? (
                            <span>{`Hi, ${userName} `}<a href= "/user/account"></a>{'!'}</span> // Visualizza il nome dell'utente loggato con il link
                        ) : (
                        <span> Welcome! </span> // Mostra "Loading..." mentre il nome dell'utente viene caricato
                        )}
                    </div>

                    <div className="subAccount">
                        <GoogleLogout
                            clientId={clientId}
                            buttonText={"Logout"}
                            onLogoutSuccess={onSuccess}
                        />
                    </div>
                </div>
            </div>
        </header>
    );
}

export default Header;
