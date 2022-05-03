import React from "react";
import { useParams } from "react-router-dom";

const Menu = () => {
  const { idUserConnected } = useParams();
  return (
    <div className="app-main-container">
      <div className="main-form-container">
        <div className="main-title-form">
          <span>Menu</span>
        </div>
        <div className="main-button-container">
          <button>New spending period</button>
        </div>
      </div>
    </div>
  );
};

export default Menu;
