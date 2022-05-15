import React from "react";
import { useNavigate } from "react-router-dom";
import nextArrow from "./next_arrow.png";
import previousArrow from "./previous_arrow.png";

const DateSpendingPeriodAndMore = ({ periodSpent }) => {
  const navigate = useNavigate();

  const goSpendingPeriodByID = (e, idPeriodSpent) => {
    e.preventDefault();
    if (idPeriodSpent !== null) {
      navigate(`/displayspendingperiod/${idPeriodSpent}`);
    }
  };

  return (
    <div className="main-date-spending-period-container">
      <img
        onClick={(e, idPeriodSpent) =>
          goSpendingPeriodByID(e, periodSpent.idPreviousPeriodSpent)
        }
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
        onClick={(e, idPeriodSpent) =>
          goSpendingPeriodByID(e, periodSpent.idNextPeriodSpent)
        }
        className="date-arrow"
        src={nextArrow}
        alt="Next Spending Period"
      ></img>
    </div>
  );
};

export default DateSpendingPeriodAndMore;
