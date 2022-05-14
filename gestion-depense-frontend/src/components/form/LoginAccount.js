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
    document.getElementById("main-app-title").innerHTML = "Spent Manager v1.0.0";
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
              "Cet e-mail n'a pas de compte";
            document.getElementById("API-error-box").style.display = "flex";
          } else {
            if (response.data.authenticated === false) {
              document.getElementById("API-error-box").firstChild.innerHTML =
                "E-mail ou mot de passe incorrect";
              document.getElementById("API-error-box").style.display = "flex";
            } else {
              console.log("User authenticated successfully");
              sessionStorage.setItem("idUserConnected", response.data.idUserConnected);
              sessionStorage.setItem("firstNameUserConnected", response.data.firstNameUserConnected);
              document.getElementById("main-app-title").innerHTML = "Bienvenue " + response.data.firstNameUserConnected;
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
          <span>Se connecter</span>
        </div>
        <div className="API-error-box" id="API-error-box">
          <span></span>
        </div>
        <div className="main-input-field" id="form">
          <div className="input-field">
            <span>E-mail</span>
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
            <span>Mot de passe</span>
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
          <button onClick={logIn}>Se connecter</button>
          <button onClick={clearInput}>Effacer les champs</button>
          <button onClick={() => navigate("/createAccount")}>
            Je n'ai pas de compte
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginAccount;
