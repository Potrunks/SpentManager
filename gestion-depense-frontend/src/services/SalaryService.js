import axios from "axios";

const SALARY_API_BASE_URL = "http://localhost:8080/spentmanager/salary";

class SalaryService {

    getSalaryByID(idSalary) {
        console.log("Send salary id " + idSalary + " request to the API");
        return axios.get(SALARY_API_BASE_URL + "/get/" + idSalary);
    }

    updateSalary(idUserConnected, salary) {
        console.log("Send salary id " + salary.idSalary + " request to the API");
        return axios.put(SALARY_API_BASE_URL + "/update/" + idUserConnected, salary);
    }
}

export default new SalaryService();