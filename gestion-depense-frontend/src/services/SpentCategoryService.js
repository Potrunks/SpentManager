import axios from "axios";

const SPENT_CATEGORY_API_BASE_URL =
  "http://localhost:8080/spentmanager/spentcategory";

class SpentCategoryService {
  getAllSpentCategories() {
    console.log("Send request to API to get all spent categories");
    return axios.get(SPENT_CATEGORY_API_BASE_URL + "/getall");
  }
}

export default new SpentCategoryService();
