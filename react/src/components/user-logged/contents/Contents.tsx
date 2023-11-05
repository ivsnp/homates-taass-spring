import React, {useState} from 'react';
import './Contents.css';
import {Button, Col, Container, Form, Row, Spinner} from "react-bootstrap";
import {FiSettings} from "react-icons/fi";
import {Route, Switch} from "react-router-dom";
import Wallet from "./wallet/Wallet";
import NotFound from "../../notFound/NotFound";
import axios, {AxiosResponse} from "axios";
import Board from "./board/Board";
import ShoppingList from "./shopping-list/ShoppingList";

function Houses() {

    interface HousesAttributes {
        id: number,
        name: string,
        description: string,
        address: string,
        owner: string,
        roomMates: Array<string>
    }

    const onChangeHome = (e: any) => {
        e.preventDefault();
        setSelectedHome(e.target.value);
        localStorage.setItem('idHomeSelected', e.target.value);
    };

    const [myhomes, setMyhomes] = useState<HousesAttributes[]>();
    const [selectedHome, setSelectedHome] = useState<number>();

    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/user-houses/houses/ivsnp", {
            headers: {}})
            .then((response: AxiosResponse<Array<HousesAttributes>>) => {
                setMyhomes(response.data);
                setSelectedHome(response.data[0].id);
                if (localStorage.getItem("idHomeSelected") === null)
                    localStorage.setItem('idHomeSelected', ""+response.data[0].id);
            })
            .catch(error => {
                console.log(error)
            });
    }, []);

    if (myhomes === undefined || selectedHome === undefined) return (
        <div>
            <Spinner animation="border" role="status" className="spinner">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    );

    return (
        <div className="Contents">
            <div className="HomeSelection">
                <Container>
                    <Row>
                        <Col>
                            <Form.Select onChange={(e) => onChangeHome(e)} aria-label="Default select example" className="HomeSelectionSelect" size="sm">
                                <option disabled>Choose your home</option>
                                {
                                    myhomes.map((myhome) => (
                                        <option value={myhome.id} key={myhome.id} selected={myhome.id+"" === localStorage.getItem("idHomeSelected")}>{myhome.name}</option>
                                    ))
                                }
                            </Form.Select>
                        </Col>
                        <Col>
                            <Button className="HoMatesButton" size="sm" href="/user/houses">
                                <FiSettings style={{fontSize: '20px'}}/> Manage your homes
                            </Button>
                        </Col>
                    </Row>
                </Container>
            </div>
            <div className="ContentWrap">
                <Switch>
                    <Route exact path="/user/task/wallet">
                        <Wallet />
                    </Route>
                    <Route exact path="/user/task/board">
                        <Board />
                    </Route>
                    <Route exact path = "/user/task/shopping-list">
                        <ShoppingList />
                    </Route>
                    <Route exact path="/user">
                        <Wallet />
                    </Route>
                    <Route path="*">
                        <div className="ContentWrap">
                            <NotFound/>
                        </div>
                    </Route>
                </Switch>
            </div>
        </div>
    );
}

export default Houses;
