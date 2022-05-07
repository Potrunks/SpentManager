import React from "react";

const Confirm = ({
  parentSetConfirmPopup,
  parentCreateNewSpendingPeriod,
  parentConfirmMessage,
}) => {
  return (
    <div className="popup-main-container">
      <div className="popup-sub-container">
        <div className="popup-question-asked">
          <span>{parentConfirmMessage}</span>
        </div>
        <div className="main-button-container">
          <button
            onClick={() => {
              parentCreateNewSpendingPeriod();
            }}
          >
            Confirm
          </button>
          <button
            onClick={() => {
              parentSetConfirmPopup(false);
            }}
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
};

export default Confirm;
