import React from 'react';
import './HeaderGuest.css';
import {FaRegQuestionCircle, FaRegUserCircle} from "react-icons/fa";
import {Container, Nav, Navbar} from "react-bootstrap";
import { Link } from "react-router-dom";

function HeaderGuest() {
    const title: string = "Mates";

    // @ts-ignore
    return (
        <header className="App-header">
            <div className="Header-left">
                <Navbar expand="lg">
                    <Container>
                        <Navbar.Brand>
                            <Link to="/">
                                <img src="/img/logo/logo_guest.svg" alt="HoMates logo"/>
                            </Link>
                        </Navbar.Brand>
                    </Container>
                </Navbar>
            </div>

            <div className="Header-right">

                <div className="Account">
                    <FaRegUserCircle style={{fontSize: '30px'}}/>
                    <a href="/login">Sign in | sign up</a>
                </div>
            </div>
        </header>
    );
}

export default HeaderGuest;
