import axios from "axios";

const PERIOD_SPENT_API_BASE_URL =
  "http://localhost:8080/spentmanager/periodspent";

class PeriodSpentService {
  postNewPeriodSpent(idUserConnected, salary) {
    console.log("Post new salary to the API app");
    return axios.post(
      PERIOD_SPENT_API_BASE_URL + "/new/" + idUserConnected,
      salary
    );
  }
}

export default new PeriodSpentService();
