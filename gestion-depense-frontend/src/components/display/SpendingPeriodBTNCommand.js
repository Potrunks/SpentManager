import React from 'react'
import { useNavigate } from 'react-router-dom'

const SpendingPeriodBTNCommand = () => {
    const navigate = useNavigate();
  return (
    <div className='spending-period-btn-main-container'>
        <button onClick={() => navigate("/newspent")}>Add spent</button>
        <button>Add user</button>
        <button onClick={() => navigate("/menu")}>Back</button>
    </div>
  )
}

export default SpendingPeriodBTNCommand