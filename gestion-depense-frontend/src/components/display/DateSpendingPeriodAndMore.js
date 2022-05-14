import React from "react";
import nextArrow from "./next_arrow.png";
import previousArrow from "./previous_arrow.png";

const DateSpendingPeriodAndMore = ({ periodSpent }) => {
  return (
    <div className="main-date-spending-period-container">
      <img
        className="date-arrow"
        src={previousArrow}
        alt="Previous Spending Period"
      ></img>
      {periodSpent.endDatePeriodSpent !== null ? (
        <span>
          {periodSpent.startDatePeriodSpent} / {periodSpent.endDatePeriodSpent}
        </span>
      ) : (
        <span>{periodSpent.startDatePeriodSpent} / "En cours"</span>
      )}
      <img
        className="date-arrow"
        src={nextArrow}
        alt="Next Spending Period"
      ></img>
    </div>
  );
};

export default DateSpendingPeriodAndMore;
