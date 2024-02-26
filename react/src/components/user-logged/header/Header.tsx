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



    // @ts-ignore <img src="/img/users/user_image_default.png" alt="User icon"  style={{width: '30px', borderRadius: '100%'}}/> <img src={userImage || "/img/users/user_image_default.png"} alt="User icon" referrerPolicy="no-referrer" style={{ width: '30px', borderRadius: '100%' }} />
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

                        {userName ? (
                            <span>
                                 <img src={userImage || "/img/users/user_image_default.png"} alt="User icon" style={{ width: '30px', borderRadius: '100%' }} />
                                {`Hi, ${userName} `}<a href= "/user/account"></a>{'!'}

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
                            />
                        )}
                    </div>
                </div>
            </div>
        </header>
    );
}

export default Header;
