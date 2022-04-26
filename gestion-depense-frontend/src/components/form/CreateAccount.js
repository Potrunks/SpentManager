import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import AccountService from "../../services/AccountService";

const CreateAccount = () => {
  const [user, setUser] = useState({
    idUser: "",
    lastNameUser: "",
    firstNameUser: "",
    mailUser: "",
    passwordUser: "",
    secondPasswordUser: "",
    saltUser: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
  };

  const createNewAccount = (e) => {
    e.preventDefault();
    AccountService.postNewUser(user)
    .then((response) => {
      console.log(response + " : New account created");
      navigate("/displaySpents");
    })
    .catch((error) => {
      console.log(error);
    })
  };

  const clearInput = (e) => {
    e.preventDefault();
    setUser({
      idUser: "",
      lastNameUser: "",
      firstNameUser: "",
      mailUser: "",
      passwordUser: "",
      secondPasswordUser: "",
      saltUser: "",
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
