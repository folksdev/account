import logo from './logo.svg';
import './App.css';
import { Link, withRouter } from 'react-router-dom';
import {Component} from "react";
import {Button, Form, FormGroup, Label, Input} from "reactstrap";

class Account extends Component {

    constructor(props) {
        super(props);
        this.state = {
            createAccountRequest : {
                customerId: '',
                initialCredit: 0
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        console.log(this.props.match.params.id)
        this.setState({
            createAccountRequest : {
                customerId: this.props.match.params.id,
                initialCredit: 0
            }})
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {createAccountRequest} = this.state;

        await fetch('/v1/account', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(createAccountRequest),
        });
        this.props.history.push('/');
    }



    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let createAccountRequest = {...this.state.createAccountRequest};
        createAccountRequest[name] = value;
        this.setState({createAccountRequest});
    }

    render() {
        const {createAccountRequest} = this.state;
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <div className="App-intro">
                        <h2>Create Account</h2>
                        <Form onSubmit={this.handleSubmit}>
                            <FormGroup>
                                <Label for="customerId">Customer Id</Label>
                                <Input type="text" name="customerId" id="customerId" value={createAccountRequest.customerId || ''}
                                       disabled/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="initialCredit">Initial Credit</Label>
                                <Input type="text" name="initialCredit" id="initialCredit" value={createAccountRequest.initialCredit}
                                       onChange={this.handleChange} autoComplete="initialCredit"/>
                            </FormGroup>
                            <FormGroup>
                                <Button color="primary" type="submit">Save</Button>{' '}
                                <Button color="secondary" tag={Link} to="/">Cancel</Button>
                            </FormGroup>
                        </Form>

                    </div>

                </header>
            </div>
        );
    }

}

export default Account;