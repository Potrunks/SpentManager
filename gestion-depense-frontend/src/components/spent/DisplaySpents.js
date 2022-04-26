import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import SpentService from "../../services/SpentService";
import RowOfSpent from "./RowOfSpent";

const DisplaySpents = () => {
  const navigate = useNavigate();

  const [loading, setLoading] = useState(true);
  const [spents, setSpents] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await SpentService.getSpentsFromBackend();
        setSpents(response.data);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, []);

  const deleteSpent = (e, id) => {
    e.preventDefault();
    SpentService.deleteSpent(id).then((response) => {
      if (spents) {
        setSpents((previousElement) => {
          return previousElement.filter((spent) => spent.idSpent !== id);
        });
      }
    });
  };

  return (
    <div>
      <div>
        <button onClick={() => navigate("/addSpent")}>Add Spent</button>
      </div>
      <div>
        <table>
          <thead>
            <tr>
              <th>Value</th>
              <th>Date</th>
              <th>Action</th>
            </tr>
          </thead>
          {!loading && (
            <tbody>
              {spents.map((spent) => (
                <RowOfSpent
                  spent={spent}
                  deleteSpent={deleteSpent}
                  key={spent.idSpent}
                />
              ))}
            </tbody>
          )}
        </table>
      </div>
    </div>
  );
};

export default DisplaySpents;
