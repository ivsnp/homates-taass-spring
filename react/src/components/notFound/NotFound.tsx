import React from 'react';
import './NotFound.css';
import {TbError404} from "react-icons/tb";

function NotFound() {
    const title: string = "Page not found.";

    return (
        <div className="NotFound">
            <TbError404 style={{fontSize: '70px'}}/>
            <h1>{title}</h1>
        </div>
    );
}

export default NotFound;