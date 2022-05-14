import React from "react";
import { useNavigate } from "react-router-dom";

const Confirm = ({
  parentSetConfirmPopup,
  parentMethodToConfirm,
  parentConfirmMessage,
}) => {
  const navigate = useNavigate();

  return (
    <div className="popup-main-container">
      <div className="popup-sub-container">
        <div className="popup-question-asked">
          <span>{parentConfirmMessage}</span>
        </div>
        <div className="main-button-container">
          <button
            onClick={() => {
              parentMethodToConfirm();
            }}
          >
            Confirmer
          </button>
          <button
            onClick={() => {
              parentSetConfirmPopup(false);
            }}
          >
            Annuler
          </button>
        </div>
      </div>
    </div>
  );
};

export default Confirm;
