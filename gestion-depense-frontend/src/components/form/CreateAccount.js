import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AccountService from "../../services/AccountService";
import InputTestService from "../../services/InputTestService";
import Confirm from "../Popup/Confirm";

const CreateAccount = () => {
  const confirmMessage = "Etes-vous sur de vouloir créer un nouveau compte ?";
  const [user, setUser] = useState({
    lastNameUser: "",
    firstNameUser: "",
    mailUser: "",
    passwordUser: "",
    secondPasswordUser: "",
    adminPassword: "",
  });

  useEffect(() => {
    sessionStorage.clear();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const navigate = useNavigate();
  const [confirmPopup, setConfirmPopup] = useState(false);
  const handleChange = (e) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
  };

  const displayConfirmPopup = (e) => {
    if (
      InputTestService.verifyIntegrityNewAccount(user) === true &&
      confirmPopup === false
    ) {
      setConfirmPopup(true);
    }
  };

  const createNewAccount = (e) => {
    //e.preventDefault();
    setConfirmPopup(false);
    console.log("Start to create new account");
    AccountService.postNewUser(user)
      .then((response) => {
        if (response.data.newAccountAdded === true) {
          console.log("New account created");
          navigate("/success");
        } else {
          if (response.data.already2Accounts === true) {
            document.getElementById("API-error-box").firstChild.innerHTML =
              "La création d'un nouveau compte est désactivé";
            document.getElementById("API-error-box").style.display = "flex";
          } else if (response.data.adminPasswordOK === false) {
            document.getElementById("API-error-box").firstChild.innerHTML =
              "Le mot de passe administrateur est incorrect";
            document.getElementById("API-error-box").style.display = "flex";
          } else if (response.data.mailAlreadyExist === true) {
            document.getElementById("API-error-box").firstChild.innerHTML =
              "Cet e-mail a déjà un compte";
            document.getElementById("API-error-box").style.display = "flex";
          }
        }
      })
      .catch((error) => {
        console.log(error);
      });
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
          <span>Creer un compte</span>
        </div>
        <div className="API-error-box" id="API-error-box">
          <span></span>
        </div>
        <div className="main-input-field" id="form">
          <div className="input-field">
            <span>Prénom</span>
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
            <span>Nom</span>
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
          <div className="input-field">
            <span>Re-taper le mot de passe</span>
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
            <span>Mot de passe administrateur</span>
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
          <button
            onClick={(e) => {
              displayConfirmPopup(e);
            }}
          >
            Créer
          </button>
          <button onClick={clearInput}>Effacer les champs</button>
          <button onClick={() => navigate("/")}>Annuler</button>
        </div>
      </div>
      {confirmPopup && (
        <Confirm
          parentSetConfirmPopup={setConfirmPopup}
          parentMethodToConfirm={createNewAccount}
          parentConfirmMessage={confirmMessage}
        />
      )}
    </div>
  );
};

export default CreateAccount;
