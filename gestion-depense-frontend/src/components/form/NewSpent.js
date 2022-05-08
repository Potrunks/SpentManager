import React, { useState } from "react";
import SpentCategoryService from "../../services/SpentCategoryService";

const NewSpent = () => {
  const [spent, setSpent] = useState({
    valueSpent: "",
    nameSpent: "",
    commentSpent: "",
    idUserConnected: sessionStorage.getItem("idUserConnedted"),
    idSpentCategorySelected: "",
  });

  const [spentCategory, setSpentCategory] = useState(null);

  useEffect(() => {
    const fetchSpentCategories = async () => {
      console.log("Loading all spent category");
      try {
        const response = await SpentCategoryService.fetchSpentCategories();
        setSpentCategory(response.data);
        console.log("Spent categories loaded");
      } catch (error) {
        console.log(error);
      }
    };
    fetchSpentCategories();
  });

  return <div>NewSpent</div>;
};

export default NewSpent;
