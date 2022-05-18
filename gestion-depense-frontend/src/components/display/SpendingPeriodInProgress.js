import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PeriodSpentService from "../../services/PeriodSpentService";
import SpentService from "../../services/SpentService";
import UserService from "../../services/UserService";
import Loading from "../page/Loading";
import DateSpendingPeriodAndMore from "./DateSpendingPeriodAndMore";
import SpendingPeriodBTNCommand from "./SpendingPeriodBTNCommand";
import Spents from "./Spents";
import UsersInPeriodSpent from "./UsersInPeriodSpent";

const SpendingPeriodInProgress = () => {
  const navigate = useNavigate();
  const [periodSpent, setPeriodSpent] = useState(null);
  const [users, setUsers] = useState(null);
  const [spents, setSpents] = useState(null);
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
          const responseSpents = await SpentService.getSpentsPeriodInProgress();
          setPeriodSpent(responsePeriodSpent.data);
          setUsers(responseUsers.data);
          setSpents(responseSpents.data);
          setLoading(false);
        } catch (error) {
          console.log(error);
        }
      };
      fetchData();
    }
  }, []);

  return (
    <div className="app-main-container">
      {loading && <Loading />}
      {!loading && <DateSpendingPeriodAndMore periodSpent={periodSpent} />}
      {!loading && (
        <UsersInPeriodSpent users={users} periodSpent={periodSpent} />
      )}
      {!loading && <SpendingPeriodBTNCommand periodSpent={periodSpent} />}
      {!loading && <Spents spents={spents} periodSpent={periodSpent} />}
    </div>
  );
};

export default SpendingPeriodInProgress;
