import React, {useEffect, useState} from 'react';
import './ShoppingList.css';
import {Accordion, Button, Card, Col, Container, Form, Row, Spinner} from "react-bootstrap";
import {LiaCartArrowDownSolid, LiaCartPlusSolid} from "react-icons/lia";
import {CiStickyNote} from "react-icons/ci";
import {BiEditAlt} from "react-icons/bi";
import {MdDeleteForever} from "react-icons/md";
import axios, {AxiosResponse} from "axios";

function ShoppingList() {

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

    interface Product {
        id: number,
        name: string
    }

    interface ProductInList {
        id: number,
        product: Product,
        description: string
    }

    interface ShoppingList {
        id: number,
        idHouse: string,
        name: string,
        productList: Array<ProductInList>
    }

    const headers = {
        "Content-Type": "application/json",
        "Accept": "application/json"
    };

    const usernameLogged: string | null = localStorage.getItem("username");
    if (usernameLogged == null) {
        window.location.assign("/");
    }

    const title: string = "Shopping list";

    const [editList, setEditList] = useState<{[idItem: string]: boolean}> ({});
    const [nameShopL, setNameShopL] = useState<{[idItem: string]: boolean}>({});

    const [shoppingList, setShoppingList] = useState<ShoppingList[]>();
    const [newListName, setNewListName] = useState('');
    const [errorNewList, setErrorList] = useState('');
    const [errorNewProduct, setErrorNewProduct] = useState('');
    const [selectedShopL, setSelectedShopL] = useState<number>();
    const [newProductName, setNewProductName] = useState('');
    const [newProductDescription, setNewProductDescription] = useState('');



    const handleSubmitAddList = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // reload page after submit

        const shopl = {
            idHouse: localStorage.getItem("idHomeSelected"),
            name: newListName,
            products: []
        };

        axios.post(process.env.REACT_APP_API_URL+"/api/v1/shoppinglist/create", shopl,
            {headers})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                setErrorList("Error adding announcement to house, check the data and try again.");
            });
    }

    const handleDeleteList = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, id: number) => {
        event.preventDefault(); // reload page after submit

        axios.delete(process.env.REACT_APP_API_URL+"/api/v1/shoppinglist/delete/"+id, {})
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
        setNameShopL(nameShopL => ({...nameShopL, [key]: value}));
    }

    const handleSaveEditList = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, id: number) => {
        event.preventDefault(); // reload page after submit

        const shopl = {
            idHouse: localStorage.getItem("idHomeSelected"),
            name: nameShopL[id]
        };

        axios.put(process.env.REACT_APP_API_URL+"/api/v1/shoppinglist/update-metadata/"+id, shopl)
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error)
            });
    }

    const handleDeleteProduct = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, id: number) => {
        event.preventDefault(); // reload page after submit

        axios.delete(process.env.REACT_APP_API_URL+"/api/v1/shoppinglist/remove-product/"+id, {})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error)
            });
    }

    const onChangeShopL = (e: any) => {
        e.preventDefault();
        setSelectedShopL(e.target.value);
    };

    const handleSubmitAddProduct = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // reload page after submit
        const product = {
            name: newProductName,
            description: newProductDescription
        };

        axios.post(process.env.REACT_APP_API_URL+"/api/v1/shoppinglist/add-product/"+selectedShopL, product,
            {headers})
            .then(function (response) {
                window.location.reload();
            })
            .catch(function (error) {
                console.log(error);
                setErrorNewProduct("Error adding product to shopping list, check the data and try again.");
            });
    }

    React.useEffect(() => {
        axios.get(process.env.REACT_APP_API_URL+"/api/v1/shoppinglist/house/"+localStorage.getItem("idHomeSelected"), {
            headers: {}})
            .then((response: AxiosResponse<ShoppingList[]>) => {
                setShoppingList(response.data);
                setSelectedShopL(response.data[0].id);

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

    if (shoppingList === undefined) return (
        <div>
            <Spinner animation="border" role="status" className="spinner">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    );

    return (
        <div className="ShoppingList">
            <div className="ContentWrap">
                <Accordion>
                    <Accordion.Item eventKey="0">
                        <Accordion.Header>
                            <Container>
                                <Row>
                                    <Col xs={2}><LiaCartPlusSolid style={{fontSize: '30px'}}/></Col>
                                    <Col className="d-flex align-items-center">New Shopping List</Col>
                                </Row>
                            </Container>
                        </Accordion.Header>
                        <Accordion.Body>
                            <Form onSubmit={handleSubmitAddList}>
                                <Form.Group className="mb-3" controlId="description">
                                    <Form.Control required type="text" placeholder="Name" onChange={e => setNewListName(e.target.value)}/>
                                </Form.Group>
                                <div className="errorMessage">
                                    {errorNewList}
                                </div>
                                <Button type="submit" className="mb-4 w-100 HoMatesButton">
                                    Add ShoppingList
                                </Button>
                            </Form>
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>
                <Accordion>
                    <Accordion.Item eventKey="0">
                        <Accordion.Header>
                            <Container>
                                <Row>
                                    <Col xs={2}><LiaCartArrowDownSolid style={{fontSize: '30px'}}/></Col>
                                    <Col className="d-flex align-items-center">Add a product</Col>
                                </Row>
                            </Container>
                        </Accordion.Header>
                        <Accordion.Body>
                            <Form onSubmit={handleSubmitAddProduct}>
                                <Form.Group className="mb-3" controlId="nameProduct">
                                    <Form.Control required type="text" placeholder="Name" onChange={e => setNewProductName(e.target.value)}/>
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="descriptionProduct">
                                    <Form.Control required as="textarea" placeholder="Description" onChange={e => setNewProductDescription(e.target.value)}/>
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="shoopingList">
                                    <Form.Select onChange={(e) => onChangeShopL(e)} className="selectShoppingList">
                                        <option disabled>Choose your home</option>
                                        {
                                            shoppingList.map((shopl) => (
                                                <option value={shopl.id} key={shopl.id}>{shopl.name}</option>
                                            ))
                                        }
                                    </Form.Select>
                                </Form.Group>
                                <div className="errorMessage">
                                    {errorNewProduct}
                                </div>

                                <Button type="submit" className="mb-4 w-100 HoMatesButton">
                                    Add Product!
                                </Button>
                            </Form>
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>

                <div className="TitleContent">
                    <h3>
                        {title}
                    </h3>
                </div>

                {shoppingList.map((shopl) => (
                    <Card body>
                        <Container>
                            {!editList[shopl.id] && !isMobile &&
                                <Row>
                                    <Col xs={1} className="d-flex align-items-center"><CiStickyNote style={{fontSize: '30px'}}/></Col>
                                    <Col className="d-flex align-items-center">
                                        <div className="">
                                            <strong>Name:</strong>&nbsp;{shopl.name}
                                        </div>
                                    </Col>
                                    <Col xs={2} className="d-flex align-items-center">
                                        <Button className="action-button" onClick={(e) => {
                                            handleEditList(e, shopl.id, shopl.name);
                                        }}>
                                            <BiEditAlt style={{fontSize: '30px', color: '#000'}}/>
                                        </Button>&nbsp;
                                        <Button className="action-button" onClick={(e) => {
                                            handleDeleteList(e, shopl.id);
                                        }}>
                                            <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                        </Button>
                                    </Col>
                                </Row>
                            }
                            {!editList[shopl.id] && isMobile &&
                                <div>
                                    <Row className="d-flex align-items-center">
                                        <div className="">
                                            <strong>Name:</strong>&nbsp;{shopl.name}
                                        </div>
                                    </Row>
                                    <Row>
                                        <Col className="d-flex align-items-center">
                                            <Button className="action-button action-button-centre" onClick={(e) => {
                                                handleEditList(e, shopl.id, shopl.name);
                                            }}>
                                                <BiEditAlt style={{fontSize: '30px', color: '#000'}}/>
                                            </Button>
                                        </Col>
                                        <Col className="d-flex align-items-center">
                                            <Button className="action-button action-button-centre" onClick={(e) => {
                                                handleDeleteList(e, shopl.id);
                                            }}>
                                                <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                            </Button>
                                        </Col>
                                    </Row>
                                </div>
                            }
                            {editList[shopl.id] && !isMobile &&
                                <Form>
                                    <Row>
                                        <Col xs={1} className="d-flex align-items-center"><CiStickyNote style={{fontSize: '30px'}}/></Col>
                                        <Col className="d-flex align-items-center">
                                            <Form.Group className="" controlId="nameUser">
                                                <Form.Control required type="text" placeholder='Name' defaultValue={shopl.name}  onChange={e =>
                                                    setNameShopL(nameShopL => ({...nameShopL, [shopl.id]: e.target.value}))}/>
                                            </Form.Group>
                                        </Col>
                                        <Col xs={2} className="d-flex align-items-center">
                                            <Button className="HoMatesButton" onClick={(e) => {
                                                handleSaveEditList(e, shopl.id);
                                            }}>
                                                Save
                                            </Button>&nbsp;
                                            <Button className="action-button" onClick={(e) => {
                                                handleDeleteList(e, shopl.id);
                                            }}>
                                                <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                            </Button>
                                        </Col>
                                    </Row>
                                </Form>
                            }
                            {editList[shopl.id] && isMobile &&
                                <Form>
                                    <Row className="d-flex align-items-center">
                                        <div className="">
                                            <strong>Name:</strong>
                                        </div>
                                        <Form.Group className="" controlId="nameUser">
                                            <Form.Control required type="text" placeholder='Name' defaultValue={shopl.name}  onChange={e =>
                                                setNameShopL(nameShopL => ({...nameShopL, [shopl.id]: e.target.value}))}/>
                                        </Form.Group>
                                    </Row>
                                    <Row>
                                        <Col className="d-flex align-items-center">
                                            <Button className="HoMatesButton  action-button-centre" onClick={(e) => {
                                                handleSaveEditList(e, shopl.id);
                                            }}>
                                                Save
                                            </Button>
                                        </Col>
                                        <Col className="d-flex align-items-center">
                                            <Button className="action-button action-button-centre" onClick={(e) => {
                                                handleDeleteList(e, shopl.id);
                                            }}>
                                                <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                            </Button>
                                        </Col>
                                    </Row>
                                </Form>
                            }
                            <Row>
                                <Col>
                                    {
                                        shopl.productList.map(item => {
                                            return <Card body>
                                                <Container>
                                                    {!isMobile &&
                                                        <Row>
                                                            <Col>
                                                                <div className="">
                                                                    <strong>Item:</strong>&nbsp;{item.product.name}
                                                                </div>
                                                                <div className="">
                                                                    <strong>Description:</strong>&nbsp;{item.description}
                                                                </div>
                                                            </Col>
                                                            <Col xs={1} className="d-flex align-items-center">
                                                                <Button className="action-button" onClick={(e) => {
                                                                    handleDeleteProduct(e, item.id);
                                                                }}>
                                                                    <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                                </Button>
                                                            </Col>
                                                        </Row>
                                                    }
                                                    {isMobile &&
                                                        <div>
                                                            <Row>
                                                                <div className="">
                                                                    <strong>Item:</strong>&nbsp;{item.product.name}
                                                                </div>
                                                                <div className="">
                                                                    <strong>Description:</strong>&nbsp;{item.description}
                                                                </div>
                                                            </Row>
                                                            <Row className="d-flex align-items-center">
                                                                <Button className="action-button action-button-centre" onClick={(e) => {
                                                                    handleDeleteProduct(e, item.id);
                                                                }}>
                                                                    <MdDeleteForever style={{fontSize: '30px', color: '#FF914D'}}/>
                                                                </Button>
                                                            </Row>
                                                        </div>
                                                    }
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

export default ShoppingList;
