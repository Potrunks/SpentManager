import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Menu = () => {
  const navigate = useNavigate();

  useEffect(() => {
    if (sessionStorage.getItem("idUserConnected") === null) {
      console.log("User no connected");
      navigate("/");
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
          <button onClick={() => navigate("/displayspendingperiodinprogress")}>
            Voir les dépenses
          </button>
          <button onClick={() => navigate("/newspent")}>Ajouter une dépense</button>
          <button>*Mes dépenses mensuelles*</button>
          <button onClick={() => navigate("/")}>Se deconnecter</button>
        </div>
      </div>
    </div>
  );
};

export default Menu;
