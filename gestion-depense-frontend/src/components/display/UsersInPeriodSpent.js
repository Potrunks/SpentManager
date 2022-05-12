import React from "react";

const UsersInPeriodSpent = ({ users }) => {
  return (
    <div className="users-display-main-container">
      <div className="main-title-display">
        <span>Users</span>
      </div>
      {users.map((user) => (
        <div key={user.idUser} className="users-display-card-container">
          <div className="user-display-card">
            <span className="user-name">{user.firstNameUser}</span>
            <div className="user-period-spent-info">
              <span>Debt : {user.valueDebt} euros</span>
              <span>Spents : {user.valueSpents} euros</span>
              <span>Salary : {user.valueSalary} euros</span>
              <span>Rate : {user.rateSpent} %</span>
            </div>
            <div className="user-btn-command">
              <button>Modify Salary</button>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default UsersInPeriodSpent;
