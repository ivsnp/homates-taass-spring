import React from 'react';
import './HomeGuest.css';
import {Button} from "react-bootstrap";

function HomeGuest() {

    if (localStorage.getItem("username") != null){
        // if there are no homes
        window.location.assign("/user/houses");
    }

    return (
        <div className="Home">
            <div className="IntroducingHoMates">
                <img src="/img/home/home1.png" alt="Join HoMates"/>
                <a href="/login"><Button className="HoMatesButton HomeButton" size="lg">Become a Mate!</Button></a>
            </div>
        </div>
    );
}

export default HomeGuest;
