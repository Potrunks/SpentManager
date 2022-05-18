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

  getPeriodSpentInProgress() {
    console.log("Send get request to the API for the period spent in progress");
    return axios.get(PERIOD_SPENT_API_BASE_URL + "/getInProgress/");
  }

  getPeriodSpentByID(idPeriodSpent) {
    console.log(
      "Send get request to the API for the period spent id : " + idPeriodSpent
    );
    return axios.get(PERIOD_SPENT_API_BASE_URL + "/get/" + idPeriodSpent);
  }

  checkPeriodSpentInProgressExist() {
    console.log("Send request to check if period spent in progress exist");
    return axios.get(PERIOD_SPENT_API_BASE_URL + "/getInProgress/exist");
  }
}

export default new PeriodSpentService();
