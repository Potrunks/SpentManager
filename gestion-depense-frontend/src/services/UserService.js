import axios from "axios";

const USER_API_BASE_URL = "http://localhost:8080/spentmanager/user";

class UserService {
  getUsersInPeriodSpentInProgress() {
    console.log("Start to fetch all users in period spent in progress");
    return axios.get(USER_API_BASE_URL + "/getusersbyperiodspentinprogress/");
  }

  getUsersInPeriodSpentByID(idPeriodSpent) {
    console.log("Start to fetch all users in period spent id " + idPeriodSpent);
    return axios.get(USER_API_BASE_URL + "/getUsersByPeriodSpent/" + idPeriodSpent);
  }

  getAllUsers() {
    console.log("Start to fetch all users in database");
    return axios.get(USER_API_BASE_URL + "/getAll/");
  }
}

export default new UserService();
