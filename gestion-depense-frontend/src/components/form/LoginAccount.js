import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import AccountService from "../../services/AccountService";
import InputTestService from "../../services/InputTestService";

const LoginAccount = () => {
  const [user, setUser] = useState({
    mailUser: "",
    passwordUser: "",
  });

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
  };

  const logIn = (e) => {
    e.preventDefault();
    console.log("Start to attempt LogIn");
    if (InputTestService.verifyIntegrityLogin(user) === true) {
      AccountService.postUserForLogIn(user)
        .then((response) => {
          if (response.data.mailExisted === false) {
            console.log(
              "The mail don't exist. Verify the input or create an account."
            );
          } else {
            if (response.data.authenticated === false) {
              console.log("Mail or password is incorrect");
            } else {
                console.log("User authenticated successfully")
              navigate(`/displaySpents/${response.data.idUserConnected}`);
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  const clearInput = (e) => {
    e.preventDefault();
    setUser({
      mailUser: "",
      passwordUser: "",
    });
  };

  const navigate = useNavigate();

  return (
    <div className="app-main-container">
      <div className="main-form-container">
        <div className="main-title-form">
          <span>Log In</span>
        </div>
        <div className="API-error-box" id="API-error-box">
          <span>Mail already exist or admin password is invalid</span>
        </div>
        <div className="main-input-field" id="form">
          <div className="input-field">
            <span>Mail</span>
            <input
              type="text"
              placeholder=""
              id="mailUser"
              name="mailUser"
              value={user.mailUser}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
          <div className="input-field">
            <span>Password</span>
            <input
              type="password"
              placeholder=""
              id="passwordUser"
              name="passwordUser"
              value={user.passwordUser}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
        </div>
        <div className="main-button-container">
          <button onClick={logIn}>Log In</button>
          <button onClick={clearInput}>Clear</button>
          <button onClick={() => navigate("/createAccount")}>
            Create an account
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginAccount;
