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
            New Spending Period
          </button>
          <button>See Spents</button>
          <button onClick={() => navigate("/newspent")}>New Spent</button>
          <button>Configure Monthly Spent</button>
          <button onClick={() => navigate("/")}>Log Out</button>
        </div>
      </div>
    </div>
  );
};

export default Menu;
