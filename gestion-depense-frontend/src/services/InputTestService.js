const regexMailFormat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
const regexNameFormat = /^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$/;
const regexPasswordFormat =
  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;
const regexFullWhiteSpace = /.*\S.*/;
class InputTestService {
  resetAllError() {
    document.getElementById("adminPassword").classList.remove("error");
    document.getElementById("firstNameUser").classList.remove("error");
    document.getElementById("lastNameUser").classList.remove("error");
    document.getElementById("mailUser").classList.remove("error");
    document.getElementById("passwordUser").classList.remove("error");
    document.getElementById("secondPasswordUser").classList.remove("error");
    document.getElementById("adminPassword").placeholder = "";
    document.getElementById("firstNameUser").placeholder = "";
    document.getElementById("lastNameUser").placeholder = "";
    document.getElementById("mailUser").placeholder = "";
    document.getElementById("passwordUser").placeholder = "";
    document.getElementById("secondPasswordUser").placeholder = "";
    document.getElementById("API-error-box").style.display = "none";
  }

  resetMailAndPasswordError() {
    document.getElementById("mailUser").classList.remove("error");
    document.getElementById("passwordUser").classList.remove("error");
    document.getElementById("mailUser").placeholder = "";
    document.getElementById("passwordUser").placeholder = "";
  }

  resetNewSpendingPeriodFormError() {
    document.getElementById("valueSalary").classList.remove("error");
    document.getElementById("valueSalary").placeholder = "";
    document.getElementById("API-error-box").style.display = "none";
  }

  resetNewSpentFormError() {
    document.getElementById("commentSpent").classList.remove("error");
    document.getElementById("commentSpent").placeholder = "";
    document.getElementById("valueSpent").classList.remove("error");
    document.getElementById("valueSpent").placeholder = "";
    document.getElementById("nameSpent").classList.remove("error");
    document.getElementById("nameSpent").placeholder = "";
    document
      .getElementById("idSpentCategorySelected")
      .classList.remove("error");
    document.getElementById("idSpentCategorySelected").placeholder = "";
    document.getElementById("API-error-box").style.display = "none";
  }

  verifyIntegrityNewSpent(spent) {
    this.resetNewSpentFormError();
    if (
      this.verifyIntegrityValueSpent(spent.valueSpent) === false ||
      this.verifyIntegrityNameSpent(spent.nameSpent) === false ||
      this.verifyIntegritySpentCategory(spent.idSpentCategorySelected) ===
        false ||
      this.verifyIntegrityCommentSpent(spent.commentSpent) === false
    ) {
      return false;
    }
    return true;
  }

  verifyIntegritySpentCategory(idSpentCategorySelected) {
    if (this.idSpentCategoryIsSelected(idSpentCategorySelected) === true) {
      return true;
    }
    return false;
  }

  idSpentCategoryIsSelected(idSpentCategorySelected) {
    if (idSpentCategorySelected === "") {
      document.getElementById("idSpentCategorySelected").classList.add("error");
      document.getElementById("idSpentCategorySelected").placeholder =
        "Input required";
      return false;
    }
    return true;
  }

  verifyIntegrityCommentSpent(commentSpent) {
    if (this.commentSpentIsOnlySpace(commentSpent) === true) {
      return false;
    }
    return true;
  }

  commentSpentIsOnlySpace(commentSpent) {
    if (!regexFullWhiteSpace.test(commentSpent) && commentSpent !== "") {
      document.getElementById("commentSpent").value = "";
      document.getElementById("commentSpent").classList.add("error");
      document.getElementById("commentSpent").placeholder =
        "Comment format invalid";
      return true;
    }
    return false;
  }

  verifyIntegrityNameSpent(name) {
    if (
      (this.nameSpentIsEmpty(name) || this.nameSpentIsOnlySpace(name)) === true
    ) {
      return false;
    }
    return true;
  }

  nameSpentIsEmpty(name) {
    if (name.length === 0) {
      document.getElementById("nameSpent").classList.add("error");
      document.getElementById("nameSpent").placeholder = "Input required";
      return true;
    }
    return false;
  }

  nameSpentIsOnlySpace(name) {
    if (!regexFullWhiteSpace.test(name)) {
      document.getElementById("nameSpent").value = "";
      document.getElementById("nameSpent").classList.add("error");
      document.getElementById("nameSpent").placeholder = "Name format invalid";
      return true;
    }
    return false;
  }

  verifyIntegrityValueSpent(value) {
    if (
      (this.valueSpentIsEmpty(value) || this.valueSpentIsZero(value)) === true
    ) {
      return false;
    }
    return true;
  }

  valueSpentIsEmpty(value) {
    if (value.length === 0) {
      document.getElementById("valueSpent").classList.add("error");
      document.getElementById("valueSpent").placeholder = "Input required";
      return true;
    }
    return false;
  }

  valueSpentIsZero(value) {
    if (value === "0") {
      document.getElementById("valueSpent").value = "";
      document.getElementById("valueSpent").classList.add("error");
      document.getElementById("valueSpent").placeholder = "Value cannot be 0";
      return true;
    }
    return false;
  }

