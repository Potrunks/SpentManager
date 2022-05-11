import React, { useEffect, useState } from "react";
import PeriodSpentService from "../../services/PeriodSpentService";
import DateSpendingPeriodAndMore from "./DateSpendingPeriodAndMore";

const SpendingPeriodInProgress = () => {
  const [periodSpent, setPeriodSpent] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await PeriodSpentService.getPeriodSpentInProgress();
        setPeriodSpent(response.data);
        console.log("Period spent in progress fetched successfully");
        setLoading(false);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, [loading]);

  return (
    <div className="app-main-container">
      {loading && <div>...Loading</div>}
      {!loading && <DateSpendingPeriodAndMore periodSpent={periodSpent} />}
    </div>
  );
};

export default SpendingPeriodInProgress;
