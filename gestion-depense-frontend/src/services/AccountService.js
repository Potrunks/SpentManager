import axios from "axios";

const ACCOUNT_API_BASE_URL = "http://localhost:8080/spentmanager/account";

class AccountService {
    postNewUser(user) {
        return axios.post(ACCOUNT_API_BASE_URL + "/new", user);
    }
}

export default new AccountService();