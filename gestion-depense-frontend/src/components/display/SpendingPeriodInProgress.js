import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PeriodSpentService from "../../services/PeriodSpentService";
import UserService from "../../services/UserService";
import DateSpendingPeriodAndMore from "./DateSpendingPeriodAndMore";
import SpendingPeriodBTNCommand from "./SpendingPeriodBTNCommand";
import UsersInPeriodSpent from "./UsersInPeriodSpent";

const SpendingPeriodInProgress = () => {
  const navigate = useNavigate();
  const [periodSpent, setPeriodSpent] = useState(null);
  const [users, setUsers] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (sessionStorage.getItem("idUserConnected") === null) {
      console.log("User no connected");
      navigate("/");
    } else {
      const fetchData = async () => {
        try {
          console.log("Start to fetch data");
          const responsePeriodSpent =
            await PeriodSpentService.getPeriodSpentInProgress();
          const responseUsers =
            await UserService.getUsersInPeriodSpentInProgress();
          setPeriodSpent(responsePeriodSpent.data);
          setUsers(responseUsers.data);
          console.log("Data fetched successfully");
          setLoading(false);
        } catch (error) {
          console.log(error);
        }
      };
      fetchData();
    }
  }, [loading]);

  return (
    <div className="app-main-container">
      {loading && <div>...Loading</div>}
      {!loading && <DateSpendingPeriodAndMore periodSpent={periodSpent} />}
      {!loading && <UsersInPeriodSpent users={users} />}
      <SpendingPeriodBTNCommand />
    </div>
  );
};

export default SpendingPeriodInProgress;
