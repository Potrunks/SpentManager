import React from 'react'
import { useNavigate } from 'react-router-dom'

const SpendingPeriodBTNCommand = () => {
    const navigate = useNavigate();
  return (
    <div className='spending-period-btn-main-container'>
        <button onClick={() => navigate("/newspent")}>New Expense</button>
        <button onClick={() => navigate("/newSpendingPeriod")}>Received Salary</button>
        <button onClick={() => navigate("/menu")}>Back</button>
    </div>
  )
}

export default SpendingPeriodBTNCommand