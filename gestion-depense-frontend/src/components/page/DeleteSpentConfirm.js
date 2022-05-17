import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import SpentService from "../../services/SpentService";

const DeleteSpentConfirm = () => {
  const { idSpentToDelete } = useParams();
  const [spent, setSpent] = useState();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);

  const deleteExpenseByID = (e, idSpent) => {
    console.log("Start to delete the expense by id :" + idSpent);
    e.preventDefault();
    SpentService.deleteExpenseById(idSpent).then((response) => {
      console.log("Spent id " + idSpent + " deleted successfully");
      navigate("/success");
    });
  };

  useEffect(() => {
    if (sessionStorage.getItem("idUserConnected") === null) {
      console.log("User no connected");
      navigate("/");
    } else {
      const fetchData = async () => {
        try {
          console.log("Start to fetch spent id " + idSpentToDelete);
          console.log(idSpentToDelete);
          const responseFetchSpent = await SpentService.getSpentByID(
            idSpentToDelete
          );
          setSpent(responseFetchSpent.data);
          console.log("Spent id " + idSpentToDelete + " fetched successfully");
          setLoading(false);
        } catch (error) {
          console.log(error);
        }
      };
      fetchData();
    }
  }, []);

  return (
    <div className="app-main-container">
      {!loading && (
        <div className="spents-main-container">
          <div className="main-title-display">
            <span>Etes-vous sur ?</span>
          </div>
          <div key={spent.idSpent} className="spents-card-container">
            <div className="spent-card">
              <div className="spent-card-title">
                <span>{spent.nameSpent}</span>
              </div>
              <div className="spent-card-info">
                <span>Fait le {spent.dateSpent}</span>
                <span>Prix : {spent.valueSpent} euros</span>
                <span>Par {spent.nameUserWhoCreate}</span>
                <span>Catégorie : {spent.nameSpentCategory}</span>
                {spent.commentSpent !== "" && (
                  <span className="spent-comment">
                    Commentaire : {spent.commentSpent}
                  </span>
                )}
              </div>
              <div className="spent-card-btn-command">
                <button
                  onClick={(e, idSpent) =>
                    deleteExpenseByID(e, idSpentToDelete)
                  }
                >
                  Supprimer
                </button>
                <button onClick={() => navigate("/menu")}>Annuler</button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default DeleteSpentConfirm;
