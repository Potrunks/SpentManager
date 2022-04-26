import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import SpentService from "../../services/SpentService";

const ModifySpent = () => {
  const { id } = useParams();
  const [spent, setSpent] = useState({
    idSpent: id,
    valueSpent: "",
    dateSpent: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const value = e.target.value;
    setSpent({ ...spent, [e.target.name]: value });
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await SpentService.getSpent(id);
        setSpent(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const modifySpent = (e) => {
    e.preventDefault();
    SpentService.updateSpent(spent, id)
      .then((response) => {
        navigate("/displaySpents");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div>
      <div>
        <span>Modify Spent Mode</span>
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
          <button onClick={modifySpent}>Modify</button>
          <button onClick={() => navigate("/displaySpents")}>Cancel</button>
        </div>
      </div>
    </div>
  );
};

export default ModifySpent;
