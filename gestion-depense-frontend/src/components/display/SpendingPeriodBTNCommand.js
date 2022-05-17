import React from "react";
import { useNavigate } from "react-router-dom";

const SpendingPeriodBTNCommand = ({ periodSpent }) => {
  const navigate = useNavigate();
  return (
    <div className="spending-period-btn-main-container">
      {periodSpent.endDatePeriodSpent === null && (
        <div>
          <button onClick={() => navigate("/newspent")}>
            Nouvelle dépense
          </button>
        </div>
      )}
      <button onClick={() => navigate("/menu")}>Retour</button>
    </div>
  );
};

export default SpendingPeriodBTNCommand;
