import axios from "axios";

const SPENT_CATEGORY_BASE_API_URL =
  "http://localhost:8080/spentmanager/spentcategory";

class SpentCategoryService {
  fetchSpentCategories() {
    console.log("Send request to the API to fetch all spent categories");
    return axios.get(SPENT_CATEGORY_BASE_API_URL + "/getall");
  }
}

export default new SpentCategoryService();
