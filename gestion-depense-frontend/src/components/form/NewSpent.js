import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import InputTestService from "../../services/InputTestService";
import SpentCategoryService from "../../services/SpentCategoryService";
import SpentService from "../../services/SpentService";
import Loading from "../page/Loading";
import Confirm from "../Popup/Confirm";

const NewSpent = () => {
  const [spent, setSpent] = useState({
    valueSpent: "",
    nameSpent: "",
    commentSpent: "",
    idUserConnected: sessionStorage.getItem("idUserConnected"),
    idSpentCategorySelected: "",
  });
  const [loading, setLoading] = useState(true);
  const [spentCategories, setSpentCategories] = useState(null);
  const confirmMessage = "Are you sure to create a new spent ?";
  const [confirmPopup, setConfirmPopup] = useState(false);

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

  const createNewSpent = (e) => {
    //e.preventDefault();
    setConfirmPopup(false);
    console.log("Start to post a new spent for the API");
    SpentService.newSpent(spent)
      .then((response) => {
        if (response.data.periodSpentInProgressExist === false) {
          document.getElementById("API-error-box").firstChild.innerHTML =
            "Cannot find a spending period in progress. Please create a new spending period.";
          document.getElementById("API-error-box").style.display = "flex";
        } else if (response.data.newSpentAdded === false) {
          document.getElementById("API-error-box").firstChild.innerHTML =
            "Problem during creation of the new spent. Please contact the administrator.";
          document.getElementById("API-error-box").style.display = "flex";
        } else {
          console.log("New spent successfully added to the API");
          navigate("/success");
        }
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
      idUserConnected: sessionStorage.getItem("idUserConnected"),
      idSpentCategorySelected: "",
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
          const response = await SpentCategoryService.getAllSpentCategories();
          setSpentCategories(response.data);
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
            <span>New Spent</span>
          </div>
          <div className="API-error-box" id="API-error-box">
            <span></span>
          </div>
          <div className="main-input-field" id="form">
            <div className="input-field">
              <span>Value (euros)</span>
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
              <span>Name</span>
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
              <span>Comment</span>
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
              <span>Spent Category</span>
              <select
                id="idSpentCategorySelected"
                name="idSpentCategorySelected"
                onChange={(e) => handleChange(e)}
                className="select-form"
              >
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
          </div>
          <div className="main-button-container">
            <button
              onClick={(e) => {
                displayConfirmPopup(e);
              }}
            >
              Create
            </button>
            <button onClick={clearInput}>Clear</button>
            <button onClick={() => navigate("/menu")}>Cancel</button>
          </div>
        </div>
      )}
      {confirmPopup && (
        <Confirm
          parentSetConfirmPopup={setConfirmPopup}
          parentMethodToConfirm={createNewSpent}
          parentConfirmMessage={confirmMessage}
        />
      )}
    </div>
  );
};

export default NewSpent;
