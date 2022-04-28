import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import AccountService from "../../services/AccountService";

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
    if (AccountService.verifyIntegrityNewAccount(user) === true) {
      AccountService.postNewUser(user)
        .then((response) => {
          if (response.data.newAccountAdded === true) {
            console.log("New account created");
            navigate("/displaySpents");
          } else {
            console.log("New account canceled : See log of API app");
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
    <div>
      <div>
        <div>
          <span>New Account</span>
        </div>
        <div>
          <div>
            <span>First Name</span>
            <input
              type="text"
              name="firstNameUser"
              value={user.firstNameUser}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
          <div>
            <span>Last Name</span>
            <input
              type="text"
              name="lastNameUser"
              value={user.lastNameUser}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
          <div>
            <span>Mail</span>
            <input
              type="text"
              name="mailUser"
              value={user.mailUser}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
          <div>
            <span>Password (First time)</span>
            <input
              type="password"
              name="passwordUser"
              value={user.passwordUser}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
          <div>
            <span>Password (Second time)</span>
            <input
              type="password"
              name="secondPasswordUser"
              value={user.secondPasswordUser}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
          <div>
            <span>Administrator Password</span>
            <input
              type="password"
              name="adminPassword"
              value={user.adminPassword}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
        </div>
        <div>
          <button onClick={createNewAccount}>Create</button>
          <button onClick={clearInput}>Clear</button>
          <button onClick={() => navigate("/")}>Cancel</button>
        </div>
      </div>
    </div>
  );
};

export default CreateAccount;
