import React from 'react'
import { useNavigate } from 'react-router-dom'

const SpendingPeriodBTNCommand = () => {
    const navigate = useNavigate();
  return (
    <div className='spending-period-btn-main-container'>
        <button onClick={() => navigate("/newspent")}>Nouvelle dépense</button>
        <button onClick={() => navigate("/newSpendingPeriod")}>Salaire reçu</button>
        <button onClick={() => navigate("/menu")}>Retour</button>
    </div>
  )
}

export default SpendingPeriodBTNCommand