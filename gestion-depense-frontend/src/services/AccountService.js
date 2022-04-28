import axios from "axios";

const ACCOUNT_API_BASE_URL = "http://localhost:8080/spentmanager/account";

class AccountService {
  postNewUser(user) {
    console.log("Post new user to the API app in order to create new account");
    return axios.post(ACCOUNT_API_BASE_URL + "/new", user);
  }

  verifyIntegrityNewAccount(user) {
    if (
      (this.inputFieldNotEmpty(user) &&
        this.verifyBothPassword(user) &&
        this.verifyMailFormat(user) &&
        this.verifyNameFormat(user) &&
        this.verifyPasswordFormat(user)) === true
    ) {
      return true;
    }
    return false;
  }

  inputFieldNotEmpty(user) {
    if (
      (user.adminPassword.length &&
        user.firstNameUser.length &&
        user.lastNameUser.length &&
        user.mailUser.length &&
        user.passwordUser.length &&
        user.secondPasswordUser.length) === 0
    ) {
      console.log("Please, fill the required input");
      return false;
    } else {
      return true;
    }
  }

  verifyMailFormat(user) {
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(user.mailUser)) {
      return true;
    }
    console.log("Mail format is not good");
    return false;
  }

  verifyNameFormat(user) {
    const regex = /^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$/;
    if (regex.test(user.firstNameUser) && regex.test(user.lastNameUser)) {
      return true;
    }
    console.log("The first name and/or the last name is not correct");
    return false;
  }

  verifyBothPassword(user) {
    if (user.passwordUser === user.secondPasswordUser) {
      return true;
    }
    console.log("The 2 user passwords are not the same");
    return false;
  }

  verifyPasswordFormat(user) {
    const regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;
    if (regex.test(user.passwordUser) && regex.test(user.secondPasswordUser)) {
      return true;
    }
    console.log(
      "The format of the password is not correct : At least 8 characters with 1 digital and 1 uppercase"
    );
    return false;
  }
}

export default new AccountService();
