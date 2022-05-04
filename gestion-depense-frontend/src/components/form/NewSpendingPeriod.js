import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

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
              type="text"
              name="valueSalary"
              value={salary.valueSalary}
              onChange={(e) => handleChange(e)}
            ></input>
          </div>
        </div>
        <div className="main-button-container">
          <button>Create</button>
          <button onClick={clearInput}>Clear</button>
          <button onClick={() => navigate("/menu")}>Cancel</button>
        </div>
      </div>
    </div>
  );
};

export default NewSpendingPeriod;
