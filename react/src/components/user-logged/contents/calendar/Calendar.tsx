import React, {useState} from 'react';
import './Calendar.css';
import {Accordion, Button, Col, Container, Form, Row, Tab, Tabs, InputGroup, Spinner, Card} from "react-bootstrap";
import {CgNotes} from "react-icons/cg";
import {MdDeleteForever, MdNoteAdd} from "react-icons/md";
import axios, {AxiosResponse} from "axios";
import {CiStickyNote} from "react-icons/ci";
import {BiEditAlt} from "react-icons/bi";

function Calendar() {

    interface Event {
        id: number,
        idHouse: number,
        description: String,
        user: string,
        start: string,
        end:string,
        time:string,
        repetition:string,
        color:string
    }

    interface Calendar {
        id: number,
        idHouse: number,
        events: []
    }

    interface EventInDate {
        id: number,
        event:Event,
        date:string
    }

    const title: string = "Calendar";
    const repetition: string = "everyday";

    const [calendar, setCalendar] = useState<Calendar>();
    const [errorNewCalendar, setErrorNewCalendar] = useState('');

    const [errorNewEvent, setErrorNewEvent] = useState('');
    const [addEventDescription, setAddEventDescription] = useState('');
    const [addEventUsername, setAddEventUsername] = useState('');
    const [addEventColor, setAddEventColor] = useState('');
    const [addEventTime, setAddEventTime] = useState('');
    const [addEventStartDate, setAddEventStartDate] = useState('');
    const [addEventEndDate, setAddEventEndDate] = useState('');


    const [deleteEventDescription, setDeleteEventDescription] = useState('');
    const [errorNewEventInDate, setErrorNewEventInDate] = useState('');

    const headers = {
        "Content-Type": "application/json",
        "Accept": "application/json"
    };

    //fare il controllo che se esiste già il calendario per questa casa, il button non deve essere più visibile
    const handleSubmitNewCalendar = (event: React.MouseEvent<HTMLFormElement>) => {
        const newCalendar = {
            idHouse: localStorage.getItem("idHomeSelected")
        }
        axios.post("http://localhost:8080/api/v1/calendar/create", newCalendar,
            {headers})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                setErrorNewCalendar("Error adding new calendar, check the data and try again.");
            });
    }

    const handleSubmitNewEvent = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const newEvent = {
            description: addEventDescription,
            user: addEventUsername,
            idHouse: localStorage.getItem("idHomeSelected"),
            start: addEventStartDate,
            end: addEventEndDate,
            time: addEventTime,
            repetition:'repetition',
            color:addEventColor
        }

        axios.post("http://localhost:8080/api/v1/calendar/event/create", newEvent,
            {headers})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                setErrorNewEvent("Error adding event to calendar, check the data and try again.");
            });
    }


    if (calendar === undefined) return (
        <div>
            <Spinner animation="border" role="status" className="spinner">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    );
    return (  <div className="Calendar">
        <div className="ContentWrap">
            <button onClick={handleSubmitNewCalendar}> New Calendar </button>
            <div className="TitleContent">
                <h3>
                    {title}
                </h3>
            </div>

        </div>
    </div>);
}

export default Calendar;
