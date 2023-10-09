import React, {useState} from 'react';
import './Board.css';
import {Accordion, Button, Col, Container, Form, Row, Tab, Tabs, InputGroup, Spinner} from "react-bootstrap";
import {CgNotes} from "react-icons/cg";
import {MdNoteAdd} from "react-icons/md";
import axios, {AxiosResponse} from "axios";

function Board() {


    interface Announces{
        id: number,
        description: string,
        user: string,
        date: string
    }
    const title: string = "Board";
    const [announces, setAnnounces] = useState<Announces[]>();

    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/bacheca/announces/house/"+localStorage.getItem("idHomeSelected"), {
            headers: {}})
            .then((response: AxiosResponse<Announces[]>) => {
                console.log("getting xacts... id:"+localStorage.getItem("idHomeSelected"))
                setAnnounces(response.data);
                console.log(response.data);
                console.log(announces);
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
         <Container>
            <Accordion>
                <Accordion.Item eventKey="0">
                    <Accordion.Header>
                        <Row>
                                <Col xs={2}><MdNoteAdd style={{fontSize: '30px'}}/></Col>
                                <Col className="d-flex align-items-center">New Announce</Col>
                        </Row>
                    </Accordion.Header>
                    <Accordion.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="description">
                                <Form.Control required type="text" placeholder="Description" />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="user">
                                <Form.Control required type="text" placeholder="User" />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="date">
                                <Form.Control required as="textarea" rows={3} placeholder="Date" />
                            </Form.Group>

                            <Button type="submit" className="mb-4 w-100 HoMatesButton">
                                Add announce
                            </Button>
                        </Form>
                    </Accordion.Body>
                </Accordion.Item>
            </Accordion>

                <Row>
                    <Tabs defaultActiveKey="announces">
                        <Tab eventKey = "announces" title = "Announces">
                            <table className="table-spacing">
                                <thead>
                                    <tr>
                                        <th>Description</th>
                                        <th>User</th>
                                        <th>Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                {announces.map(announces => (
                                    <tr>
                                        <td>{announces.description} </td>
                                        <td>{announces.user} </td>
                                        <td>{announces.date} </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </Tab>
                        <Tab eventKey="documents" title="Documents">
                            <Row classname = "icon-row">
                                <Col><CgNotes style={{fontSize: '50px', display: "flex", justifyContent: "center"}}/></Col>
                                <Col><CgNotes style={{fontSize: '50px', display: "flex", justifyContent: "center"}}/></Col>
                                <Col><CgNotes style={{fontSize: '50px', display: "flex", justifyContent: "center"}}/></Col>
                                <Col><CgNotes style={{fontSize: '50px', display: "flex", justifyContent: "center"}}/></Col>
                            </Row>
                            <Row>
                                <Col>Bolletta luce</Col>
                                <Col>Chiusura acqua</Col>
                                <Col>Orari portineria</Col>
                                <Col>Squadre</Col>
                            </Row>
                        </Tab>
                    </Tabs>
                </Row>
            </Container>
        </div>
    );
}

export default Board;
