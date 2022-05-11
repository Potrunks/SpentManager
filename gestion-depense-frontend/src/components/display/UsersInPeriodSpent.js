import React from "react";

const UsersInPeriodSpent = ({ users }) => {
  return (
    <div className="users-display-main-container">
      <div className="main-title-display">
        <span>Users</span>
      </div>
      <div className="users-display-card-container">
        <div className="user-display-card">
          <span className="user-name">Alexis</span>
          <div className="user-period-spent-info">
            <span>Debt : 2000 euros</span>
            <span>Spent : 2000 euros</span>
            <span>Salary : 2000 euros</span>
            <span>Rate : 22%</span>
          </div>
          <div className="user-btn-command">
            <button>Modify Salary</button>
          </div>
        </div>
      </div>
      <div className="users-display-card-container">
        <div className="user-display-card">
          <span className="user-name">Alexis</span>
          <div className="user-period-spent-info">
            <span>Debt : 2000 euros</span>
            <span>Spent : 2000 euros</span>
            <span>Salary : 2000 euros</span>
            <span>Rate : 22%</span>
          </div>
          <div className="user-btn-command">
            <button>Modify Salary</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UsersInPeriodSpent;