  verifyIntegrityNewAccount(user) {
    this.resetAllError();
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

  verifyIntegrityLogin(user) {
    this.resetMailAndPasswordError();
    if (this.inputFieldNotEmptyForLogin(user) === true) {
      return true;
    }
    return false;
  }

  verifyIntegrityNewSpendingPeriod(salary) {
    this.resetNewSpendingPeriodFormError();
    if (this.inputFieldNotEmptyForNewSpendingPeriod(salary) === true) {
      return true;
    } else {
      return false;
    }
  }

  inputFieldNotEmptyForNewSpendingPeriod(salary) {
    if (salary.valueSalary.length === 0 || salary.valueSalary === "0") {
      if (salary.valueSalary.length === 0) {
        document.getElementById("valueSalary").classList.add("error");
        document.getElementById("valueSalary").placeholder = "Input required";
      }
      if (salary.valueSalary === "0") {
        document.getElementById("valueSalary").value = "";
        document.getElementById("valueSalary").classList.add("error");
        document.getElementById("valueSalary").placeholder =
          "Value cannot be 0";
      }
      return false;
    } else {
      return true;
    }
  }

  inputFieldNotEmptyForLogin(user) {
    if ((user.mailUser.length && user.passwordUser.length) === 0) {
      if (user.mailUser.length === 0) {
        document.getElementById("mailUser").classList.add("error");
        document.getElementById("mailUser").placeholder = "Input required";
      }
      if (user.passwordUser.length === 0) {
        document.getElementById("passwordUser").classList.add("error");
        document.getElementById("passwordUser").placeholder = "Input required";
      }
      return false;
    } else {
      return true;
    }
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
      if (user.adminPassword.length === 0) {
        document.getElementById("adminPassword").classList.add("error");
        document.getElementById("adminPassword").placeholder = "Input required";
      }
      if (user.firstNameUser.length === 0) {
        document.getElementById("firstNameUser").classList.add("error");
        document.getElementById("firstNameUser").placeholder = "Input required";
      }
      if (user.lastNameUser.length === 0) {
        document.getElementById("lastNameUser").classList.add("error");
        document.getElementById("lastNameUser").placeholder = "Input required";
      }
      if (user.mailUser.length === 0) {
        document.getElementById("mailUser").classList.add("error");
        document.getElementById("mailUser").placeholder = "Input required";
      }
      if (user.passwordUser.length === 0) {
        document.getElementById("passwordUser").classList.add("error");
        document.getElementById("passwordUser").placeholder = "Input required";
      }
      if (user.secondPasswordUser.length === 0) {
        document.getElementById("secondPasswordUser").classList.add("error");
        document.getElementById("secondPasswordUser").placeholder =
          "Input required";
      }
      return false;
    } else {
      return true;
    }
  }

  verifyMailFormat(user) {
    if (regexMailFormat.test(user.mailUser)) {
      return true;
    }
    document.getElementById("mailUser").value = "";
    document.getElementById("mailUser").classList.add("error");
    document.getElementById("mailUser").placeholder = "Mail invalid";
    return false;
  }

  verifyNameFormat(user) {
    if (
      regexNameFormat.test(user.firstNameUser) &&
      regexNameFormat.test(user.lastNameUser)
    ) {
      return true;
    }
    if (regexNameFormat.test(user.firstNameUser) === false) {
      document.getElementById("firstNameUser").value = "";
      document.getElementById("firstNameUser").classList.add("error");
      document.getElementById("firstNameUser").placeholder = "Invalid format";
    }
    if (regexNameFormat.test(user.lastNameUser) === false) {
      document.getElementById("lastNameUser").value = "";
      document.getElementById("lastNameUser").classList.add("error");
      document.getElementById("lastNameUser").placeholder = "Invalid format";
    }
    return false;
  }

  verifyBothPassword(user) {
    if (user.passwordUser === user.secondPasswordUser) {
      return true;
    }
    document.getElementById("secondPasswordUser").value = "";
    document.getElementById("secondPasswordUser").classList.add("error");
    document.getElementById("secondPasswordUser").placeholder =
      "Not equal to password";
    return false;
  }

  verifyPasswordFormat(user) {
    if (
      regexPasswordFormat.test(user.passwordUser) &&
      regexPasswordFormat.test(user.secondPasswordUser)
    ) {
      return true;
    }
    if (regexPasswordFormat.test(user.passwordUser) === false) {
      document.getElementById("passwordUser").value = "";
      document.getElementById("passwordUser").classList.add("error");
      document.getElementById("passwordUser").placeholder =
        "8 char max with 1 digit and 1 uppercase";
    }
    if (regexPasswordFormat.test(user.secondPasswordUser) === false) {
      document.getElementById("secondPasswordUser").value = "";
      document.getElementById("secondPasswordUser").classList.add("error");
      document.getElementById("secondPasswordUser").placeholder =
        "8 char max with 1 digit and 1 uppercase";
    }
    return false;
  }
}

export default new InputTestService();
