import React from "react";
import { useNavigate } from "react-router-dom";

const UsersInPeriodSpent = ({ users, periodSpent }) => {

  const navigate = useNavigate();

  return (
    <div className="users-display-main-container">
      <div className="main-title-display">
        <span>Les utilisateurs</span>
      </div>
      {users.map((user) => (
        <div key={user.idUser} className="users-display-card-container">
          <div className="user-display-card">
            <span className="user-name">{user.firstNameUser}</span>
            <div className="user-period-spent-info">
              {user.valueDebt > 0 ? (
                <span>Dette : {Math.round(user.valueDebt)} euros</span>
              ) : (
                <span>Pas de dette</span>
              )}
              {user.valueSpents > 0 ? (
                <span>{user.valueSpents} euros dépensés</span>
              ) : (
                <span>Pas de dépense</span>
              )}
              {user.valueSalary > 0 ? (
                <span>Salaire : {user.valueSalary} euros</span>
              ) : (
                <span>Pas de salaire</span>
              )}
              {user.rateSpent > 0 ? (
                <span>Taux de dépense : {Math.round(user.rateSpent)} %</span>
              ) : (
                <span>Pas de taux de dépense</span>
              )}
            </div>
            {periodSpent.endDatePeriodSpent === null && (
              <div className="user-btn-command">
                <button onClick={() => navigate(`/modifySalary/${user.idSalary}`)}>Modifier le salaire</button>
              </div>
            )}
          </div>
        </div>
      ))}
    </div>
  );
};

export default UsersInPeriodSpent;
