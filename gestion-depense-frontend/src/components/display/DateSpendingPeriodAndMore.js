import React from "react";
import nextArrow from "./next_arrow.png";
import previousArrow from "./previous_arrow.png";

const DateSpendingPeriodAndMore = () => {
  return (
    <div className="main-date-spending-period-container">
      <img className="date-arrow" src={previousArrow} alt="Previous Spending Period"></img>
      <span>10-01-1987 to 10-01-1987</span>
      <img className="date-arrow" src={nextArrow} alt="Next Spending Period"></img>
    </div>
  );
};

export default DateSpendingPeriodAndMore;
