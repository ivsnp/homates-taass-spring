import React, {useState} from 'react';
import './Wallet.css';
import {Accordion, Button, Col, Container, Form, Row, Tab, Tabs, InputGroup, Card, Spinner} from "react-bootstrap";
import {MdOutlineAddCard, MdShoppingCartCheckout, MdDeleteForever} from "react-icons/md";
import {RiRefund2Line} from "react-icons/ri";
import {FaMoneyBillTransfer} from "react-icons/fa6";
import {TbArrowsSplit} from "react-icons/tb";
import {BiEditAlt} from "react-icons/bi";
import {BsHouse} from "react-icons/bs";
import axios, {AxiosResponse} from "axios";



function Wallet() {

    interface Payment {
        id: number,
        description: String,
        amount: number,
        date: string,
        idHouse: number,
        usernamePay: string,
        usernameSplit: Array<string>
    }

    interface Refund {
        id: number,
        description: string,
        amount: number,
        date: string,
        idHouse: number,
        usernameFrom: string,
        usernameTo: string
    }

    interface Wallet {
        username: string,
        idHouse: number,
        balance: number
    }

    const [transactions, setTransactions] = useState<(Payment | Refund)[]>();
    const [roommates, setRoommates] = useState<Wallet[]>();

    // "http://localhost:8080/api/v1/wallet/balances/1"+localStorage.getItem("idHomeSelected")

    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/wallet/transaction/house/"+localStorage.getItem("idHomeSelected"), {
            headers: {}})
            .then((response: AxiosResponse<(Payment | Refund)[]>) => {
                console.log("getting xacts... id:"+localStorage.getItem("idHomeSelected"))
                setTransactions(response.data);
                console.log(response.data);
                console.log(transactions);
            })
            .catch(error => {
                console.log(error)
            });
    }, [localStorage.getItem("idHomeSelected")]);

    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/wallet/balances/"+localStorage.getItem("idHomeSelected"), {
            headers: {}})
            .then((response: AxiosResponse<Wallet[]>) => {
                console.log("getting balances... id:"+localStorage.getItem("idHomeSelected"))
                setRoommates(response.data);
                console.log(response.data);
                console.log(roommates);
            })
            .catch(error => {
                console.log(error)
            });
    }, [localStorage.getItem("idHomeSelected")]);

    if (transactions === undefined || roommates === undefined) return (
        <div>
            <Spinner animation="border" role="status" className="spinner">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    );

    return (
        <div className="Wallet">
            <div className="WalletBalances">
                <Container>
                    <Row>
                        <Col>
                            <div className="WalletBalance MyBalance">
                                <Container>
                                    <Row>
                                        <Col className="d-flex align-content-center flex-wrap">
                                            <h3>
                                                House balances
                                            </h3>
                                        </Col>
                                        <Col>
                                            {roommates.map((roommate) => (
                                                <Row>
                                                    <Col sm={4}>
                                                        <div className="RoomMateName">
                                                            {roommate.username}
                                                        </div>
                                                    </Col>
                                                    <Col sm={8}>
                                                        <div className="RoomMatesBalance">
                                                            {roommate.balance}€
                                                        </div>
                                                    </Col>
                                                </Row>
                                            ))}
                                        </Col>
                                    </Row>
                                </Container>
                            </div>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Accordion>
                                <Accordion.Item eventKey="0">
                                    <Accordion.Header>
                                        <Container>
                                            <Row>
                                                <Col xs={1}><MdOutlineAddCard style={{fontSize: '30px'}}/></Col>
                                                <Col className="d-flex align-items-center">New transaction</Col>
                                            </Row>
                                        </Container>
                                    </Accordion.Header>
                                    <Accordion.Body>
                                        <Tabs
                                            defaultActiveKey="payment"
                                            id="transactionTab"
                                            className="mb-3 HoMatesTab"
                                            justify>
                                            <Tab eventKey="payment" title="Payment">
                                                <Form>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="amountPayment">€</InputGroup.Text>
                                                        <Form.Control required type="number" step="0.01" placeholder="00.00" />
                                                    </InputGroup>
                                                    <Form.Group className="mb-3" controlId="datePayment">
                                                        <Form.Control required type="date" />
                                                    </Form.Group>
                                                    <Form.Group className="mb-3" controlId="descriptionPayment">
                                                        <Form.Control required type="text" placeholder="Description" />
                                                    </Form.Group>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="splitPayment">Split with:</InputGroup.Text>
                                                        {roommates.map((roommate) => (
                                                            <Form.Check
                                                                type="checkbox"
                                                                id={roommate.username}
                                                                label={roommate.username}
                                                            />
                                                        ))}
                                                    </InputGroup>

                                                    <Button type="submit" className="mb-4 w-100 HoMatesButton">
                                                        Add payment
                                                    </Button>
                                                </Form>
                                            </Tab>
                                            <Tab eventKey="refund" title="Refund">
                                                <Form>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="amountRefund">€</InputGroup.Text>
                                                        <Form.Control required type="number" step="0.01" placeholder="00.00" />
                                                    </InputGroup>
                                                    <Form.Group className="mb-3" controlId="dateRefund">
                                                        <Form.Control required type="date" />
                                                    </Form.Group>
                                                    <Form.Group className="mb-3" controlId="descriptionRefund">
                                                        <Form.Control required type="text" placeholder="Description" />
                                                    </Form.Group>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="fromRefund">From:</InputGroup.Text>
                                                        <Form.Select required aria-label="Default select example" className="HomeSelectionSelect" size="sm">
                                                            {roommates.map((roommate) => (
                                                                <option value={roommate.username} key={roommate.username}>{roommate.username}</option>
                                                            ))}
                                                        </Form.Select>
                                                    </InputGroup>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="ToRefund">To:</InputGroup.Text>
                                                        <Form.Select required aria-label="Default select example" className="HomeSelectionSelect" size="sm">
                                                            {roommates.map((roommate) => (
                                                                <option value={roommate.username} key={roommate.username}>{roommate.username}</option>
                                                            ))}
                                                        </Form.Select>
                                                    </InputGroup>

                                                    <Button type="submit" className="mb-4 w-100 HoMatesButton">
                                                        Add payment
                                                    </Button>
                                                </Form>
                                            </Tab>
                                        </Tabs>
                                    </Accordion.Body>
                                </Accordion.Item>
                            </Accordion>
                        </Col>
                    </Row>
                </Container>
            </div>
            <div className="WalletTransactions">
                <div className="TitleContent">
                    <h3>
                        House Transactions
                    </h3>
                </div>
                {transactions.map((t) => (
                    <Card body>
                        <Container>
                            {'usernameSplit' in t && (
                                <Row>
                                    <Col xs={1} className="d-flex align-items-center"><MdShoppingCartCheckout style={{fontSize: '30px'}}/></Col>
                                    <Col>
                                        <Container>
                                            <Row>
                                                <Col xs={4}>{t.date}</Col>
                                                <Col xs={4}>{t.amount}€</Col>
                                            </Row>
                                            <Row>
                                                <Col xs={4}><strong>Pay:</strong>&nbsp;{t.usernamePay}</Col>
                                                <Col xs={1}><TbArrowsSplit style={{fontSize: '30px'}}/></Col>
                                                <Col xs={5}>
                                                    {
                                                        t.usernameSplit.map(item => {
                                                            return <span key={item}>&nbsp;{item}</span>;
                                                        })
                                                    }
                                                </Col>
                                            </Row>
                                            <Row>
                                                <Col>
                                                    <strong>Description:</strong>&nbsp;{t.description}
                                                </Col>
                                            </Row>
                                        </Container>
                                    </Col>
                                    <Col xs={2} className="d-flex align-items-center">
                                        <BiEditAlt style={{fontSize: '30px'}}/>&nbsp;
                                        <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                    </Col>
                                </Row>
                            )}
                            {'usernameTo' in t && (
                                <Row>
                                    <Col xs={1} className="d-flex align-items-center"><RiRefund2Line style={{fontSize: '30px'}}/></Col>
                                    <Col>
                                        <Container>
                                            <Row>
                                                <Col xs={4}>{t.date}</Col>
                                                <Col xs={4}>{t.amount}€</Col>
                                            </Row>
                                            <Row>
                                                <Col xs={4}><strong>From:</strong>&nbsp;{t.usernameFrom}</Col>
                                                <Col xs={1}><FaMoneyBillTransfer style={{fontSize: '30px'}}/></Col>
                                                <Col xs={4}><strong>To:</strong>&nbsp;{t.usernameTo}</Col>
                                            </Row>
                                            <Row>
                                                <Col>
                                                    <strong>Description:</strong>&nbsp;{t.description}
                                                </Col>
                                            </Row>
                                        </Container>
                                    </Col>
                                    <Col xs={2} className="d-flex align-items-center">
                                        <BiEditAlt style={{fontSize: '30px'}}/>&nbsp;
                                        <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                    </Col>
                                </Row>
                            )}
                        </Container>
                    </Card>
                ))}
            </div>
        </div>
    );
}

export default Wallet;
