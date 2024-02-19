import React, {useRef, useState} from 'react';
import './Houses.css';
import {
    Accordion,
    Alert,
    Button,
    Card,
    Col,
    Container,
    Form,
    InputGroup,
    Row,
    Spinner,
    Tab,
    Tabs
} from "react-bootstrap";
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

    const username: string = "ivsnp";
    const title: string = "My Houses";
    const [errorNewHouse, setErrorNewHouse] = useState('');
    const [errorNewPerson, setErrorNewPerson] = useState('');
    const [myhomes, setMyhomes] = useState<HousesAttributes[]>();
    const [newHouseName, setNewHouseName] = useState('');
    const [newHouseDescription, setNewHouseDescription] = useState('');
    const [newHouseAddress, setNewHouseAddress] = useState('');
    const [idHouseNewPerson, setIdHouseNewPerson] = useState('');
    const [usernameNewPerson, setUsernameNewPerson] = useState('');

    const headers = {
        "Content-Type": "application/json",
        "Accept": "application/json"
    };

    const handleSubmitAddHouse = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // reload page after submit
        const house = {
            name: newHouseName,
            description: newHouseDescription,
            address: newHouseAddress,
            username_owner: username
        };

        axios.post("http://localhost:8080/api/v1/user-houses/houses/create", house, {headers})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error);
                setErrorNewPerson("Error adding house, check the data and try again.");
            });
    }

    const handleSubmitAddPerson = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // reload page after submit
        //, idHouse: number, usernameMate: string

        axios.post("http://localhost:8080/api/v1/user-houses/houses/add-roommate/"+idHouseNewPerson+"/"+usernameNewPerson, {},
            {headers})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error);
                setErrorNewPerson("Error adding person to house, check the data and try again.");
            });
    }

    const handleDeleteRoommate = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, idHouse: number, username: string) => {
        event.preventDefault(); // reload page after submit

        axios.delete("http://localhost:8080/api/v1/user-houses/houses/remove-roommate/"+idHouse+"/"+username, {})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error)
            });
    }

    const handleDeleteHouse = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, id: number) => {
        event.preventDefault(); // reload page after submit

        axios.delete("http://localhost:8080/api/v1/user-houses/houses/delete/"+id, {})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error)
            });
    }

    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/user-houses/houses/"+username, {
            headers: {}})
            .then((response: AxiosResponse<Array<HousesAttributes>>) => {
                setMyhomes(response.data);
                setIdHouseNewPerson(""+response.data[0].id);
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
                                    <Form onSubmit={handleSubmitAddHouse}>
                                        <Form.Group className="mb-3" controlId="nameHouse">
                                            <Form.Control required type="text" placeholder="House name" onChange={e => setNewHouseName(e.target.value)}/>
                                        </Form.Group>
                                        <Form.Group className="mb-3" controlId="addressHouse">
                                            <Form.Control required type="text" placeholder="Address" onChange={e => setNewHouseAddress(e.target.value)}/>
                                        </Form.Group>
                                        <Form.Group className="mb-3" controlId="descriptionHouse">
                                            <Form.Control required as="textarea" rows={3} placeholder="Description" onChange={e => setNewHouseDescription(e.target.value)}/>
                                        </Form.Group>

                                        <div className="errorMessage">
                                            {errorNewHouse}
                                        </div>

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
                                    <Form onSubmit={handleSubmitAddPerson}>
                                        <Form.Group className="mb-3" controlId="usernameMate">
                                            <Form.Control required type="text" placeholder="username" onChange={e => setUsernameNewPerson(e.target.value)}/>
                                        </Form.Group>
                                        <Form.Group className="mb-3" controlId="houseMate">
                                            <Form.Select aria-label="Default select example" className="HomeSelectionSelectMyHouse" onChange={e => setIdHouseNewPerson(e.target.value)}>
                                                <option disabled>Choose your home</option>

                                                {myhomes.map((myhome) => (
                                                    <option value={myhome.id} key={myhome.id}>{myhome.name}</option>
                                                ))}
                                            </Form.Select>
                                        </Form.Group>

                                        <div className="errorMessage">
                                            {errorNewPerson}
                                        </div>

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
                                    <Button className="action-button" onClick={(e) => {
                                        // @ts-ignore
                                        handleDeleteHouse(e, myhome.id);
                                    }}>
                                        <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                    </Button>
                                </Col>
                            </Row>
                            <Row>
                                <Col>
                                    <div className="">
                                        <strong>Roommates:</strong>
                                    </div>
                                    {
                                        myhome.roomMates.map(item => {
                                            return <Card body>
                                                <Container>
                                                    <Row>
                                                        <Col className="d-flex align-items-center">
                                                            <strong>Name:</strong>&nbsp;{item}
                                                        </Col>
                                                        <Col xs={1} className="d-flex align-items-center">
                                                            <Button className="action-button" onClick={(e) => {
                                                                handleDeleteRoommate(e, myhome.id, item);
                                                            }}>
                                                                <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                            </Button>
                                                        </Col>
                                                    </Row>
                                                </Container>

                                            </Card>;
                                        })
                                    }
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
