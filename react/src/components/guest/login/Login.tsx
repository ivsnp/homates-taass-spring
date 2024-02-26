import React from 'react';
import './Login.css';
import { useState } from 'react';
import { MDBBtn } from 'mdb-react-ui-kit';
import {FcGoogle} from "react-icons/fc";
import {Button, Container, Nav, Navbar, Tab, Tabs} from "react-bootstrap";
import Form from 'react-bootstrap/Form';
import {GoogleLogin} from 'react-google-login';
import { Redirect } from 'react-router-dom';


const clientId = "903884998155-d5fqjb5mj7n5202e7qbdj3r9d3citfgj.apps.googleusercontent.com"
function Login() {

    const title: string = "Login HoMates";
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useState("");
    const [justifyActive, setJustifyActive] = useState('tab1');
    const [userImage, setUserImage] = useState("");

    const onSuccess = (res: any) => {
        console.log("LOGIN SUCCESS! Current user ", res.profileObj);
        const firstName = res.profileObj.givenName;
        const mail = res.profileObj.email;//di tutto prendo solo il nome che viene visualizzato dall'alert
        const profileImage = res.profileObj.imageUrl;
        localStorage.setItem('username', mail); //prende le info che alert mostra
        localStorage.setItem('firstname', firstName);
        localStorage.setItem('userImage', profileImage);
        setUserName(firstName);
        setUserImage(profileImage);
        //alert(localStorage.getItem("username"));

        console.log(firstName)
        setIsLoggedIn(true); // Set isLoggedIn to true upon successful login
    }


    if (isLoggedIn) {
        return <Redirect to="/user" />;
    }
    const onFailure = (res: any) => {
        console.log("LOGIN FAILED! res: ", res);
    }
    const handleJustifyClick = (value: string) => {
        if (value === justifyActive) {
            return;
        }

        setJustifyActive(value);
    };

    return (
        <div className="Login">

            <a href="/"><img src="/img/logo/logo_complete.svg" alt="HoMates logo"/></a>

            <Tabs
                defaultActiveKey="login"
                id="loginTab"
                className="mb-3 HoMatesTab"
                justify>
                <Tab eventKey="login" title="Login">
                    <div className="text-center mb-3">
                        <p>Sign in with:</p>

                        <div className='d-flex justify-content-between mx-auto' style={{width: 'fit-content'}}>

                            <MDBBtn tag='a' color='none' className='m-1'>
                                {/* <FcGoogle style={{fontSize: '30px'}}/>*/}
                                <GoogleLogin
                                    clientId={clientId}
                                    buttonText="Login"
                                    onSuccess={onSuccess}
                                    onFailure={onFailure}
                                    cookiePolicy={'single_host_origin'}
                                    isSignedIn={true} />
                            </MDBBtn>
                        </div>

                        {/* <p className="text-center mt-3">or:</p> */}
                    </div>

                    {/*  <Form>
                        <Form.Group className="mb-3" controlId="loginUsername">
                            <Form.Control required type="text" placeholder="Username" />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="loginPassword">
                            <Form.Control required type="password" placeholder="Password" />
                        </Form.Group>

                        <div className="d-flex justify-content-between mx-4 mb-4">
                            <Form.Check type="checkbox" id="remberMe" label="Remember me" />
                            <a className="simpleLink" href="#">Forgot password?</a>
                        </div>

                        <Button type="submit" className="mb-4 w-100 HoMatesButton">
                            Sign in
                        </Button>
                    </Form>*/}
                </Tab>
                <Tab eventKey="register" title="Register">
                    <div className="text-center mb-3">
                        <p>Sign up with:</p>

                        <div className='d-flex justify-content-between mx-auto' style={{width: 'fit-content'}}>

                            <MDBBtn tag='a' color='none' className='m-1'>
                                {/* <FcGoogle style={{fontSize: '30px'}}/>*/}
                                <GoogleLogin
                                    clientId={clientId}
                                    buttonText="Login"
                                    onSuccess={onSuccess}
                                    onFailure={onFailure}
                                    cookiePolicy={'single_host_origin'}
                                    isSignedIn={true} />
                            </MDBBtn>
                        </div>

                        {/* <p className="text-center mt-3">or:</p>*/}
                    </div>

                    {/*
                    <Form>
                        <Form.Group className="mb-3" controlId="registerName">
                            <Form.Control required type="text" placeholder="Name" />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="registerUsername">
                            <Form.Control required type="text" placeholder="Username" />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="registerEmail">
                            <Form.Control required type="email" placeholder="Email" />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="registerPassword">
                            <Form.Control required type="password" placeholder="Password" />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="registerPassword2">
                            <Form.Control required type="password" placeholder="Confirm password" />
                        </Form.Group>

                        <div className="d-flex justify-content-center mb-4">
                            <Form.Check required type="checkbox" id="terms" label="I agree to the terms and conditions" />
                        </div>

                        <Button type="submit" className="mb-4 w-100 HoMatesButton">
                            Sign up
                        </Button>
                    </Form> */}
                </Tab>
            </Tabs>
        </div>
    );
}

export default Login;
