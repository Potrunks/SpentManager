import axios from "axios";

const SPENT_API_BASE_URL = "http://localhost:8080/spentmanager/spent";

class SpentService {
  newSpent(spent) {
    return axios.post(SPENT_API_BASE_URL + "/new", spent);
  }

  getSpentsFromBackend() {
    console.log("Get all the spents from the data base")
    return axios.get(SPENT_API_BASE_URL + "/getall");
  }

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
