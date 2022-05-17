import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import InputTestService from "../../services/InputTestService";
import SpentCategoryService from "../../services/SpentCategoryService";
import SpentService from "../../services/SpentService";
import UserService from "../../services/UserService";
import Loading from "../page/Loading";
import Confirm from "../Popup/Confirm";

const ModifySpent = () => {
  const { idSpent } = useParams();
  const [spent, setSpent] = useState({
    idSpent: idSpent,
    valueSpent: "",
    nameSpent: "",
    commentSpent: "",
    idUserExpenser: "",
    idSpentCategorySelected: "",
  });
  const [loading, setLoading] = useState(true);
  const [spentCategories, setSpentCategories] = useState(null);
  const confirmMessage = "Etes-vous sur de vouloir modifier la dépense ?";
  const [confirmPopup, setConfirmPopup] = useState(false);
  const [users, setUsers] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const value = e.target.value;
    setSpent({ ...spent, [e.target.name]: value });
  };

  const displayConfirmPopup = (e) => {
    if (
      InputTestService.verifyIntegrityNewSpent(spent) === true &&
      confirmPopup === false
    ) {
      setConfirmPopup(true);
    }
  };

  const modifySpent = (e) => {
    //e.preventDefault();
    SpentService.updateSpentByID(idSpent, spent)
      .then((response) => {
        navigate("/success");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const clearInput = (e) => {
    e.preventDefault();
    setSpent({
      valueSpent: "",
      nameSpent: "",
      commentSpent: "",
    });
  };

  useEffect(() => {
    if (sessionStorage.getItem("idUserConnected") === null) {
      console.log("User no connected");
      navigate("/");
    } else {
      const fetchData = async () => {
        setLoading(true);
        console.log("Start loading...");
        try {
          const responseSpent = await SpentService.getSpentByID(idSpent);
          const response = await SpentCategoryService.getAllSpentCategories();
          const responseUsers = await UserService.getAllUsers();
          setSpent(responseSpent.data);
          setSpentCategories(response.data);
          setUsers(responseUsers.data);
        } catch (error) {
          console.log(error);
        }
        setLoading(false);
        console.log("Data loaded");
      };
      fetchData();
    }
  }, []);

  return (
    <div className="app-main-container">
      {loading && <Loading />}
      {!loading && (
        <div className="main-form-container">
          <div className="main-title-form">
            <span>Ajouter une dépense</span>
          </div>
          <div className="API-error-box" id="API-error-box">
            <span></span>
          </div>
          <div className="main-input-field" id="form">
            <div className="input-field">
              <span>Prix (euros)</span>
              <input
                type="number"
                placeholder=""
                id="valueSpent"
                name="valueSpent"
                value={spent.valueSpent}
                onChange={(e) => handleChange(e)}
              ></input>
            </div>
            <div className="input-field">
              <span>Nom</span>
              <input
                type="text"
                placeholder=""
                id="nameSpent"
                name="nameSpent"
                value={spent.nameSpent}
                onChange={(e) => handleChange(e)}
              ></input>
            </div>
            <div className="input-field">
              <span>Commentaire</span>
              <input
                type="text"
                placeholder=""
                id="commentSpent"
                name="commentSpent"
                value={spent.commentSpent}
                onChange={(e) => handleChange(e)}
              ></input>
            </div>
            <div className="main-select-container">
              <span>Catégorie de la dépense</span>
              <select
                id="idSpentCategorySelected"
                name="idSpentCategorySelected"
                onChange={(e) => handleChange(e)}
                className="select-form"
                defaultValue={spent.idSpentCategorySelected}
              >
                <option value="">Choisissez une catégorie...</option>
                {spentCategories.map((spentCategory) => (
                  <option
                    key={spentCategory.idSpentCategory}
                    value={spentCategory.idSpentCategory}
                  >
                    {spentCategory.nameSpentCategory}
                  </option>
                ))}
              </select>
            </div>
            <div className="main-select-container">
              <span>Par qui ?</span>
              <select
                id="idUserExpenser"
                name="idUserExpenser"
                onChange={(e) => handleChange(e)}
                className="select-form"
                defaultValue={spent.idUserExpenser}
              >
                <option value="">Choisissez un utilisateur...</option>
                {users.map((user) => (
                  <option key={user.idUser} value={user.idUser}>
                    {user.firstNameUser}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <div className="main-button-container">
            <button
              onClick={(e) => {
                displayConfirmPopup(e);
              }}
            >
              Modifier
            </button>
            <button onClick={clearInput}>Effacer les champs</button>
            <button onClick={() => navigate("/menu")}>Annuler</button>
          </div>
        </div>
      )}
      {confirmPopup && (
        <Confirm
          parentSetConfirmPopup={setConfirmPopup}
          parentMethodToConfirm={modifySpent}
          parentConfirmMessage={confirmMessage}
        />
      )}
    </div>
  );
};

export default ModifySpent;
