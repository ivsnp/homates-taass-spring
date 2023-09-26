import React, {useState} from 'react';
import './Houses.css';
import {Accordion, Button, Card, Col, Container, Form, InputGroup, Row, Spinner, Tab, Tabs} from "react-bootstrap";
import {BsHouseAdd, BsPersonAdd, BsHouse} from "react-icons/bs";
import {MdDeleteForever, MdShoppingCartCheckout} from "react-icons/md";
import {BiEditAlt} from "react-icons/bi";
import axios, {AxiosResponse} from "axios";


function Houses() {

    interface HousesAttributes {
        id: number,
        name: string,
        description: string,
        address: string,
        owner: string,
        roomMates: Array<string>
    }

    const title: string = "My Houses";
    const [myhomes, setMyhomes] = useState<HousesAttributes[]>();

    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/user-houses/houses/ivsnp", {
            headers: {}})
            .then((response: AxiosResponse<Array<HousesAttributes>>) => {
                setMyhomes(response.data);
            })
            .catch(error => {
                console.log(error)
            });
    }, []);

    if (myhomes === undefined) return (
        <div>
            <Spinner animation="border" role="status" className="spinner">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    );

    return (
        <div className="Houses">
            <Container>
                <Row>
                    <Col>
                        <Accordion>
                            <Accordion.Item eventKey="0">
                                <Accordion.Header>
                                    <Container>
                                        <Row>
                                            <Col xs={2}><BsHouseAdd style={{fontSize: '30px'}}/></Col>
                                            <Col className="d-flex align-items-center">New house</Col>
                                        </Row>
                                    </Container>
                                </Accordion.Header>
                                <Accordion.Body>
                                    <Form>
                                        <Form.Group className="mb-3" controlId="nameHouse">
                                            <Form.Control required type="text" placeholder="House name" />
                                        </Form.Group>
                                        <Form.Group className="mb-3" controlId="addressHouse">
                                            <Form.Control required type="text" placeholder="Address" />
                                        </Form.Group>
                                        <Form.Group className="mb-3" controlId="descriptionHouse">
                                            <Form.Control required as="textarea" rows={3} placeholder="Description" />
                                        </Form.Group>

                                        <Button type="submit" className="mb-4 w-100 HoMatesButton">
                                            Add house
                                        </Button>
                                    </Form>
                                </Accordion.Body>
                            </Accordion.Item>
                        </Accordion>
                    </Col>
                    <Col>
                        <Accordion>
                            <Accordion.Item eventKey="0">
                                <Accordion.Header>
                                    <Container>
                                        <Row>
                                            <Col xs={2}><BsPersonAdd style={{fontSize: '30px'}}/></Col>
                                            <Col className="d-flex align-items-center">Add a roommate</Col>
                                        </Row>
                                    </Container>
                                </Accordion.Header>
                                <Accordion.Body>
                                    <Form>
                                        <Form.Group className="mb-3" controlId="usernameMate">
                                            <Form.Control required type="text" placeholder="username" />
                                        </Form.Group>
                                        <Form.Group className="mb-3" controlId="houseMate">
                                            <Form.Select aria-label="Default select example" className="HomeSelectionSelectMyHouse">
                                                <option disabled>Choose your home</option>

                                                {myhomes.map((myhome) => (
                                                    <option value={myhome.id} key={myhome.id}>{myhome.name}</option>
                                                ))}
                                            </Form.Select>
                                        </Form.Group>

                                        <Button type="submit" className="mb-4 w-100 HoMatesButton">
                                            Add Mate!
                                        </Button>
                                    </Form>
                                </Accordion.Body>
                            </Accordion.Item>
                        </Accordion>
                    </Col>
                </Row>
            </Container>
            <br/>
            <div className="ContentWrap">
                <div className="TitleContent">
                    <h3>
                        {title}
                    </h3>
                </div>
                {myhomes.map((myhome) => (
                    <Card body>
                        <Container>
                            <Row>
                                <Col xs={1} className="d-flex align-items-center"><BsHouse style={{fontSize: '30px'}}/></Col>
                                <Col>
                                    <div className="nameHouse">
                                        {myhome.name}
                                    </div>
                                    <br/>
                                    <div className="addressHouse">
                                        <strong>Address:</strong>&nbsp;{myhome.address}
                                    </div>
                                    <div className="descrHouse">
                                        <strong>Description:</strong>&nbsp;{myhome.description}
                                    </div>
                                    <div className="descrHouse">
                                        <strong>Owner:</strong>&nbsp;{myhome.owner}
                                    </div>
                                    <div className="matesHouse">
                                        <strong>Roommates:</strong>
                                        {
                                            myhome.roomMates.map(item => {
                                                return <span key={item}>&nbsp;{item}</span>;
                                            })
                                        }
                                    </div>
                                </Col>
                                <Col xs={2} className="d-flex align-items-center">
                                    <BiEditAlt style={{fontSize: '30px'}}/>&nbsp;
                                    <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                </Col>
                            </Row>
                        </Container>
                    </Card>
                ))}
            </div>
        </div>
    );
}

export default Houses;
