import React, {useState} from 'react';
import './Board.css';
import {Accordion, Button, Col, Container, Form, Row, Tab, Tabs, InputGroup, Spinner, Card} from "react-bootstrap";
import {CgNotes} from "react-icons/cg";
import {MdDeleteForever, MdNoteAdd} from "react-icons/md";
import axios, {AxiosResponse} from "axios";
import {CiStickyNote} from "react-icons/ci";
import {BiEditAlt} from "react-icons/bi";

function Board() {


    interface Announcement{
        id: number,
        idHouse: number,
        description: string,
        user: string,
        date: string,
        documents: []
    }
    const username: string = "ivsnp";
    const title: string = "Announcements";
    const [errorNewAnnoucement, setErrorNewAnnoucement] = useState('');
    const [description, setDescription] = useState('');
    const [announces, setAnnounces] = useState<Announcement[]>();

    const headers = {
        "Content-Type": "application/json",
        "Accept": "application/json"
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // reload page after submit

        const date = new Date();

        let day = date.getDate();
        let month = date.getMonth() + 1;
        let year = date.getFullYear();

        const announcement = {
            description: description,
            idHouse: localStorage.getItem("idHomeSelected"),
            user: username,
            date: year+"-"+month+"-"+day,
            documents: []
        };

        axios.post("http://localhost:8080/api/v1/bacheca/announces/create", announcement,
            {headers})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                setErrorNewAnnoucement("Error adding announcement to house, check the data and try again.");
            });
    }

    const handleDelete = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, id: number) => {
        event.preventDefault(); // reload page after submit

        axios.delete("http://localhost:8080/api/v1/bacheca/announces/delete/"+id, {})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error)
            });
    }

    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/bacheca/announces/house/"+localStorage.getItem("idHomeSelected"), {
            headers: {}})
            .then((response: AxiosResponse<Announcement[]>) => {
                setAnnounces(response.data);
            })
            .catch(error => {
                console.log(error)
            });
    }, [localStorage.getItem("idHomeSelected")]);




    if (announces === undefined) return (
        <div>
            <Spinner animation="border" role="status" className="spinner">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    );

    return (
        <div className="Board">
            <div className="ContentWrap">

                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="description">
                        <Form.Control required as="textarea" rows={3} placeholder="Description" onChange={e => setDescription(e.target.value)}/>
                    </Form.Group>
                    <div className="errorMessage">
                        {errorNewAnnoucement}
                    </div>
                    <Button type="submit" className="mb-4 w-100 HoMatesButton">
                        Add announcement
                    </Button>
                </Form>
                <div className="TitleContent">
                    <h3>
                        {title}
                    </h3>
                </div>
                {announces.map((announce) => (
                    <Card body>
                        <Container>
                            <Row>
                                <Col xs={1} className="d-flex align-items-center"><CiStickyNote style={{fontSize: '30px'}}/></Col>
                                <Col>
                                    <div className="">
                                        <strong>User:</strong>&nbsp;{announce.user}
                                    </div>
                                    <div className="">
                                        <strong>Description:</strong>&nbsp;{announce.description}
                                    </div>
                                    <div className="descrHouse">
                                        <strong>Date:</strong>&nbsp;{announce.date}
                                    </div>
                                </Col>
                                <Col xs={2} className="d-flex align-items-center">
                                    <BiEditAlt style={{fontSize: '30px'}}/>&nbsp;
                                    <Button className="action-button" onClick={(e) => {
                                        // @ts-ignore
                                        handleDelete(e, announce.id);
                                    }}>
                                        <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                    </Button>
                                </Col>
                            </Row>
                        </Container>
                    </Card>
                ))}
            </div>
        </div>
    );
}

export default Board;

//<Tabs defaultActiveKey="announces">
//    <Tab eventKey = "announces" title = "Announces">
//        <table className="table-spacing">
//            <thead>
//            <tr>
//                <th>Description</th>
//                <th>User</th>
//                <th>Date</th>
//            </tr>
//            </thead>
//            <tbody>
//            {announces.map(announces => (
//                <tr>
//                    <td>{announces.description} </td>
//                    <td>{announces.user} </td>
//                    <td>{announces.date} </td>
//                </tr>
//            ))}
//            </tbody>
//        </table>
//    </Tab>
//    <Tab eventKey="documents" title="Documents">
//        <Row classname = "icon-row">
//            <Col><CgNotes style={{fontSize: '50px', display: "flex", justifyContent: "center"}}/></Col>
//            <Col><CgNotes style={{fontSize: '50px', display: "flex", justifyContent: "center"}}/></Col>
//            <Col><CgNotes style={{fontSize: '50px', display: "flex", justifyContent: "center"}}/></Col>
//            <Col><CgNotes style={{fontSize: '50px', display: "flex", justifyContent: "center"}}/></Col>
//        </Row>
//        <Row>
//            <Col>Bolletta luce</Col>
//            <Col>Chiusura acqua</Col>
//            <Col>Orari portineria</Col>
//            <Col>Squadre</Col>
//        </Row>
//    </Tab>
//</Tabs>
