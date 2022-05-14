import React from "react";
import { useNavigate } from "react-router-dom";
import successImage from "./success.png";

const Success = () => {
  const navigate = useNavigate();

  return (
    <div className="app-main-container">
      <div className="page-container">
        <div className="title-container">
          <span>Success</span>
        </div>
        <div className="text-container">
          <span>Votre action a bien été enregistré</span>
        </div>
        <div className="img-container">
          <img src={successImage} alt="Success"></img>
        </div>
        <div className="main-button-container">
          <button onClick={() => navigate("/menu")}>OK</button>
        </div>
      </div>
    </div>
  );
};

export default Success;
