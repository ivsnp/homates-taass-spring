import React from 'react';
import './Login.css';
import { useState } from 'react';
import { MDBBtn } from 'mdb-react-ui-kit';
import {GoogleLogin} from 'react-google-login';
import { Redirect } from 'react-router-dom';
import axios from "axios";


const clientId = "903884998155-d5fqjb5mj7n5202e7qbdj3r9d3citfgj.apps.googleusercontent.com"
function Login() {

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useState("");
    const [userImage, setUserImage] = useState("");

    const headers = {
        "Content-Type": "application/json",
        "Accept": "application/json"
    };

    const onSuccess = (res: any) => {
        //console.log("LOGIN SUCCESS! Current user ", res.profileObj);
        const firstName = res.profileObj.givenName;
        const surnameName = res.profileObj.familyName;
        const mail = res.profileObj.email;  // get google email
        const profileImage = res.profileObj.imageUrl;  // get google image
        localStorage.setItem('username', mail);
        localStorage.setItem('firstname', firstName);
        localStorage.setItem('userImage', profileImage);
        setUserName(firstName);
        setUserImage(profileImage);
        setIsLoggedIn(true); // Set isLoggedIn to true upon successful login

        axios.post(process.env.REACT_APP_API_URL+"/api/v1/user-houses/user/"+userName, {},
            {headers})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                const newUser = {
                    username: mail,
                    name: firstName,
                    surname: surnameName,
                    email: mail
                };
                axios.post(process.env.REACT_APP_API_URL+"/api/v1/user-houses/user/create", newUser,
                    {headers})
                    .then(function (response) {
                        window.location.reload();
                    })
                    .catch(function (error) {
                        console.log("Error during login.");
                    });
            });
    }


    if (isLoggedIn) {
        return <Redirect to="/user" />;
    }
    const onFailure = (res: any) => {
        console.log("LOGIN FAILED! res: ", res);
    }

    return (
        <div className="Login">

            <a href="/"><img src="/img/logo/logo_complete.svg" alt="HoMates logo"/></a>
            <div className="text-center mb-3">
                <p>Welcome to HoMates, join us!</p>

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

            {/*<Tabs
                defaultActiveKey="login"
                id="loginTab"
                className="mb-3 HoMatesTab"
                justify>
                <Tab eventKey="login" title="Login">
                    <div className="text-center mb-3">
                        <p>Sign in with:</p>

                        <div className='d-flex justify-content-between mx-auto' style={{width: 'fit-content'}}>

                            <MDBBtn tag='a' color='none' className='m-1'>
                                {/* <FcGoogle style={{fontSize: '30px'}}/>/}
                                <GoogleLogin
                                    clientId={clientId}
                                    buttonText="Login | Signin"
                                    onSuccess={onSuccess}
                                    onFailure={onFailure}
                                    cookiePolicy={'single_host_origin'}
                                    isSignedIn={true}/>
                            </MDBBtn>
                        </div>

                        {/* <p className="text-center mt-3">or:</p> /}
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
                    </Form>
                </Tab>
                <Tab eventKey="register" title="Register">
                    <div className="text-center mb-3">
                        <p>Sign up with:</p>

                        <div className='d-flex justify-content-between mx-auto' style={{width: 'fit-content'}}>

                            <MDBBtn tag='a' color='none' className='m-1'>
                                {/* <FcGoogle style={{fontSize: '30px'}}/>/}
                                <GoogleLogin
                                    clientId={clientId}
                                    buttonText="Login"
                                    onSuccess={onSuccess}
                                    onFailure={onFailure}
                                    cookiePolicy={'single_host_origin'}
                                    isSignedIn={true}/>
                            </MDBBtn>
                        </div>

                        {/* <p className="text-center mt-3">or:</p>/}
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
                    </Form> /}
                </Tab>
            </Tabs>*/}
        </div>
    );
}

export default Login;
