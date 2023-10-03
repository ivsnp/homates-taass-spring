import React, {useState} from 'react';
import './Board.css';
import {Accordion, Button, Col, Container, Form, Row, Tab, Tabs, InputGroup} from "react-bootstrap";
import {CgNotes} from "react-icons/cg";
import {MdNoteAdd} from "react-icons/md";

function Board() {
    const title: string = "Board";
    const [announces, setRoommates] = useState([
        {description: 'Bolletta luce', user : 'Mattia', date : '2022-05-16'},
        {description: 'Chiusura acqua', user : 'Giorgia', date : '2022-07-30'},
        {description: 'Orari portineria', user : 'Angelica', date : '2021-10-20'},
        {description: 'Squadre partita di calcio', user : 'Giorgio', date : '2022-08-14'}
    ])

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
