import React from 'react';
import './Header.css';
import {FaRegQuestionCircle, FaRegUserCircle} from "react-icons/fa";
import { GrLogout } from "react-icons/gr";
import {Button, Col, Container, Dropdown, Nav, Row, NavDropdown, Navbar} from "react-bootstrap";
import { Link } from "react-router-dom";
import {CiStickyNote} from "react-icons/ci";

function Header() {
    const title: string = "Mates";

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
                        <a href="/user/account">Hi, Sheldon!</a>
                    </div>

                    <div className="subAccount">
                        <GrLogout className="iconHeader" color="white" style={{fontSize: '30px'}}/>
                        <a href="#">
                            Logout
                        </a>
                    </div>
                </div>
            </div>
        </header>
    );
}

export default Header;
