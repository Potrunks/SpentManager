import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import SpentService from "../../services/SpentService";

const AddSpent = () => {
  const [spent, setSpent] = useState({
    idSpent: "",
    valueSpent: "",
    dateSpent: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const value = e.target.value;
    setSpent({ ...spent, [e.target.name]: value });
  };

  const newSpent = (e) => {
    e.preventDefault();
    SpentService.newSpent(spent)
      .then((response) => {
        navigate("/displaySpents");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const clear = (e) => {
    e.preventDefault();
    setSpent({
      idSpent: "",
      valueSpent: "",
      dateSpent: "",
    });
  };

  return (
    <div>
      <div>
        <span>Add Spent Mode</span>
      </div>
      <div>
        <div>
          <span>Value (euros)</span>
          <input
            type="text"
            name="valueSpent"
            value={spent.valueSpent}
            onChange={(e) => handleChange(e)}
          ></input>
        </div>
        <div>
          <span>Date</span>
          <input
            type="date"
            name="dateSpent"
            value={spent.dateSpent}
            onChange={(e) => handleChange(e)}
          ></input>
        </div>
        <div>
          <button onClick={newSpent}>Add</button>
          <button onClick={clear}>Clear</button>
          <button onClick={() => navigate("/displaySpents")}>Cancel</button>
        </div>
      </div>
    </div>
  );
};

export default AddSpent;
