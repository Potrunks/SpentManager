import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import AccountService from "../../services/AccountService";
import InputTestService from "../../services/InputTestService";

const CreateAccount = () => {
  const [user, setUser] = useState({
    lastNameUser: "",
    firstNameUser: "",
    mailUser: "",
    passwordUser: "",
    secondPasswordUser: "",
    adminPassword: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
  };

  const createNewAccount = (e) => {
    e.preventDefault();
    console.log("Start to create new account");
    if (InputTestService.verifyIntegrityNewAccount(user) === true) {
      AccountService.postNewUser(user)
        .then((response) => {
          if (response.data.newAccountAdded === true) {
            console.log("New account created");
            navigate("/displaySpents");
          } else {
            document.getElementById("API-error-box").style.display = "flex";
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
      lastNameUser: "",
      firstNameUser: "",
      mailUser: "",
      passwordUser: "",
      secondPasswordUser: "",
      adminPassword: "",
    });
  };

  return (
    <div className="app-main-container">
      <div className="main-form-container">
        <div className="main-title-form">
          <span>New Account</span>
        </div>
        <div className="API-error-box" id="API-error-box">
          <span>Mail already exist or admin password is invalid</span>
        </div>
        <div className="main-input-field" id="form">
          <div className="input-field">
            <span>First Name</span>
            <input
              placeholder=""
              id="firstNameUser"
              type="text"
              name="firstNameUser"
              value={user.firstNameUser}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
          <div className="input-field">
            <span>Last Name</span>
            <input
              type="text"
              placeholder=""
              id="lastNameUser"
              name="lastNameUser"
              value={user.lastNameUser}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
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
            <span>Password (First time)</span>
            <input
              type="password"
              placeholder=""
              id="passwordUser"
              name="passwordUser"
              value={user.passwordUser}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
          <div className="input-field">
            <span>Password (Second time)</span>
            <input
              id="secondPasswordUser"
              placeholder=""
              type="password"
              name="secondPasswordUser"
              value={user.secondPasswordUser}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
          <div className="input-field">
            <span>Administrator Password</span>
            <input
              id="adminPassword"
              placeholder=""
              type="password"
              name="adminPassword"
              value={user.adminPassword}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
        </div>
        <div className="main-button-container">
          <button onClick={createNewAccount}>Create</button>
          <button onClick={clearInput}>Clear</button>
          <button onClick={() => navigate("/")}>Cancel</button>
        </div>
      </div>
    </div>
  );
};

export default CreateAccount;
