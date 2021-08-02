import logo from './logo.svg';
import './App.css';
import {Component} from "react";
import {Link, withRouter} from 'react-router-dom';
import {Button, Container} from 'reactstrap';

class Customer extends Component {
    state = {
        customers: [{
            accounts: [{
                    transactions: []
                }]
        }]
    };

    async componentDidMount() {
        const response = await fetch('/v1/customer');
        const body = await response.json();
        this.setState({customers: body});
    }


    render() {
        const {customers} = this.state;
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <div className="App-intro">
                        <h2>Customer</h2>
                        {customers.map(customer =>
                        <div key={customer.id}>
                            {customer.name} {customer.surname}

                            {
                                customer.accounts.map(account =>
                                    (<p> Accounts: {account.id}, {account.balance} , {account.creationDate}
                                        {
                                            account.transactions.map(transaction =>
                                                (<p> Transactions
                                                    : {transaction.id} , {transaction.amount}, {transaction.transactionDate}, {transaction.transactionType}</p>)
                                            )}
                                    </p>)
                                )
                            }
                            <Button color="link"><Link to={`/account/${customer.id}`}>Account</Link></Button>
                        </div>
                        )}
                    </div>
                </header>
            </div>
        );
    }

}

export default Customer;
