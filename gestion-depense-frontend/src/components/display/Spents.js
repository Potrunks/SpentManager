import React from "react";
import { useNavigate } from "react-router-dom";

const Spents = ({ spents, periodSpent }) => {
  const navigate = useNavigate();

  return (
    <div className="spents-main-container">
      <div className="main-title-display">
        <span>Les dépenses</span>
      </div>
      {spents.map((spent) => (
        <div key={spent.idSpent} className="spents-card-container">
          <div className="spent-card">
            <div className="spent-card-title">
              <span>{spent.nameSpent}</span>
            </div>
            <div className="spent-card-info">
              <span>Fait le {spent.dateSpent}</span>
              <span>Prix : {spent.valueSpent} euros</span>
              <span>Par {spent.nameUserWhoCreate}</span>
              <span>Catégorie : {spent.nameSpentCategory}</span>
              {spent.commentSpent !== "" && (
                <span className="spent-comment">
                  Commentaire : {spent.commentSpent}
                </span>
              )}
            </div>
            {periodSpent.endDatePeriodSpent === null && (
              <div className="spent-card-btn-command">
                <button
                  onClick={() => navigate(`/modifySpent/${spent.idSpent}`)}
                >
                  Modifier
                </button>
                <button
                  onClick={() => navigate(`/deleteSpent/${spent.idSpent}`)}
                >
                  Supprimer
                </button>
              </div>
            )}
          </div>
        </div>
      ))}
    </div>
  );
};

export default Spents;
