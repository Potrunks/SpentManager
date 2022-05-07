import React from 'react';
import menuBurgerImage from './burger_menu.png';

const Navbar = () => {
    return (
        <div className="main-navbar-container">
            <div className='main-navbar-app-title'>
                <span id='main-app-title'>Spent Manager v1.0.0</span>
            </div>
            <img src={menuBurgerImage} alt='Menu'></img>
        </div>
    )
}

export default Navbar