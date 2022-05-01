import axios from "axios";

const USER_API_BASE_URL = "http://localhost:8080/spentmanager/user";

class UserService {
  postIdUserConnected(idUserConnected) {
    console.log("Post ID of the user connected to the API app");
    return axios.post(USER_API_BASE_URL + "/get/" + idUserConnected);
  }
}

export default new UserService();
