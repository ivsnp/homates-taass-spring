import React, {useState} from 'react';
import './Calendar.css';
import {
    Accordion,
    Button,
    Col,
    Container,
    Form,
    Row,
    Tab,
    Tabs,
    InputGroup,
    Spinner,
    Card,
    DropdownButton,
    Dropdown, Stack, Modal
} from "react-bootstrap";
import {CgNotes} from "react-icons/cg";
import {MdDeleteForever, MdNoteAdd} from "react-icons/md";
import axios, {AxiosResponse} from "axios";
import {CiStickyNote} from "react-icons/ci";
import {BiEditAlt} from "react-icons/bi";
import {LiaCartArrowDownSolid, LiaCartPlusSolid} from "react-icons/lia";


function Calendar() {

    interface Event {
        id: number,
        idHouse: number,
        description: string,
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
    interface Freq {
        day_month: number
    }

    const title: string = "Calendar";
    const [roommates,setRoommates] = useState<String[]>();
    const [selectedRepetition, setSelectedRepetition] = useState("");
    const [frequency, setFrequency] = useState(0);
    const daysOfWeek = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
    const months = ['All','January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    // Get the current date in the format "YYYY-MM-DD"
    const currentDate = new Date().toISOString().split('T')[0];

    const [show, setShow] = useState(false);
    const [ev, setEv] = useState(0);
    const handleShow = (event: React.MouseEvent<HTMLButtonElement>,id: number) => {
        setEv(id);
        setAddEventDescription(null);
        setAddEventUsername(null);
        setAddEventEndDate(null);
        setAddEventStartDate(null);
        setAddEventColor(null);
        setAddEventTime(null);
        setAddEventRepetition(null);
        setFrequency(0);

        setShow(true);
    }
    const handleClose = () => setShow(false);


    const [calendar, setCalendar] = useState<Calendar>();
    const [errorNewCalendar, setErrorNewCalendar] = useState('');
    const [events, setEvents] = useState<EventInDate[]>([]);
    const [editListEvents, setEditListEvents] = useState<{[idItem: string]: boolean}> ({});


    const [errorNewEvent, setErrorNewEvent] = useState('');
    const [addEventDescription, setAddEventDescription] = useState<string |null>('');
    const [addEventUsername, setAddEventUsername] = useState<string | null>('');
    const [addEventColor, setAddEventColor] = useState<string | null>('#00BFFF');
    const [addEventTime, setAddEventTime] = useState<string | null>('');
    const [addEventStartDate, setAddEventStartDate] = useState<string | null>(currentDate);
    const [addEventEndDate, setAddEventEndDate] = useState<string | null>('');
    const [addEventRepetition, setAddEventRepetition] = useState<string | null>('');

    const groupedEvents = events.reduce((groups: {[key: string]: EventInDate[]}, event) => {
        const date = event.date.split('T')[0]; // assuming date is in ISO format
        const formattedDate = new Date(date).toLocaleDateString('en-US', {
            day: 'numeric', month: 'short', year: 'numeric'
        });
        if (!groups[formattedDate]) {
            groups[formattedDate] = [];
        }
        groups[formattedDate].push(event);
        return groups;
    }, {});

    const headers = {
        "Content-Type": "application/json",
        "Accept": "application/json"
    };


    const handleSubmitNewCalendar = (event: React.MouseEvent<HTMLFormElement>) => {
        event.preventDefault();
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
            repetition: addEventRepetition,
            color:addEventColor
        }
        console.log(newEvent);  // TODO: comment

        axios.post("http://localhost:8080/api/v1/calendar/event/create", newEvent,
            {headers})
            .then(function (response) {
                const resp = response.data
                //console.log(resp)
                switch (resp.repetition) {
                    case "everyday":
                        axios.post("http://localhost:8080/api/v1/calendar/add_event/everyday/"+resp.id,
                            {headers})
                            .then(function (response) {
                                window.location.reload();
                            })
                            .catch(function (error) {
                                setErrorNewEvent("Error adding event to calendar, check the data and try again.");
                            });
                        break;
                    case "weekly":
                        var info = {day_month : frequency}
                        axios.post("http://localhost:8080/api/v1/calendar/add_event/weekly/"+resp.id, info,
                            {headers})
                            .then(function (response) {
                                window.location.reload();
                            })
                            .catch(function (error) {
                                setErrorNewEvent("Error adding event to calendar, check the data and try again.");
                            });
                        break;
                    case "monthly":
                        var info = {day_month : frequency}
                        axios.post("http://localhost:8080/api/v1/calendar/add_event/monthly/"+resp.id, info,
                            {headers})
                            .then(function (response) {
                                window.location.reload();
                            })
                            .catch(function (error) {
                                setErrorNewEvent("Error adding event to calendar, check the data and try again.");
                            });
                        break;
                    case "yearly":
                        axios.post("http://localhost:8080/api/v1/calendar/add_event/yearly/"+resp.id,
                            {headers})
                            .then(function (response) {
                                window.location.reload();
                            })
                            .catch(function (error) {
                                setErrorNewEvent("Error adding event to calendar, check the data and try again.");
                            });
                        break;
                    // default:
                        //     console.log(frequency);
                        //     console.log(addEventRepetition);
                }
            })
            .catch(function (error) {
                setErrorNewEvent("Error adding event to calendar, check the data and try again.");
            });

    }

    const handleDeleteEventInDate = (id: number) => {
        //event.preventDefault(); // reload page after submit
        const house = localStorage.getItem("idHomeSelected");

        axios.delete("http://localhost:8080/api/v1/calendar/events_in_date/delete/"+id+"/"+house, {})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error)
            });
    }
    const handleDeleteEvent = (id: number) => {
        //event.preventDefault(); // reload page after submit

        axios.delete("http://localhost:8080/api/v1/calendar/event/delete/"+id, {})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error)
            });
    }


    const handleSaveEditEvent = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, id: number) => {
        event.preventDefault(); // reload page after submit

        const editEvent = {
            description: addEventDescription,
            user: addEventUsername,
            start: addEventStartDate,
            end:addEventEndDate,
            time:addEventTime,
            repetition:addEventRepetition,
            color:addEventColor,
            frequency:frequency
        }
        // console.log(editEvent);

            axios.put("http://localhost:8080/api/v1/calendar/event/update/" + id, editEvent)
                .then(function (response) {
                    window.location.reload();
                })
                .catch(function (error) {
                    console.log(error)
                });

        setShow(false);

    }

    const handleClickCombined = (rep: string,freq:number) => {
        setAddEventRepetition(rep);
        setFrequency(freq);
        setSelectedRepetition(rep);

    }



    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/user-houses/houses/rommates/"+localStorage.getItem("idHomeSelected"), {
            headers: {}})
            .then((response: AxiosResponse<string[]>) => {
                if (response.data === undefined || response.data.length == 0){
                    // if there are no roommates
                    setRoommates([]);
                } else {
                    setRoommates(response.data);

                    // setting default usernames
                    setAddEventUsername(response.data[0]);
                }

            })
            .catch(error => {
                console.log(error)
            });
    }, [localStorage.getItem("idHomeSelected")]);


    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/calendar/my-calendar/"+localStorage.getItem("idHomeSelected"), {
            headers: {}})
            .then((response: AxiosResponse<Calendar>) => {
                    setCalendar(response.data);

                    if (response.data.events === undefined || response.data.events.length == 0){
                        setEvents([]);

                    } else {
                        const now = new Date();
                        now.setDate(now.getDate() - 1);
                        const filteredEvents = (response.data.events as EventInDate[]).filter(event => new Date(event.date) >= now);
                        filteredEvents.sort((a, b) => a.date.localeCompare(b.date));
                        setEvents(filteredEvents);
                    }

                    const eList: {[idItem: number]: boolean} = {}
                    for (const key in response.data.events){
                        eList[key] = false;
                    }

                    setEditListEvents(eList);

            })
            .catch(error => {
                console.log(error)
                //setCalExists(false);
            });
    }, [localStorage.getItem("idHomeSelected")]);


    if (roommates === undefined || calendar === undefined) return (
        <div>
            <Spinner animation="border" role="status" className="spinner">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    );


    if ( events === undefined ) return (

        <div className="Calendar">
            <div className="ContentWrap">

                <Accordion>

                    <Form onSubmit={handleSubmitNewCalendar}>
                        <div className="errorMessage">
                            {errorNewCalendar}
                        </div>

                        <Button type="submit" className="mb-4 w-100 HoMatesButton">
                            Add Calendar
                        </Button>
                    </Form>

                </Accordion>
                <h3>
                    {title}
                </h3>
            </div>
        </div>
    );

    return (
        <div className="Calendar">
            <div className="ContentWrap">

                    <Accordion>
                        <Accordion.Item eventKey="0">
                            <Accordion.Header>
                                <Container>
                                    <Row>
                                        <Col xs={2}><LiaCartArrowDownSolid style={{fontSize: '30px'}}/></Col>
                                        <Col className="d-flex align-items-center">Add an event</Col>
                                    </Row>
                                </Container>
                            </Accordion.Header>

                                <Accordion.Body>
                                    <Form onSubmit={handleSubmitNewEvent}>
                                        <Form.Group className="mb-3" controlId="descriptionEvent">
                                            <Form.Control required type="text" placeholder="Description"
                                                          onChange={e => setAddEventDescription(e.target.value)}/>
                                        </Form.Group>
                                        <InputGroup className="mb-3">
                                            <InputGroup.Text id="startDateEvent">Start Date:</InputGroup.Text>
                                                <Form.Control required type="date" placeholder="Start Date" min={currentDate}
                                                              onChange={e => setAddEventStartDate(e.target.value)}/>
                                        </InputGroup>
                                        <InputGroup className="mb-3">
                                            <InputGroup.Text id="endDateEvent">End Date:</InputGroup.Text>
                                            <Form.Control required type="date" placeholder="End Date" min={currentDate}
                                                          onChange={e => setAddEventEndDate(e.target.value)}/>
                                        </InputGroup>
                                        <InputGroup className="mb-3">
                                            <InputGroup.Text id="timeEvent">Time:</InputGroup.Text>
                                            <Form.Control required type="time" placeholder="Time"
                                                          onChange={e => setAddEventTime(e.target.value)}/>
                                        </InputGroup>
                                        <Form.Group className="mb-3" controlId="colorEvent">
                                            <Form.Control required type="color" placeholder="Color" defaultValue="#00BFFF"
                                                          onChange={e => setAddEventColor(e.target.value)}/>
                                        </Form.Group>
                                        <InputGroup className="mb-3">
                                            <Form.Select required aria-label="Default select example"
                                                         className="HomeSelectionSelect" size="sm"
                                                         onChange={e => setAddEventUsername(e.target.value)}>
                                                {roommates.map((roommate) => (
                                                    //@ts-ignore
                                                    <option value={roommate} key={roommate}>{roommate}</option>
                                                ))}
                                            </Form.Select>
                                        </InputGroup>

                                        <InputGroup className="mb-3">
                                            <InputGroup.Text id="repetition">Repetition:</InputGroup.Text>
                                            <Button
                                                variant={selectedRepetition === "everyday" ? 'primary' : 'outline-secondary'}
                                                onClick={() => handleClickCombined("everyday",0)}
                                            >
                                                everyday
                                            </Button>
                                            <DropdownButton
                                                variant={selectedRepetition === "weekly" ? 'primary' : 'outline-secondary'}
                                                title="weekly"
                                                id="rep_weekly"
                                            >
                                                {daysOfWeek.map((rep, index) => (
                                                    <Dropdown.Item key={index} onClick={() => handleClickCombined("weekly",index+1)}>
                                                        {rep}
                                                    </Dropdown.Item>
                                                ))}
                                            </DropdownButton>
                                            <DropdownButton
                                                variant={selectedRepetition === "monthly" ? 'primary' : 'outline-secondary'}
                                                title="monthly"
                                                id="rep_monthly"
                                            >
                                                {months.map((rep, index) => (
                                                    <Dropdown.Item key={index} onClick={() => handleClickCombined("monthly",index)}>
                                                        {rep}
                                                    </Dropdown.Item>
                                                ))}
                                            </DropdownButton>
                                            <Button
                                                variant={selectedRepetition === "yearly" ? 'primary' : 'outline-secondary'}
                                                onClick={() => handleClickCombined("yearly",0)}
                                            >
                                                yearly
                                            </Button>
                                        </InputGroup>
                                        <div className="errorMessage">
                                            {errorNewEvent}
                                        </div>

                                        <Button type="submit" className="mb-4 w-100 HoMatesButton">
                                            Add Event
                                        </Button>
                                    </Form>
                                </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>



                <h3>
                    {title}
                </h3>
                <Container>
                    {Object.entries(groupedEvents).map(([date, events]) => (

                        <Row key={date}>
                            <h4>{date}</h4>
                            <Col>
                        {events.map((event,index) => (
                            <Card body style={{backgroundColor: event.event.color}}>
                                <Container>

                                    {!editListEvents[event.id] &&
                                        <Row>
                                            <Col>
                                                <div className="">
                                                    <strong>User:</strong>&nbsp;{event.event.user}
                                                </div>
                                                <div className="">
                                                    <strong>To Do:</strong>&nbsp;{event.event.description}
                                                </div>
                                                <div className="date">
                                                    <strong>When:</strong>&nbsp;{event.date}
                                                </div>
                                                <div className="time">
                                                    <strong>Time:</strong>&nbsp;{event.event.time}
                                                </div>
                                            </Col>
                                            <Col xs={3} className="d-flex align-items-center">
                                                <Button className="action-button" onClick={(e) => {handleShow(e,index)}}>
                                                    <BiEditAlt style={{fontSize: '30px', color: '#000'}}/>
                                                </Button>&nbsp;

                                                <DropdownButton className = "dropdown-Button" title={<MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>} variant="red">
                                                    <Dropdown.Item  onClick={() => {handleDeleteEventInDate(event.id);}}>
                                                           Delete date
                                                    </Dropdown.Item>
                                                    <Dropdown.Item  onClick={() => {handleDeleteEvent(event.event.id);}}>
                                                        Delete event
                                                    </Dropdown.Item>
                                                </DropdownButton>
                                            </Col>
                                        </Row>
                                    }
                                </Container>
                            </Card>
                        ))}
                    </Col>
                    </Row>
                    ))}
                    {events.length > 0 &&
                        <Modal show={show} onHide={handleClose}>
                            <Modal.Header closeButton>
                                <Modal.Title>Edit Event</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <Form>
                                    <InputGroup className="mb-3">
                                        <Form.Control  type="text" placeholder="Description" defaultValue={(events[ev] === undefined) ? "":events[ev].event.description}
                                                      onChange={e => setAddEventDescription(e.target.value)}/>
                                    </InputGroup>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Text id="startDateEvent">Start Date:</InputGroup.Text>
                                        <Form.Control  type="date" placeholder="Start Date" min={currentDate} defaultValue={(events[ev] === undefined) ? "":events[ev].event.start}
                                                      onChange={e => setAddEventStartDate(e.target.value)}/>
                                    </InputGroup>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Text id="endDateEvent">End Date:</InputGroup.Text>
                                        <Form.Control  type="date" placeholder="End Date" min={currentDate} defaultValue={(events[ev] === undefined) ? "":events[ev].event.end}
                                                      onChange={e => setAddEventEndDate(e.target.value)}/>
                                    </InputGroup>
                                    <InputGroup className="mb-3">
                                        <InputGroup.Text id="timeEvent">Time:</InputGroup.Text>
                                        <Form.Control  type="time" placeholder="Time" defaultValue={(events[ev] === undefined) ? "":events[ev].event.time}
                                                      onChange={e => setAddEventTime(e.target.value)}/>
                                    </InputGroup>
                                    <InputGroup className="mb-3">
                                        <Form.Control  type="color" placeholder="Color"  defaultValue={(events[ev] === undefined) ? "":events[ev].event.color}
                                                      onChange={e => setAddEventColor(e.target.value)}/>
                                    </InputGroup>
                                    <InputGroup className="mb-3">
                                        <Form.Select  aria-label="Default select example"
                                                     className="HomeSelectionSelect" size="sm" defaultValue={(events[ev] === undefined) ? "":events[ev].event.user}
                                                     onChange={e => setAddEventUsername(e.target.value)}>
                                            {roommates.map((roommate) => (
                                                //@ts-ignore
                                                <option value={roommate} key={roommate}>{roommate}</option>
                                            ))}
                                        </Form.Select>
                                    </InputGroup>

                                    <InputGroup className="mb-3">
                                        <InputGroup.Text id="repetition">Repetition:</InputGroup.Text>
                                        <Button
                                            variant={addEventRepetition === "everyday" ? 'primary' : 'outline-secondary'}
                                            onClick={() => handleClickCombined("everyday",0)}
                                        >
                                            everyday
                                        </Button>
                                        <DropdownButton
                                            variant={addEventRepetition === "weekly" ? 'primary' : 'outline-secondary'}
                                            title="weekly"
                                            id="rep_weekly"
                                        >
                                            {daysOfWeek.map((rep, index) => (
                                                <Dropdown.Item key={index} onClick={() => handleClickCombined("weekly",index+1)}>
                                                    {rep}
                                                </Dropdown.Item>
                                            ))}
                                        </DropdownButton>
                                        <DropdownButton
                                            variant={addEventRepetition === "monthly" ? 'primary' : 'outline-secondary'}
                                            title="monthly"
                                            id="rep_monthly"
                                        >
                                            {months.map((rep, index) => (
                                                <Dropdown.Item key={index} onClick={() => handleClickCombined("monthly",index)}>
                                                    {rep}
                                                </Dropdown.Item>
                                            ))}
                                        </DropdownButton>
                                        <Button
                                            variant={addEventRepetition === "yearly" ? 'primary' : 'outline-secondary'}
                                            onClick={() => handleClickCombined("yearly",0)}
                                        >
                                            yearly
                                        </Button>
                                    </InputGroup>
                                </Form>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button variant="primary" onClick={(e) => {handleSaveEditEvent(e, events[ev].event.id)}}>
                                    Save Changes
                                </Button>
                                <Button variant="secondary" onClick={handleClose}>
                                    Close
                                </Button>

                            </Modal.Footer>
                        </Modal>
            }
                </Container>
            </div>
        </div>


    );
}

export default Calendar;
