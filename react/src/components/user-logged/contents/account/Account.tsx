import React, {useEffect, useState} from 'react';
import './Account.css';
import {Button, Form, Spinner} from "react-bootstrap";
import axios, {AxiosResponse} from "axios";

function Account() {

    interface UserAttributes {
        username: string,
        name: string,
        surname: string,
        email: string,
        bio: string
    }

    const title: string = "Account";
    const [userdata, setUserdata] = useState<UserAttributes>();
    const [username, setUsername] = useState('');
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [email, setEmail] = useState('');
    const [bio, setBio] = useState('');

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // reload page after submit
        const user = {
            username: username,
            name: name,
            surname: surname,
            email: email,
            bio: bio
        };
        const headers = {
            "Content-Type": "application/json",
            "Accept": "application/json"
        };
        console.log(user);
        alert(user);

        axios.put("http://localhost:8080/api/v1/user-houses/user/update/ivsnp", user, {headers})
            .then(function (response) {
                //event.preventDefault();
                alert('ok');
                console.log(response);
                alert('ok');
            })
            .catch(function (error) {
                alert('not ok');
                console.log(error);
                alert('not ok');
            });
    }

    React.useEffect(() => {
        axios.get("http://localhost:8080/api/v1/user-houses/user/ivsnp", {
            headers: {}})
            .then((response: AxiosResponse<UserAttributes>) => {
                setUserdata(response.data);

                setUsername(response.data.username);
                setName(response.data.name);
                setSurname(response.data.surname);
                setEmail(response.data.email);
                setBio(response.data.bio);
            })
            .catch(error => {
                console.log(error)
            });
    }, []);

    if (userdata === undefined) return (
        <div>
            <Spinner animation="border" role="status" className="spinner">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    );

    // @ts-ignore
    return (
        <div className="UserAccount">
            <div className="UserImage">
                <img src="/img/users/user_image_default.png" alt="User icon"/>
            </div>

            <div className="ContentWrap">
                <h3>
                    {title}
                </h3>
                <div className="UserDataForm">
                    <Form.Group className="mb-3" controlId="username">
                        <Form.Label>Username</Form.Label>
                        <Form.Control disabled type="text" placeholder='Username' value={userdata.username} />
                    </Form.Group>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="nameUser">
                            <Form.Label>Name</Form.Label>
                            <Form.Control required type="text" placeholder='Name' defaultValue={userdata.name}  onChange={e => setName(e.target.value)}/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="surnameUser">
                            <Form.Label>Surname</Form.Label>
                            <Form.Control required type="text" placeholder='Surname' defaultValue={userdata.surname}  onChange={e => setSurname(e.target.value)}/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="emailUser">
                            <Form.Label>Email</Form.Label>
                            <Form.Control required type="email" placeholder='Email' defaultValue={userdata.email}  onChange={e => setEmail(e.target.value)}/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="bioUser">
                            <Form.Label>Bio</Form.Label>
                            <Form.Control required as="textarea" rows={3} placeholder='Bio' defaultValue={userdata.bio}  onChange={e => setBio(e.target.value)}/>
                        </Form.Group>

                        <Button type="submit" className="mb-4 w-100 HoMatesButton">
                            Save
                        </Button>
                    </Form>
                </div>
            </div>
        </div>
    );
}

export default Account;