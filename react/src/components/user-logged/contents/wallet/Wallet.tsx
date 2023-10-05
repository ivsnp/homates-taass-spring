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

    const [errorNewPayment, setErrorNewPayment] = useState('');
    const [errorNewRefund, setErrorNewRefund] = useState('');
    const [transactions, setTransactions] = useState<(Payment | Refund)[]>();
    const [roommates, setRoommates] = useState<Wallet[]>();

    const [addPaymentDescription, setAddPaymentDescription] = useState('');
    const [addPaymentAmount, setAddPaymentAmount] = useState('');
    const [addPaymentDate, setAddPaymentDate] = useState('');
    const [addPaymentUsernameBy, setAddPaymentUserameBy] = useState('');
    const [addPaymentUsernameSplit, setAddPaymentUsernameSplit] = useState([]);

    const [addRefundDescription, setAddRefundDescription] = useState('');
    const [addRefundAmount, setAddRefundAmount] = useState('');
    const [addRefundDate, setAddRefundDate] = useState('');
    const [addRefundUsernameFrom, setAddRefundUserameFrom] = useState('');
    const [addRefundUsernameTo, setAddRefundUsernameTo] = useState('');

    const headers = {
        "Content-Type": "application/json",
        "Accept": "application/json"
    };

    const handleSubmitAddPayment = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // reload page after submit
        const payment = {
            description: addPaymentDescription,
            amount: addPaymentAmount,
            date: addPaymentDate,
            idHouse: localStorage.getItem("idHomeSelected"),
            usernamePay: addPaymentUsernameBy,
            usernameSplit: addPaymentUsernameSplit
        };

        axios.post("http://localhost:8080/api/v1/wallet/transaction/add-payment", payment, {headers})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                setErrorNewPayment("Error adding payment, check the data and try again.");
            });
    }

    const handleSubmitAddRefund = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // reload page after submit
        const refund = {
            description: addRefundDescription,
            amount: addRefundAmount,
            date: addRefundDate,
            idHouse: localStorage.getItem("idHomeSelected"),
            usernameFrom: addRefundUsernameFrom,
            usernameTo: addRefundUsernameTo
        };

        axios.post("http://localhost:8080/api/v1/wallet/transaction/add-refund", refund, {headers})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                setErrorNewRefund("Error adding refund, check the data and try again.");
            });
    }

    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/wallet/transaction/house/"+localStorage.getItem("idHomeSelected"), {
            headers: {}})
            .then((response: AxiosResponse<(Payment | Refund)[]>) => {
                response.data.sort((a, b) => b.date.localeCompare(a.date));
                setTransactions(response.data);
            })
            .catch(error => {
                console.log(error)
            });
    }, [localStorage.getItem("idHomeSelected")]);

    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/wallet/balances/"+localStorage.getItem("idHomeSelected"), {
            headers: {}})
            .then((response: AxiosResponse<Wallet[]>) => {
                setRoommates(response.data);

                // setting default usernames
                setAddPaymentUserameBy(response.data[1].username);
                setAddRefundUsernameTo(response.data[1].username);
                setAddRefundUserameFrom(response.data[1].username);
            })
            .catch(error => {
                console.log(error)
            });
    }, [localStorage.getItem("idHomeSelected")]);

    const handleCheckboxChange = (data: any) => {
        const isChecked = addPaymentUsernameSplit.some(checkedCheckbox => checkedCheckbox === data.username)
        if (isChecked)
            setAddPaymentUsernameSplit(
                addPaymentUsernameSplit.filter((checkedCheckbox) => checkedCheckbox !== data.username));
        else
            setAddPaymentUsernameSplit(addPaymentUsernameSplit.concat(data.username));
    };

    if (transactions === undefined || roommates === undefined) return (
        <div>
            <Spinner animation="border" role="status" className="spinner">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    );

    // @ts-ignore
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
                                                            {roommate.balance.toFixed(2)}€
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
                                                <Form onSubmit={handleSubmitAddPayment}>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="amountPayment">€</InputGroup.Text>
                                                        <Form.Control required type="number" step="0.01" placeholder="00.00" onChange={e => setAddPaymentAmount(e.target.value)}/>
                                                    </InputGroup>
                                                    <Form.Group className="mb-3" controlId="datePayment">
                                                        <Form.Control required type="date" onChange={e => setAddPaymentDate(e.target.value)}/>
                                                    </Form.Group>
                                                    <Form.Group className="mb-3" controlId="descriptionPayment">
                                                        <Form.Control required type="text" placeholder="Description" onChange={e => setAddPaymentDescription(e.target.value)}/>
                                                    </Form.Group>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="fromRefund">Pay:</InputGroup.Text>
                                                        <Form.Select required aria-label="Default select example" className="HomeSelectionSelect" size="sm" onChange={e => setAddPaymentUserameBy(e.target.value)}>
                                                            {roommates.map((roommate) => (
                                                                <option value={roommate.username} key={roommate.username}>{roommate.username}</option>
                                                            ))}
                                                        </Form.Select>
                                                    </InputGroup>
                                                    <InputGroup className="mb-3" >
                                                        <InputGroup.Text id="splitPayment">Split with:</InputGroup.Text>
                                                        {roommates.map((roommate) => (
                                                            <Form.Check
                                                                type="checkbox"
                                                                id={roommate.username}
                                                                label={roommate.username}
                                                                checked={addPaymentUsernameSplit.some(checkedCheckbox => checkedCheckbox === roommate.username)}
                                                                onChange={() => handleCheckboxChange(roommate)}
                                                            />
                                                        ))}
                                                    </InputGroup>

                                                    <div className="errorMessage">
                                                        {errorNewPayment}
                                                    </div>

                                                    <Button type="submit" className="mb-4 w-100 HoMatesButton">
                                                        Add payment
                                                    </Button>
                                                </Form>
                                            </Tab>
                                            <Tab eventKey="refund" title="Refund">
                                                <Form onSubmit={handleSubmitAddRefund}>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="amountRefund">€</InputGroup.Text>
                                                        <Form.Control required type="number" step="0.01" placeholder="00.00" onChange={e => setAddRefundAmount(e.target.value)}/>
                                                    </InputGroup>
                                                    <Form.Group className="mb-3" controlId="dateRefund">
                                                        <Form.Control required type="date" onChange={e => setAddRefundDate(e.target.value)}/>
                                                    </Form.Group>
                                                    <Form.Group className="mb-3" controlId="descriptionRefund">
                                                        <Form.Control required type="text" placeholder="Description" onChange={e => setAddRefundDescription(e.target.value)}/>
                                                    </Form.Group>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="fromRefund">From:</InputGroup.Text>
                                                        <Form.Select required aria-label="Default select example" className="HomeSelectionSelect" size="sm" onChange={e => setAddRefundUserameFrom(e.target.value)}>
                                                            {roommates.map((roommate) => (
                                                                <option value={roommate.username} key={roommate.username}>{roommate.username}</option>
                                                            ))}
                                                        </Form.Select>
                                                    </InputGroup>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="ToRefund">To:</InputGroup.Text>
                                                        <Form.Select required aria-label="Default select example" className="HomeSelectionSelect" size="sm"onChange={e => setAddRefundUsernameTo(e.target.value)}>
                                                            {roommates.map((roommate) => (
                                                                <option value={roommate.username} key={roommate.username}>{roommate.username}</option>
                                                            ))}
                                                        </Form.Select>
                                                    </InputGroup>

                                                    <div className="errorMessage">
                                                        {errorNewRefund}
                                                    </div>

                                                    <Button type="submit" className="mb-4 w-100 HoMatesButton">
                                                        Add Refund
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
