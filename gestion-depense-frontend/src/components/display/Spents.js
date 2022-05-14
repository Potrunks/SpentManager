import React from "react";

const Spents = ({ spents }) => {
  return (
    <div className="spents-main-container">
      <div className="main-title-display">
        <span>Spents</span>
      </div>
      {spents.map((spent) => (
        <div key={spent.idSpent} className="spents-card-container">
          <div className="spent-card">
            <div className="spent-card-title">
              <span>{spent.nameSpent}</span>
            </div>
            <div className="spent-card-info">
              <span>Date : {spent.dateSpent}</span>
              <span>Price : {spent.valueSpent} euros</span>
              <span>By : {spent.nameUserWhoCreate}</span>
              <span>Category : {spent.nameSpentCategory}</span>
              <span>Comment : {spent.commentSpent}</span>
            </div>
            <div className="spent-card-btn-command">
              <button>Modify</button>
              <button>Delete</button>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default Spents;
