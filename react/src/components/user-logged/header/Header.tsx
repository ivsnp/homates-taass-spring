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
    const [userImage, setUserImage] = useState<string | null>(null);
    //var userName = localStorage.getItem("username")?.split(" ")[0] || ""; // Ottieni solo il nome dell'utente memorizzato in localStorage


    useEffect(() => {
        const storedName = localStorage.getItem("firstname");
        const storedImage = localStorage.getItem("userImage")
        if (storedName) {
            //const name = storedName.split(" ")[0];
            setUserName(storedName);
            setUserImage(storedImage);
        }



    }, []);

    const onSuccess = () => {
        console.log("Log out successfull!");
        localStorage.removeItem('username');
        localStorage.removeItem('userImage');
        setIsLoggedOut(true);
    }


    if (isLoggedOut) {
        window.location.assign("/");
        //return <Redirect to="/user/houses" />;
    }



    // @ts-ignore className="small-image" className="welcome-button"
    return (
        <header className="App-header">
            <div className="Header-left">
                <Navbar expand="lg">
                    <Container>
                        <Navbar.Toggle aria-controls="basic-navbar-nav" />
                        <Navbar.Brand>
                            <a href="/user/">
                                <img src="/img/logo/logo.svg" alt="HoMates logo" />
                            </a>
                        </Navbar.Brand>
                        <Navbar.Collapse id="basic-navbar-nav" className="navbar-style">
                            <Nav className="me-auto">
                                <Nav.Link href="/user/task/wallet">Wallet</Nav.Link>
                                <Nav.Link href="/user/task/calendar">Calendar</Nav.Link>
                                <Nav.Link href="/user/task/board">Board</Nav.Link>
                                <Nav.Link href="/user/task/shopping-list">Shopping list</Nav.Link>
                                <Nav.Link href="/user/houses">Your homes</Nav.Link>
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
            </div>
            <div className="Header-right">

                <div className="Account">
                    <div className="subAccount">

                        {userName ? (
                            <span>

                                <a href= "/user/account">{`Hi, ${userName} `} <img src={userImage || "/img/users/user_image_default.png"} alt="User icon" /></a>

                            </span>
                            // Visualizza il nome dell'utente loggato con il link
                        ) : (
                        <span> Welcome! </span> // Mostra "Loading..." mentre il nome dell'utente viene caricato
                        )}
                    </div>

                    <div className="subAccount">
                        {localStorage.getItem("username") != null && (
                            <GoogleLogout
                                clientId={clientId}
                                buttonText={"Logout"}
                                onLogoutSuccess={onSuccess}
                                className="logoutButton"
                            />
                        )}
                    </div>
                </div>
            </div>
        </header>
    );
}

export default Header;
