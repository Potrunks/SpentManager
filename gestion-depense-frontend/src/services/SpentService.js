import axios from "axios";

const SPENT_API_BASE_URL = "http://localhost:8080/spentmanager/spent";

class SpentService {
  newSpent(spent) {
    return axios.post(SPENT_API_BASE_URL + "/new", spent);
  }

  getSpentsPeriodInProgress() {
    console.log("Get all the spents during period spent in progress");
    return axios.get(SPENT_API_BASE_URL + "/getAllByPeriodSpentInProgress/");
  }

  getSpentsPeriodByID(idPeriodSpent) {
    console.log(
      "Get all the spents during period spent by id : " + idPeriodSpent
    );
    return axios.get(
      SPENT_API_BASE_URL + "/getAllByPeriodSpent/" + idPeriodSpent
    );
  }

  deleteExpenseById(idSpent) {
    console.log("Start to post the spent id " + idSpent + " to the API");
    return axios.delete(SPENT_API_BASE_URL + "/delete/" + idSpent);
  }

  getSpentByID(idSpent) {
    console.log(
      "Start to send the spent " + idSpent + " to the API for fetch data"
    );
    return axios.get(SPENT_API_BASE_URL + "/get/" + idSpent);
  }

  // a sup
  deleteSpent(id) {
    return axios.delete(SPENT_API_BASE_URL + "/delete/" + id);
  }

  getSpent(id) {
    return axios.get(SPENT_API_BASE_URL + "/get/" + id);
  }

  updateSpent(spent, id) {
    return axios.put(SPENT_API_BASE_URL + "/update/" + id, spent);
  }
}

export default new SpentService();
