import axios from "axios";

const USER_API_BASE_URL = "http://localhost:8080/spentmanager/user";

class UserService {
  getUsersInPeriodSpentInProgress() {
    console.log("Start to fetch all users in period spent in progress");
    return axios.get(USER_API_BASE_URL + "/getusersbyperiodspentinprogress/");
  }
}

export default new UserService();
