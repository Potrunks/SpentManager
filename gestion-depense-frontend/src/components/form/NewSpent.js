import React, { useState, useEffect } from "react";
import InputTestService from "../../services/InputTestService";
import SpentCategoryService from "../../services/SpentCategoryService";

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

  const handleChange = (e) => {
    const value = e.target.value;
    setSpent({ ...spent, [e.target.name]: value });
  };

  const createNewSpent = (e) => {
    e.preventDefault();
    if (InputTestService.verifyIntegrityNewSpent(spent) === true) {
      console.log(spent);
    }
  };

  useEffect(() => {
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
  }, []);

  return (
    <div className="app-main-container">
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
            {loading && <span className="loading-span">Loading...</span>}
            {!loading && (
              <select
                name="idSpentCategorySelected"
                onChange={(e) => handleChange(e)}
                className="select-form"
              >
                <option id="idSpentCategorySelected">Choose a category . . .</option>
                {spentCategories.map((spentCategory) => (
                  <option
                    key={spentCategory.idSpentCategory}
                    value={spentCategory.idSpentCategory}
                  >
                    {spentCategory.nameSpentCategory}
                  </option>
                ))}
              </select>
            )}
          </div>
        </div>
        <div className="main-button-container">
          <button onClick={createNewSpent}>Create</button>
          <button>Clear</button>
          <button>Cancel</button>
        </div>
      </div>
    </div>
  );
};

export default NewSpent;
