import React, { useState, useEffect } from "react";
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

  useEffect(() => {
    sessionStorage.clear();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const logIn = (e) => {
    e.preventDefault();
    console.log("Start to attempt LogIn");
    if (InputTestService.verifyIntegrityLogin(user) === true) {
      AccountService.postUserForLogIn(user)
        .then((response) => {
          if (response.data.mailExisted === false) {
            document.getElementById("API-error-box").firstChild.innerHTML =
              "This mail have no account";
            document.getElementById("API-error-box").style.display = "flex";
          } else {
            if (response.data.authenticated === false) {
              document.getElementById("API-error-box").firstChild.innerHTML =
                "Mail or password is not correct";
              document.getElementById("API-error-box").style.display = "flex";
            } else {
              console.log("User authenticated successfully");
              sessionStorage.setItem("idUserConnected", response.data.idUserConnected);
              navigate(`/menu`);
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
          <span></span>
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
