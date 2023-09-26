import React from 'react';
import './Contacts.css';
import {FiMapPin, FiPhone, FiMail} from "react-icons/fi";
import {Container, Row, Col} from "react-bootstrap";

function Contacts() {
    const title: string = "Contact HoMates";
    const contentText: string = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Egestas maecenas pharetra convallis posuere morbi leo urna. Tellus in metus vulputate eu scelerisque. Pellentesque nec nam aliquam sem et tortor consequat. Tristique risus nec feugiat in. Nunc mi ipsum faucibus vitae. Sem nulla pharetra diam sit amet nisl suscipit adipiscing bibendum. Velit ut tortor pretium viverra suspendisse potenti nullam. Aliquet lectus proin nibh nisl. Vestibulum morbi blandit cursus risus at ultrices mi. Sed tempus urna et pharetra pharetra massa massa. In ornare quam viverra orci sagittis eu. Ut venenatis tellus in metus vulputate eu scelerisque felis. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Eu feugiat pretium nibh ipsum consequat nisl vel pretium. Eu consequat ac felis donec. Convallis convallis tellus id interdum velit.\n";

    return (
        <div className="Contacts">
            <h2>{title}</h2>
            <p>{contentText}</p>

            <address>
                <div className="Brand">
                    HoMates Corporation
                </div>
                <div className="Contacts">
                    <Container>
                        <Row>
                            <Col xs={1}><FiMapPin style={{fontSize: '20px'}}/></Col>
                            <Col>Corso Svizzera, 185, 10149 Torino TO, Italy</Col>
                        </Row>
                        <Row>
                            <Col xs={1}><FiPhone style={{fontSize: '20px'}}/></Col>
                            <Col>(+39) 123 456 7890</Col>
                        </Row>
                        <Row>
                            <Col xs={1}><FiMail style={{fontSize: '20px'}}/></Col>
                            <Col><a href="mailto:example@example.com">example@example.com</a></Col>
                        </Row>
                    </Container>
                </div>
            </address>
        </div>
    );
}

export default Contacts;
