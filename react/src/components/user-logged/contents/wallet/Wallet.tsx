import React, {useEffect, useState} from 'react';
import './Wallet.css';
import {Accordion, Button, Col, Container, Form, Row, Tab, Tabs, InputGroup, Card, Spinner} from "react-bootstrap";
import {MdOutlineAddCard, MdShoppingCartCheckout, MdDeleteForever} from "react-icons/md";
import {RiRefund2Line} from "react-icons/ri";
import {BiEditAlt} from "react-icons/bi";
import axios, {AxiosResponse} from "axios";


function Wallet() {

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

    interface Payment {
        id: number,
        description: string,
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

    const usernameLogged: string | null = localStorage.getItem("username");
    if (usernameLogged == null) {
        window.location.assign("/");
    }

    const [editList, setEditList] = useState<{ [idItem: string]: boolean }>({});

    // edit: payment and refund
    const [dateEdit, setDateEdit] = useState<{ [idItem: string]: boolean }>({});
    const [amountEdit, setAmountEdit] = useState<{ [idItem: string]: boolean }>({});
    const [descriptionEdit, setDescriptionEdit] = useState<{ [idItem: string]: boolean }>({});

    // edit: payment
    const [usernamePayEdit, setUsernamePayEdit] = useState<{ [idItem: string]: boolean }>({});
    const [usernameSplitEdit, setUsernameSplitEdit] = useState<{ [idItem: string]: []}>({});

    // edit: refund
    const [usernameFromEdit, setUsernameFromEdit] = useState<{ [idItem: string]: boolean }>({});
    const [usernameToEdit, setUsernameToEdit] = useState<{ [idItem: string]: boolean }>({});


    const [errorNewPayment, setErrorNewPayment] = useState('');
    const [errorNewRefund, setErrorNewRefund] = useState('');
    const [transactions, setTransactions] = useState<(Payment | Refund)[]>();
    const [roommatesBalances, setRoommatesBalances] = useState<Wallet[]>();
    const [roommates, setRoommates] = useState<String[]>();

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

        axios.post(process.env.REACT_APP_API_URL+"/api/v1/wallet/transaction/add-payment", payment, {headers})
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

        axios.post(process.env.REACT_APP_API_URL+"/api/v1/wallet/transaction/add-refund", refund, {headers})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                setErrorNewRefund("Error adding refund, check the data and try again.");
            });
    }

    const handleDeleteTransaction = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, id: number) => {
        event.preventDefault(); // reload page after submit

        axios.delete(process.env.REACT_APP_API_URL+"/api/v1/wallet/transaction/delete/" + id, {})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error)
            });
    }

    const handleEditListPayment = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, key: number, date: string, amount: number, description: string, usernamePay: string, usernameSplit: string[]) => {
        event.preventDefault(); // reload page after submit
        setEditList(editList => ({...editList, [key]: true}));
        setDateEdit(items => ({...items, [key]: date}));
        setAmountEdit(items => ({...items, [key]: amount}));
        setDescriptionEdit(items => ({...items, [key]: description}));

        setUsernamePayEdit(items => ({...items, [key]: usernamePay}));
        setUsernameSplitEdit(items => ({...items, [key]: usernameSplit}));
    }

    const handleEditListRefund = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, key: number, date: string, amount: number, description: string, usernameFrom: string, usernameTo: string) => {
        event.preventDefault(); // reload page after submit
        setEditList(editList => ({...editList, [key]: true}));
        setDateEdit(items => ({...items, [key]: date}));
        setAmountEdit(items => ({...items, [key]: amount}));
        setDescriptionEdit(items => ({...items, [key]: description}));

        setUsernameFromEdit(items => ({...items, [key]: usernameFrom}));
        setUsernameToEdit(items => ({...items, [key]: usernameTo}));
    }

    const handleSaveEditListPayment = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, id: number) => {
        event.preventDefault(); // reload page after submit

        const itemEdit = {
            description: descriptionEdit[id],
            amount: amountEdit[id],
            date: dateEdit[id],
            idHouse: localStorage.getItem("idHomeSelected"),
            usernamePay: usernamePayEdit[id],
            usernameSplit: usernameSplitEdit[id]
        };

        axios.put(process.env.REACT_APP_API_URL+"/api/v1/wallet/transaction/update-payment/" + id, itemEdit)
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error)
            });
    }

    const handleSaveEditListRefund = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, id: number) => {
        event.preventDefault(); // reload page after submit

        const itemEdit = {
            description: descriptionEdit[id],
            amount: amountEdit[id],
            date: dateEdit[id],
            idHouse: localStorage.getItem("idHomeSelected"),
            usernameFrom: usernameFromEdit[id],
            usernameTo: usernameToEdit[id]
        };

        axios.put(process.env.REACT_APP_API_URL+"/api/v1/wallet/transaction/update-refund/" + id, itemEdit)
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error)
            });
    }

    React.useEffect(() => {
        axios.get(process.env.REACT_APP_API_URL+"/api/v1/wallet/transaction/house/" + localStorage.getItem("idHomeSelected"), {
            headers: {}
        })
            .then((response: AxiosResponse<(Payment | Refund)[]>) => {
                if (response.data === undefined || response.data.length === 0) {
                    setTransactions([]);
                } else {
                    response.data.sort((a, b) => b.date.localeCompare(a.date));
                    setTransactions(response.data);
                }

                const eList: { [idItem: number]: boolean } = {}
                for (const key in response.data) {
                    eList[key] = false;
                }

                setEditList(eList);
            })
            .catch(error => {
                console.log(error)
            });
    }, [localStorage.getItem("idHomeSelected")]);

    React.useEffect(() => {
        axios.get(process.env.REACT_APP_API_URL+"/api/v1/wallet/balances/" + localStorage.getItem("idHomeSelected"), {
            headers: {}
        })
            .then((response: AxiosResponse<Wallet[]>) => {
                if (response.data === undefined || response.data.length === 0) {
                    // if there are no roommates
                    setRoommatesBalances([]);
                } else {
                    setRoommatesBalances(response.data);

                    // setting default usernames
                    /*setAddPaymentUserameBy(response.data[1].username);
                    setAddRefundUsernameTo(response.data[1].username);
                    setAddRefundUserameFrom(response.data[1].username);*/
                }
            })
            .catch(error => {
                console.log(error)
            });
    }, [localStorage.getItem("idHomeSelected")]);

    React.useEffect(() => {
        axios.get(process.env.REACT_APP_API_URL+"/api/v1/user-houses/houses/rommates/" + localStorage.getItem("idHomeSelected"), {
            headers: {}
        })
            .then((response: AxiosResponse<string[]>) => {
                if (response.data === undefined || response.data.length === 0) {
                    // if there are no roommates
                    setRoommates([]);
                } else {
                    setRoommates(response.data);

                    // setting default usernames
                    setAddPaymentUserameBy(response.data[0]);
                    setAddRefundUsernameTo(response.data[0]);
                    setAddRefundUserameFrom(response.data[0]);
                }
            })
            .catch(error => {
                console.log(error)
            });
    }, [localStorage.getItem("idHomeSelected")]);

    const handleCheckboxChange = (data: any) => {
        const isChecked = addPaymentUsernameSplit.some(checkedCheckbox => checkedCheckbox === data)
        if (isChecked)
            setAddPaymentUsernameSplit(
                addPaymentUsernameSplit.filter((checkedCheckbox) => checkedCheckbox !== data));
        else
            setAddPaymentUsernameSplit(addPaymentUsernameSplit.concat(data));
    };

    const handleCheckboxChangeEdit = (id: number, data: any) => {
        const isChecked = usernameSplitEdit[id].some(checkedCheckbox => checkedCheckbox === data)
        console.log(usernameSplitEdit[id]);
        console.log(isChecked);
        if (isChecked)
            setUsernameSplitEdit(items => ({...items, [id]:
                    usernameSplitEdit[id].filter((checkedCheckbox) => checkedCheckbox !== data)}));
        else
            setUsernameSplitEdit(items => ({...items, [id]: usernameSplitEdit[id].concat(data)}));
    };

    if (transactions === undefined || roommatesBalances === undefined || roommates === undefined) return (
        <div>
            <Spinner animation="border" role="status" className="spinner">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    );

    // @ts-ignore
    // @ts-ignore
    // @ts-ignore
    return (
        <div className="Wallet">
            <div className="WalletBalances">
                <Container>
                    <Row>
                        <Col>
                            <div className="WalletBalance MyBalance">
                                <Container>
                                    {!isMobile &&
                                        <Row>
                                            <Col  xs={4} className="d-flex align-content-center flex-wrap">
                                                <h3>
                                                    House balances
                                                </h3>
                                            </Col>
                                            <Col>
                                                {roommatesBalances.map((roommate) => (
                                                    <Row>
                                                        <Col>
                                                            <div className="RoomMateName">
                                                                {roommate.username}
                                                            </div>
                                                        </Col>
                                                        <Col>
                                                            <div className="RoomMatesBalance">
                                                                {roommate.balance.toFixed(2)}€
                                                            </div>
                                                        </Col>
                                                    </Row>
                                                ))}
                                            </Col>
                                        </Row>
                                    }
                                    {isMobile &&
                                        <Row>
                                            <Col className="d-flex align-content-center flex-wrap">
                                                <h3>
                                                    House balances
                                                </h3>
                                            </Col>
                                            {roommatesBalances.map((roommate) => (
                                                <Col>
                                                    <Row>
                                                        <div className="RoomMateName">
                                                            {roommate.username}
                                                        </div>
                                                    </Row>
                                                    <Row>
                                                        <div className="RoomMatesBalance">
                                                            {roommate.balance.toFixed(2)}€
                                                        </div>
                                                    </Row>
                                                </Col>
                                            ))}
                                        </Row>
                                    }
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
                                                <Col xs={2}><MdOutlineAddCard style={{fontSize: '30px'}}/></Col>
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
                                                        <Form.Control required type="number" step="0.01"
                                                                      placeholder="00.00"
                                                                      onChange={e => setAddPaymentAmount(e.target.value)}/>
                                                    </InputGroup>
                                                    <Form.Group className="mb-3" controlId="datePayment">
                                                        <Form.Control required type="date"
                                                                      onChange={e => setAddPaymentDate(e.target.value)}/>
                                                    </Form.Group>
                                                    <Form.Group className="mb-3" controlId="descriptionPayment">
                                                        <Form.Control required type="text" placeholder="Description"
                                                                      onChange={e => setAddPaymentDescription(e.target.value)}/>
                                                    </Form.Group>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="fromRefund">Pay:</InputGroup.Text>
                                                        <Form.Select required aria-label="Selection"
                                                                     className="Selection" size="sm"
                                                                     onChange={e => setAddPaymentUserameBy(e.target.value)}>
                                                            {roommates.map((roommate) => (
                                                                //@ts-ignore
                                                                <option value={roommate} key={roommate}>{roommate}</option>
                                                            ))}
                                                        </Form.Select>
                                                    </InputGroup>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="splitPayment">Split with:</InputGroup.Text>
                                                        {roommates.map((roommate) => (
                                                            <Form.Check
                                                                type="checkbox"
                                                                //@ts-ignore
                                                                id={roommate}
                                                                label={roommate}
                                                                checked={addPaymentUsernameSplit.some(checkedCheckbox => checkedCheckbox === roommate)}
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
                                                        <Form.Control required type="number" step="0.01"
                                                                      placeholder="00.00"
                                                                      onChange={e => setAddRefundAmount(e.target.value)}/>
                                                    </InputGroup>
                                                    <Form.Group className="mb-3" controlId="dateRefund">
                                                        <Form.Control required type="date"
                                                                      onChange={e => setAddRefundDate(e.target.value)}/>
                                                    </Form.Group>
                                                    <Form.Group className="mb-3" controlId="descriptionRefund">
                                                        <Form.Control required type="text" placeholder="Description"
                                                                      onChange={e => setAddRefundDescription(e.target.value)}/>
                                                    </Form.Group>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="fromRefund">From:</InputGroup.Text>
                                                        <Form.Select required aria-label="Selection"
                                                                     className="HomeSelectionSelect" size="sm"
                                                                     onChange={e => setAddRefundUserameFrom(e.target.value)}>
                                                            {roommates.map((roommate) => (
                                                                //@ts-ignore
                                                                <option value={roommate} key={roommate}>{roommate}</option>
                                                            ))}
                                                        </Form.Select>
                                                    </InputGroup>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="ToRefund">To:</InputGroup.Text>
                                                        <Form.Select required aria-label="Selection"
                                                                     className="HomeSelectionSelect" size="sm"
                                                                     onChange={e => setAddRefundUsernameTo(e.target.value)}>
                                                            {roommates.map((roommate) => (
                                                                //@ts-ignore
                                                                <option value={roommate} key={roommate}>{roommate}</option>
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
                            {'usernameSplit' in t && !editList[t.id] && !isMobile && (
                                <Row>
                                    <Col xs={1} className="d-flex align-items-center"><MdShoppingCartCheckout
                                        style={{fontSize: '30px'}}/></Col>
                                    <Col>
                                        <Row>
                                            <Col xs={4}><strong>Date:</strong>&nbsp;{t.date}</Col>
                                            <Col xs={4}><strong>Amount:</strong>&nbsp;{t.amount}€</Col>
                                        </Row>
                                        <Row>
                                            <strong>Pay:</strong><span>{t.usernamePay}</span>
                                        </Row>
                                        <Row>
                                            <strong>Split with:</strong>
                                                {
                                                    t.usernameSplit.map(item => {
                                                        return <span key={item}>{item}</span>;
                                                    })
                                                }
                                        </Row>
                                        <Row>
                                            <Col>
                                                <strong>Description:</strong>&nbsp;{t.description}
                                            </Col>
                                            </Row>
                                    </Col>

                                    <Col xs={2} className="d-flex align-items-center">
                                        {usernameLogged === t.usernamePay &&
                                            <div className="d-flex align-items-center">
                                                <Button className="action-button" onClick={(e) => {
                                                    handleEditListPayment(e, t.id, t.date, t.amount, t.description, t.usernamePay, t.usernameSplit);
                                                }}>
                                                    <BiEditAlt style={{fontSize: '30px', color: '#000'}}/>
                                                </Button>&nbsp;
                                                <Button className="action-button" onClick={(e) => {
                                                    // @ts-ignore
                                                    handleDeleteTransaction(e, t.id);
                                                }}>
                                                    <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                </Button>
                                            </div>
                                        }
                                    </Col>
                                </Row>
                            )}
                            {'usernameSplit' in t && !editList[t.id] && isMobile && (
                                <div>
                                    <Row>
                                        <Row>
                                            <Col><strong>Date:</strong>&nbsp;{t.date}</Col>
                                            <Col><strong>Amount:</strong>&nbsp;{t.amount}€</Col>
                                        </Row>
                                        <Row>
                                            <strong>Pay:</strong><span>{t.usernamePay}</span></Row>
                                        <Row>
                                            <strong>Splitted with:</strong>
                                            {
                                                t.usernameSplit.map(item => {
                                                    return <span key={item}>{item}</span>;
                                                })
                                            }
                                        </Row>
                                        <Row>
                                            <strong>Description:</strong><span>{t.description}</span>
                                        </Row>
                                    </Row>
                                    {usernameLogged === t.usernamePay &&
                                        <Row>
                                            <Col className="d-flex align-items-center">
                                                <Button className="action-button action-button-centre" onClick={(e) => {
                                                    handleEditListPayment(e, t.id, t.date, t.amount, t.description, t.usernamePay, t.usernameSplit);
                                                }}>
                                                    <BiEditAlt style={{fontSize: '30px', color: '#000'}}/>
                                                </Button>
                                            </Col>
                                            <Col className="d-flex align-items-center">
                                                <Button className="action-button action-button-centre" onClick={(e) => {
                                                    // @ts-ignore
                                                    handleDeleteTransaction(e, t.id);
                                                }}>
                                                    <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                </Button>
                                            </Col>
                                        </Row>
                                    }
                                </div>
                            )}
                            {'usernameSplit' in t && editList[t.id] && (
                                <Form>
                                    <Row>
                                        {!isMobile &&
                                            <Col xs={1} className="d-flex align-items-center"><MdShoppingCartCheckout
                                            style={{fontSize: '30px'}}/></Col>
                                        }
                                        <Col>
                                            <Container>
                                                {!isMobile &&
                                                    <Row>
                                                        <Col>
                                                            <strong>Date:</strong>&nbsp;
                                                            <Form.Group className="" controlId="dateEdit">
                                                                <Form.Control required type="date" placeholder='Name'
                                                                              defaultValue={t.date} onChange={e =>
                                                                    setDateEdit(items => ({
                                                                        ...items,
                                                                        [t.id]: e.target.value
                                                                    }))}/>
                                                            </Form.Group>
                                                        </Col>

                                                        <Col>
                                                            <strong>Amount:</strong>&nbsp;
                                                            <InputGroup className="mb-3">
                                                                <InputGroup.Text id="amountEdit">€</InputGroup.Text>
                                                                <Form.Control required type="number" step="0.01"
                                                                              placeholder="00.00" defaultValue={t.amount}
                                                                              onChange={e =>
                                                                                  setAmountEdit(items => ({
                                                                                      ...items,
                                                                                      [t.id]: e.target.value
                                                                                  }))}/>
                                                            </InputGroup>
                                                        </Col>
                                                    </Row>
                                                }
                                                {isMobile &&
                                                    <Row>
                                                        <strong>Date:</strong>&nbsp;
                                                        <Form.Group className="" controlId="dateEdit">
                                                            <Form.Control required type="date" placeholder='Name'
                                                                          defaultValue={t.date} onChange={e =>
                                                                setDateEdit(items => ({
                                                                    ...items,
                                                                    [t.id]: e.target.value
                                                                }))}/>
                                                        </Form.Group>
                                                    </Row>
                                                }
                                                {isMobile &&
                                                    <Row>
                                                        <strong>Amount:</strong>&nbsp;
                                                        <InputGroup className="mb-3">
                                                            <InputGroup.Text id="amountEdit">€</InputGroup.Text>
                                                            <Form.Control required type="number" step="0.01"
                                                                          placeholder="00.00" defaultValue={t.amount}
                                                                          onChange={e =>
                                                                              setAmountEdit(items => ({
                                                                                  ...items,
                                                                                  [t.id]: e.target.value
                                                                              }))}/>
                                                        </InputGroup>
                                                    </Row>
                                                }
                                                <Row>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="payPayment">Pay:</InputGroup.Text>
                                                        <Form.Select required aria-label="Selection"
                                                                     className="Selection" size="sm"
                                                                     defaultValue={t.usernamePay}
                                                                     onChange={e =>
                                                                         setUsernamePayEdit(items => ({...items, [t.id]: e.target.value}))}>
                                                            {roommates.map((roommate) => (
                                                                //@ts-ignore
                                                                <option value={roommate} key={roommate}>{roommate}</option>
                                                            ))}
                                                        </Form.Select>
                                                    </InputGroup>
                                                </Row>
                                                <Row>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="splitPayment">Split with:</InputGroup.Text>
                                                        {roommates.map((roommate) => (
                                                            <Form.Check
                                                                type="checkbox"
                                                                //@ts-ignore
                                                                id={roommate}
                                                                label={roommate}
                                                                checked={usernameSplitEdit[t.id].some(checkedCheckbox => checkedCheckbox === roommate)}
                                                                onChange={() => handleCheckboxChangeEdit(t.id, roommate)}
                                                            />
                                                        ))}
                                                    </InputGroup>
                                                </Row>
                                                <Row>
                                                    <Col>
                                                        <strong>Description:</strong>&nbsp;
                                                        <Form.Group className="" controlId="descriptionEdit">
                                                            <Form.Control required type="text" placeholder='Name'
                                                                          defaultValue={t.description} onChange={e =>
                                                                setDescriptionEdit(items => ({
                                                                    ...items,
                                                                    [t.id]: e.target.value
                                                                }))}/>
                                                        </Form.Group>
                                                    </Col>
                                                </Row>
                                            </Container>
                                        </Col>
                                        {!isMobile &&
                                            <Col xs={2} className="d-flex align-items-center">
                                                {usernameLogged === t.usernamePay &&
                                                    <div className="d-flex align-items-center">
                                                        <Button className="HoMatesButton" onClick={(e) => {
                                                            handleSaveEditListPayment(e, t.id);
                                                        }}>
                                                            Save
                                                        </Button>&nbsp;
                                                        <Button className="action-button" onClick={(e) => {
                                                            // @ts-ignore
                                                            handleDeleteTransaction(e, t.id);
                                                        }}>
                                                            <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                        </Button>
                                                    </div>
                                                }
                                            </Col>
                                        }
                                    </Row>
                                    {isMobile && usernameLogged === t.usernamePay &&
                                        <Row>
                                            <Col className="d-flex align-items-center">
                                                <Button className="HoMatesButton action-button-centre" onClick={(e) => {
                                                    handleSaveEditListPayment(e, t.id);
                                                }}>
                                                    Save
                                                </Button>
                                            </Col>
                                            <Col className="d-flex align-items-center">
                                                <Button className="action-button action-button-centre" onClick={(e) => {
                                                    // @ts-ignore
                                                    handleDeleteTransaction(e, t.id);
                                                }}>
                                                    <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                </Button>
                                            </Col>
                                        </Row>
                                    }
                                </Form>
                            )}
                            {'usernameTo' in t && !editList[t.id] && !isMobile && (
                                <Row>
                                    <Col xs={1} className="d-flex align-items-center"><RiRefund2Line
                                        style={{fontSize: '30px'}}/></Col>
                                    <Col>
                                        <Row>
                                            <Col xs={4}><strong>Date:</strong>&nbsp;{t.date}</Col>
                                            <Col xs={4}><strong>Amount:</strong>&nbsp;{t.amount}€</Col>
                                        </Row>
                                        <Row><strong>From:</strong><span>{t.usernameFrom}</span></Row>
                                        <Row><strong>To:</strong><span>{t.usernameTo}</span></Row>
                                        <Row>
                                            <strong>Description:</strong><span>{t.description}</span>
                                        </Row>
                                    </Col>
                                    <Col xs={2} className="d-flex align-items-center">
                                        {usernameLogged === t.usernameFrom &&
                                            <div className="d-flex align-items-center">
                                                <Button className="action-button" onClick={(e) => {
                                                    handleEditListRefund(e, t.id, t.date, t.amount, t.description, t.usernameFrom, t.usernameTo);
                                                }}>
                                                    <BiEditAlt style={{fontSize: '30px', color: '#000'}}/>
                                                </Button>&nbsp;
                                                <Button className="action-button" onClick={(e) => {
                                                    // @ts-ignore
                                                    handleDeleteTransaction(e, t.id);
                                                }}>
                                                    <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                </Button>
                                            </div>
                                        }
                                    </Col>
                                </Row>
                            )}
                            {'usernameTo' in t && !editList[t.id] && isMobile && (
                                <div>
                                    <Row>
                                        <Row>
                                            <Col><strong>Date:</strong>&nbsp;{t.date}</Col>
                                            <Col><strong>Amount:</strong>&nbsp;{t.amount}€</Col>
                                        </Row>
                                        <Row><strong>From:</strong><span>{t.usernameFrom}</span></Row>
                                        <Row><strong>To:</strong><span>{t.usernameTo}</span></Row>
                                        <Row>
                                            <strong>Description:</strong><span>{t.description}</span>
                                        </Row>
                                    </Row>
                                    {usernameLogged === t.usernameFrom &&
                                        <Row>
                                            <Col className="d-flex align-items-center">
                                                <Button className="action-button action-button-centre" onClick={(e) => {
                                                    handleEditListRefund(e, t.id, t.date, t.amount, t.description, t.usernameFrom, t.usernameTo);
                                                }}>
                                                    <BiEditAlt style={{fontSize: '30px', color: '#000'}}/>
                                                </Button>
                                            </Col>
                                            <Col className="d-flex align-items-center">
                                                <Button className="action-button action-button-centre" onClick={(e) => {
                                                    // @ts-ignore
                                                    handleDeleteTransaction(e, t.id);
                                                }}>
                                                    <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                </Button>
                                            </Col>
                                        </Row>
                                    }
                                </div>
                            )}
                            {'usernameTo' in t && editList[t.id] && (
                                <Form>
                                    <Row>
                                        {!isMobile &&
                                            <Col xs={1} className="d-flex align-items-center"><RiRefund2Line
                                            style={{fontSize: '30px'}}/></Col>
                                        }
                                        <Col>
                                            <Container>
                                                {!isMobile &&
                                                    <Row>
                                                        <Col>
                                                            <strong>Date:</strong>&nbsp;
                                                            <Form.Group className="" controlId="dateEdit">
                                                                <Form.Control required type="date" placeholder='Name'
                                                                              defaultValue={t.date} onChange={e =>
                                                                    setDateEdit(items => ({
                                                                        ...items,
                                                                        [t.id]: e.target.value
                                                                    }))}/>
                                                            </Form.Group>
                                                        </Col>

                                                        <Col>
                                                            <strong>Amount:</strong>&nbsp;
                                                            <InputGroup className="mb-3">
                                                                <InputGroup.Text id="amountEdit">€</InputGroup.Text>
                                                                <Form.Control required type="number" step="0.01"
                                                                              placeholder="00.00" defaultValue={t.amount}
                                                                              onChange={e =>
                                                                                  setAmountEdit(items => ({
                                                                                      ...items,
                                                                                      [t.id]: e.target.value
                                                                                  }))}/>
                                                            </InputGroup>
                                                        </Col>
                                                    </Row>
                                                }
                                                {isMobile &&
                                                    <Row>
                                                        <strong>Date:</strong>&nbsp;
                                                        <Form.Group className="" controlId="dateEdit">
                                                            <Form.Control required type="date" placeholder='Name'
                                                                          defaultValue={t.date} onChange={e =>
                                                                setDateEdit(items => ({
                                                                    ...items,
                                                                    [t.id]: e.target.value
                                                                }))}/>
                                                        </Form.Group>
                                                    </Row>
                                                }
                                                {isMobile &&
                                                    <Row>
                                                        <strong>Amount:</strong>&nbsp;
                                                        <InputGroup className="mb-3">
                                                            <InputGroup.Text id="amountEdit">€</InputGroup.Text>
                                                            <Form.Control required type="number" step="0.01"
                                                                          placeholder="00.00" defaultValue={t.amount}
                                                                          onChange={e =>
                                                                              setAmountEdit(items => ({
                                                                                  ...items,
                                                                                  [t.id]: e.target.value
                                                                              }))}/>
                                                        </InputGroup>
                                                    </Row>
                                                }
                                                <Row>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="fromRefund">From:</InputGroup.Text>
                                                        <Form.Select required aria-label="Selection"
                                                                     className="Selection" size="sm"
                                                                     defaultValue={t.usernameFrom}
                                                                     onChange={e =>
                                                                         setUsernameFromEdit(items => ({...items, [t.id]: e.target.value}))}>
                                                            {roommates.map((roommate) => (
                                                                //@ts-ignore
                                                                <option value={roommate} key={roommate}>{roommate}</option>
                                                            ))}
                                                        </Form.Select>
                                                    </InputGroup>
                                                </Row>


                                                <Row>
                                                    <InputGroup className="mb-3">
                                                        <InputGroup.Text id="toRefund">To:</InputGroup.Text>
                                                        <Form.Select required aria-label="Selection"
                                                                     className="Selection" size="sm"
                                                                     defaultValue={t.usernameTo}
                                                                     onChange={e =>
                                                                         setUsernameToEdit(items => ({...items, [t.id]: e.target.value}))}>
                                                            {roommates.map((roommate) => (
                                                                //@ts-ignore
                                                                <option value={roommate} key={roommate}>{roommate}</option>
                                                            ))}
                                                        </Form.Select>
                                                    </InputGroup>
                                                </Row>

                                                <Row>
                                                    <Col>
                                                        <strong>Description:</strong>&nbsp;
                                                        <Form.Group className="" controlId="descriptionEdit">
                                                            <Form.Control required type="text" placeholder='Name'
                                                                          defaultValue={t.description} onChange={e =>
                                                                setDescriptionEdit(items => ({
                                                                    ...items,
                                                                    [t.id]: e.target.value
                                                                }))}/>
                                                        </Form.Group>
                                                    </Col>
                                                </Row>
                                            </Container>
                                        </Col>
                                        {!isMobile &&
                                            <Col xs={2} className="d-flex align-items-center">
                                                {usernameLogged === t.usernameFrom &&
                                                    <div className="d-flex align-items-center">
                                                        <Button className="HoMatesButton" onClick={(e) => {
                                                            handleSaveEditListRefund(e, t.id);
                                                        }}>
                                                            Save
                                                        </Button>&nbsp;
                                                        <Button className="action-button" onClick={(e) => {
                                                            // @ts-ignore
                                                            handleDeleteTransaction(e, t.id);
                                                        }}>
                                                            <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                        </Button>
                                                    </div>
                                                }
                                            </Col>
                                        }
                                    </Row>

                                    {isMobile && usernameLogged === t.usernameFrom &&
                                        <Row>
                                            <Col className="d-flex align-items-center">
                                                <Button className="HoMatesButton action-button-centre" onClick={(e) => {
                                                    handleSaveEditListRefund(e, t.id);
                                                }}>
                                                    Save
                                                </Button>
                                            </Col>
                                            <Col className="d-flex align-items-center">
                                                <Button className="action-button action-button-centre" onClick={(e) => {
                                                    // @ts-ignore
                                                    handleDeleteTransaction(e, t.id);
                                                }}>
                                                    <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                </Button>
                                            </Col>
                                        </Row>
                                    }
                                </Form>
                            )}
                        </Container>
                    </Card>
                ))}
            </div>
        </div>
    );
}

export default Wallet;
