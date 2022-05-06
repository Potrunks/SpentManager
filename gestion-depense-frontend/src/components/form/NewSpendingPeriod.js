import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import InputTestService from "../../services/InputTestService";
import PeriodSpentService from "../../services/PeriodSpentService";

const NewSpendingPeriod = () => {
  const [salary, setSalary] = useState({
    valueSalary: "",
  });

  const handleChange = (e) => {
    const value = e.target.value;
    setSalary({ ...salary, [e.target.name]: value });
  };

  const navigate = useNavigate();

  const clearInput = (e) => {
    e.preventDefault();
    setSalary({
      valueSalary: "",
    });
  };

  useEffect(() => {
    if (sessionStorage.getItem("idUserConnected") === null) {
      console.log("User no connected");
      navigate("/");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const createNewSpendingPeriod = (e) => {
    e.preventDefault();
    // TO DO : pop-up de confirmation
    console.log("Start to create a new spending period");
    if (InputTestService.verifyIntegrityNewSpendingPeriod(salary) === true) {
      PeriodSpentService.postNewPeriodSpent(
        sessionStorage.getItem("idUserConnected"),
        salary
      )
        .then((response) => {
          if (response.data.periodSpentAdded === true) {
            console.log("New period spent created");
            // TO DO : go vers une page de succés
            navigate("/menu");
          } else if (response.data.periodSpentInProgressIsClosable === false) {
            document.getElementById("API-error-box").firstChild.innerHTML =
              "Cannot create new Spending Period because there is only one salary on the Spending Period in progress";
            document.getElementById("API-error-box").style.display = "flex";
          }
          // TO DO : message d'erreur personnalisé
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  return (
    <div className="app-main-container">
      <div className="main-form-container">
        <div className="main-title-form">
          <span>New spending period</span>
        </div>
        <div className="API-error-box" id="API-error-box">
          <span></span>
        </div>
        <div className="main-input-field" id="form">
          <div className="input-field">
            <span>Salary (euros)</span>
            <input
              placeholder=""
              id="valueSalary"
              type="number"
              name="valueSalary"
              value={salary.valueSalary}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
        </div>
        <div className="main-button-container">
          <button onClick={createNewSpendingPeriod}>Create</button>
          <button onClick={clearInput}>Clear</button>
          <button onClick={() => navigate("/menu")}>Cancel</button>
        </div>
      </div>
    </div>
  );
};

export default NewSpendingPeriod;
