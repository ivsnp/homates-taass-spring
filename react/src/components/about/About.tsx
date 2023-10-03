import React from 'react';
import './About.css';

function About() {
    const title: string = "About HoMates";
    const contentText: string = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Egestas maecenas pharetra convallis posuere morbi leo urna. Tellus in metus vulputate eu scelerisque. Pellentesque nec nam aliquam sem et tortor consequat. Tristique risus nec feugiat in. Nunc mi ipsum faucibus vitae. Sem nulla pharetra diam sit amet nisl suscipit adipiscing bibendum. Velit ut tortor pretium viverra suspendisse potenti nullam. Aliquet lectus proin nibh nisl. Vestibulum morbi blandit cursus risus at ultrices mi. Sed tempus urna et pharetra pharetra massa massa. In ornare quam viverra orci sagittis eu. Ut venenatis tellus in metus vulputate eu scelerisque felis. Tempor id eu nisl nunc mi ipsum faucibus vitae aliquet. Eu feugiat pretium nibh ipsum consequat nisl vel pretium. Eu consequat ac felis donec. Convallis convallis tellus id interdum velit.\n" +
        "\n" +
        "Non nisi est sit amet facilisis magna etiam. Nulla posuere sollicitudin aliquam ultrices sagittis orci. Purus gravida quis blandit turpis cursus in hac habitasse platea. Interdum velit laoreet id donec ultrices tincidunt arcu non sodales. Pellentesque habitant morbi tristique senectus et netus et malesuada. Id consectetur purus ut faucibus pulvinar. Consectetur libero id faucibus nisl tincidunt eget nullam non nisi. Varius quam quisque id diam vel. Ut tellus elementum sagittis vitae et. Turpis egestas pretium aenean pharetra magna ac placerat vestibulum lectus. Tellus in metus vulputate eu scelerisque. Nunc scelerisque viverra mauris in aliquam sem fringilla ut. Vitae et leo duis ut diam quam nulla porttitor. Quam nulla porttitor massa id.\n" +
        "\n" +
        "Et netus et malesuada fames ac turpis. Scelerisque fermentum dui faucibus in ornare quam viverra orci sagittis. Eu nisl nunc mi ipsum faucibus vitae aliquet nec. Ultrices vitae auctor eu augue. Elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique. Sit amet nisl suscipit adipiscing bibendum est ultricies integer. Sem nulla pharetra diam sit amet nisl suscipit adipiscing bibendum. Maecenas sed enim ut sem viverra aliquet. Eget lorem dolor sed viverra ipsum nunc aliquet bibendum. Molestie a iaculis at erat pellentesque. Rutrum quisque non tellus orci. Gravida quis blandit turpis cursus in hac.\n";

    return (
        <div className="About">
            <h2>{title}</h2>
            <p>{contentText}</p>
        </div>
    );
}

export default About;
