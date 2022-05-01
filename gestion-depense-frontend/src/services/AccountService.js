import axios from "axios";

const ACCOUNT_API_BASE_URL = "http://localhost:8080/spentmanager/account";

class AccountService {
  postNewUser(user) {
    console.log("Post new user to the API app in order to create new account");
    return axios.post(ACCOUNT_API_BASE_URL + "/new", user);
  }

  postUserForLogIn(user) {
    console.log("Post user at API app to attempt to authenticate");
    return axios.post(ACCOUNT_API_BASE_URL + "/connect", user);
  }
}

export default new AccountService();
