import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PeriodSpentService from "../../services/PeriodSpentService";

const Menu = () => {
  const navigate = useNavigate();
  const [periodSpentInProgressExist, setPeriodSpentInProgressExist] =
    useState(false);

  useEffect(() => {
    if (sessionStorage.getItem("idUserConnected") === null) {
      console.log("User no connected");
      navigate("/");
    } else {
      PeriodSpentService.checkPeriodSpentInProgressExist().then((response) => {
        setPeriodSpentInProgressExist(response.data);
      });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div className="app-main-container">
      <div className="main-form-container">
        <div className="main-title-form">
          <span>Menu</span>
        </div>
        <div className="main-button-container">
          <button onClick={() => navigate("/newSpendingPeriod")}>
            Nouveau salaire reçu
          </button>
          {periodSpentInProgressExist && (
            <div>
              <button
                onClick={() => navigate("/displayspendingperiodinprogress")}
              >
                Voir les dépenses
              </button>
              <button onClick={() => navigate("/newspent")}>
                Ajouter une dépense
              </button>
            </div>
          )}
          <button>*Mes dépenses mensuelles*</button>
          <button onClick={() => navigate("/")}>Se deconnecter</button>
        </div>
      </div>
    </div>
  );
};

export default Menu;
