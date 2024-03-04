import React, {useEffect, useState} from 'react';
import './Board.css';
import {Accordion, Button, Col, Container, Form, Row, Tab, Tabs, InputGroup, Spinner, Card} from "react-bootstrap";
import {CgNotes} from "react-icons/cg";
import {MdDeleteForever, MdNoteAdd} from "react-icons/md";
import axios, {AxiosResponse} from "axios";
import {CiStickyNote} from "react-icons/ci";
import {BiEditAlt} from "react-icons/bi";

function Board() {

    const [width, setWidth] = useState<number>(window.innerWidth);

    function handleWindowSizeChange() {
        setWidth(window.innerWidth);
    }
    useEffect(() => {
        window.addEventListener('resize', handleWindowSizeChange);
        return () => {
            window.removeEventListener('resize', handleWindowSizeChange);
        }
    }, []);

    const isMobile = width <= 768;

    interface Announcement{
        id: number,
        idHouse: number,
        description: string,
        user: string,
        date: string,
        documents: []
    }
    const username: string | null = localStorage.getItem("username");
    const title: string = "Board";

    const [editList, setEditList] = useState<{[idItem: string]: boolean}> ({});
    const [descriptionEdit, setDescriptionEdit] = useState<{[idItem: string]: boolean}>({});

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

        //("0" + this.getDate()).slice(-2)

        const announcement = {
            idHouse: localStorage.getItem("idHomeSelected"),
            description: description,
            user: username,
            date: year+"-"+("0" + month).slice(-2)+"-"+("0" + day).slice(-2)
        };
        console.log(announcement.idHouse+"");
        console.log(announcement.description+"");
        console.log(announcement.user+"");
        console.log(announcement.date+"");

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

    const handleEditList = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, key: number, value:string) => {
        event.preventDefault(); // reload page after submit
        setEditList(editList => ({...editList, [key]: true}));
        setDescriptionEdit(items => ({...items, [key]: value}));
    }

    const handleSaveEditList = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, id: number) => {
        event.preventDefault(); // reload page after submit

        console.log("Saving edited description for ID:", id);
        console.log("Edited description:", descriptionEdit[id]);
        const item = {
            idAnnounce: id,
            description: descriptionEdit[id]
        };

        console.log("Item:", item);
        axios.put("http://localhost:8080/api/v1/bacheca/announces/update", item)
            .then(function (response) {
                console.log("Update successful");
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

                if (response.data === undefined || response.data.length == 0) {
                    setAnnounces([]);
                } else {
                    response.data.sort((a, b) => b.date.localeCompare(a.date));
                    setAnnounces(response.data);
                }

                const eList: {[idItem: number]: boolean} = {}
                for (const key in response.data){
                    eList[key] = false;
                }

                setEditList(eList);
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
                            {!editList[announce.id] && !isMobile &&
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
                                    {username == announce.user &&
                                        <Col xs={2} className="d-flex align-items-center">
                                            <Button className="action-button" onClick={(e) => {
                                                handleEditList(e, announce.id, announce.description);
                                            }}>
                                                <BiEditAlt style={{fontSize: '30px', color: '#000'}}/>
                                            </Button>&nbsp;
                                            <Button className="action-button" onClick={(e) => {
                                                // @ts-ignore
                                                handleDelete(e, announce.id);
                                            }}>
                                                <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                            </Button>
                                        </Col>
                                    }
                                </Row>
                            }
                            {!editList[announce.id] && isMobile &&
                                <div>
                                    <Row>
                                        <div className="">
                                            <strong>User:</strong>&nbsp;{announce.user}
                                        </div>
                                        <div className="">
                                            <strong>Description:</strong>&nbsp;{announce.description}
                                        </div>
                                        <div className="descrHouse">
                                            <strong>Date:</strong>&nbsp;{announce.date}
                                        </div>
                                    </Row>
                                    {username == announce.user &&
                                        <Row>
                                            <Col className="d-flex align-items-center">
                                                <Button className="action-button action-button-centre" onClick={(e) => {
                                                    handleEditList(e, announce.id, announce.description);
                                                }}>
                                                    <BiEditAlt style={{fontSize: '30px', color: '#000'}}/>
                                                </Button>
                                            </Col>
                                            <Col className="d-flex align-items-center">
                                                <Button className="action-button action-button-centre" onClick={(e) => {
                                                    // @ts-ignore
                                                    handleDelete(e, announce.id);
                                                }}>
                                                    <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                </Button>
                                            </Col>
                                        </Row>
                                    }
                                </div>
                            }
                            {editList[announce.id] && !isMobile &&
                                <Form>
                                    <Row>
                                        <Col xs={1} className="d-flex align-items-center"><CiStickyNote style={{fontSize: '30px'}}/></Col>
                                        <Col>
                                            <div className="">
                                                <strong>User:</strong>&nbsp;{announce.user}
                                            </div>
                                            <div className="">
                                                <strong>Description:</strong>&nbsp;
                                                <Form.Group className="" controlId="descriptionEdit">
                                                    <Form.Control required type="text" placeholder='Name' defaultValue={announce.description}  onChange={e =>
                                                        setDescriptionEdit(items => ({...items, [announce.id]: e.target.value}))}/>
                                                </Form.Group>
                                            </div>
                                            <div className="descrHouse">
                                                <strong>Date:</strong>&nbsp;{announce.date}
                                            </div>
                                        </Col>
                                        <Col xs={2} className="d-flex align-items-center">
                                            <Button className="HoMatesButton" onClick={(e) => {
                                                handleSaveEditList(e, announce.id);
                                            }}>
                                                Save
                                            </Button>&nbsp;
                                            <Button className="action-button" onClick={(e) => {
                                                // @ts-ignore
                                                handleDelete(e, announce.id);
                                            }}>
                                                <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                            </Button>
                                        </Col>
                                    </Row>
                                </Form>
                            }
                            {editList[announce.id] && isMobile &&
                                <Form>
                                    <Row>
                                        <div className="">
                                            <strong>User:</strong>&nbsp;{announce.user}
                                        </div>
                                        <div className="">
                                            <strong>Description:</strong>&nbsp;
                                            <Form.Group className="" controlId="descriptionEdit">
                                                <Form.Control required type="text" placeholder='Name' defaultValue={announce.description}  onChange={e =>
                                                    setDescriptionEdit(items => ({...items, [announce.id]: e.target.value}))}/>
                                            </Form.Group>
                                        </div>
                                        <div className="descrHouse">
                                            <strong>Date:</strong>&nbsp;{announce.date}
                                        </div>
                                    </Row>
                                    <Row xs={2} className="d-flex align-items-center">
                                        <Col className="d-flex align-items-center">
                                            <Button className="HoMatesButton  action-button-centre" onClick={(e) => {
                                                handleSaveEditList(e, announce.id);
                                            }}>
                                                Save
                                            </Button>
                                        </Col>
                                        <Col className="d-flex align-items-center">
                                            <Button className="action-button  action-button-centre" onClick={(e) => {
                                                // @ts-ignore
                                                handleDelete(e, announce.id);
                                            }}>
                                                <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                            </Button>
                                        </Col>
                                    </Row>
                                </Form>
                            }
                        </Container>
                    </Card>
                ))}
            </div>
        </div>
    );
}

export default Board;