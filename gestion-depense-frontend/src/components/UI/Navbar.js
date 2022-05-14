import React, { useEffect, useState } from "react";
import menuBurgerImage from "./burger_menu.png";

const Navbar = () => {

  const [loading, setLoading] = useState(true);
  const [navbarText, setNavbarText] = useState();

  useEffect(() => {
    setLoading(true);
    if (sessionStorage.getItem("firstNameUserConnected") !== null) {
      setNavbarText("Bienvenue " + sessionStorage.getItem("firstNameUserConnected"));
    } else {
      setNavbarText("Spent Manager v1.0.0");
    }
    setLoading(false);
  }, []);

  return (
    <div className="main-navbar-container">
      <div className="main-navbar-app-title">
        <span id="main-app-title">{navbarText}</span>
      </div>
      <img src={menuBurgerImage} alt="Menu"></img>
    </div>
  );
};

export default Navbar;
