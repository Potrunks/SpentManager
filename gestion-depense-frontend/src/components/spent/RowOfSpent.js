import React from "react";
import { useNavigate } from "react-router-dom";

const RowOfSpent = ({ spent, deleteSpent }) => {
  const navigate = useNavigate();

  const goModifySpentComponent = (e, id) => {
    e.preventDefault();
    navigate(`/modifySpent/${id}`);
  };

  return (
    <tr key={spent.idSpent}>
      <td>{spent.valueSpent}</td>
      <td>{spent.dateSpent}</td>
      <td>
        <button onClick={(e, id) => goModifySpentComponent(e, spent.idSpent)}>
          Modify
        </button>
        <button onClick={(e, id) => deleteSpent(e, spent.idSpent)}>
          Delete
        </button>
      </td>
    </tr>
  );
};

export default RowOfSpent;
