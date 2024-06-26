 import React, {useEffect, useState} from 'react';
import './Header.css';
import {Container, Nav, Navbar, Offcanvas} from "react-bootstrap";



function Header() {

    const [isLoggedOut, setIsLoggedOut] = useState(false);
    const [userName, setUserName] = useState<string | null>(null);
    const [userImage, setUserImage] = useState<string | null>(null);

    useEffect(() => {
        const storedName = localStorage.getItem("firstname");
        const storedImage = localStorage.getItem("userImage")
        if (storedName) {
            //const name = storedName.split(" ")[0];
            setUserName(storedName);
            setUserImage(storedImage);
        }



    }, []);

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
                        <Navbar.Brand>
                            <a href="/user/">
                                <img src="/img/logo/logo.svg" alt="HoMates logo" />
                            </a>
                        </Navbar.Brand>

                        <Navbar.Toggle aria-controls={`offcanvasNavbar-expand-false`} />
                        <Navbar.Offcanvas
                            id={`offcanvasNavbar-expand-false`}
                            aria-labelledby={`offcanvasNavbarLabel-expand-false`}
                            placement="start"
                        >
                            <Offcanvas.Header closeButton>
                                <Offcanvas.Title id={`offcanvasNavbarLabel-expand-false`}>
                                    Homates
                                </Offcanvas.Title>
                            </Offcanvas.Header>
                            <Offcanvas.Body>
                                <Nav className="me-auto">
                                    <Nav.Link href="/user/task/wallet">Wallet</Nav.Link>
                                    <Nav.Link href="/user/task/calendar">Calendar</Nav.Link>
                                    <Nav.Link href="/user/task/board">Board</Nav.Link>
                                    <Nav.Link href="/user/task/shopping-list">Shopping list</Nav.Link>
                                    <Nav.Link href="/user/houses">Your homes</Nav.Link>
                                </Nav>
                            </Offcanvas.Body>
                        </Navbar.Offcanvas>

                    </Container>
                </Navbar>
            </div>
            <div className="Header-right">

                <div className="Account">
                    {userName &&
                        <img src={userImage || "/img/users/user_image_default.png"} alt="User icon" style={{ width: '30px', borderRadius: '100%' }} referrerPolicy="no-referrer" className="user-image"/>
                    }
                    {userName &&
                        <a href= "/user/account" className="welcome-user">
                            {`Hi, ${userName} !`}
                        </a>
                    }
                    {/*userName &&
                        <GoogleLogout
                            clientId={clientId}
                            buttonText={"Logout"}
                            onLogoutSuccess={onSuccess}
                            className="logoutButton"
                        />*/
                    }

                </div>
            </div>
        </header>
    );
}

export default Header;
