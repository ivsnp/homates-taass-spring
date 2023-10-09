import React from 'react';
import './Footer.css';
import {Container, Nav, Navbar} from "react-bootstrap";
import {FaRegQuestionCircle} from "react-icons/fa";

function Footer() {
    const title: string = "Footer component";

    return (
        <nav className="Footer">
            <Navbar expand="lg">
                <Container>
                    <Nav className="me-auto">
                        <Nav.Link href="/about">About</Nav.Link>
                        <Nav.Link href="/contacts">Contact us</Nav.Link>
                        <Nav.Link href="/terms">Terms & conditions</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
        </nav>
    );
}

export default Footer;
