import React from "react";

const UsersInPeriodSpent = ({ users }) => {
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
              <span>{user.valueSpents} euros dépensés</span>
              <span>Salaire : {user.valueSalary} euros</span>
              <span>Taux de dépense : {Math.round(user.rateSpent)} %</span>
            </div>
            <div className="user-btn-command">
              <button>Modifier le salaire</button>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default UsersInPeriodSpent;
