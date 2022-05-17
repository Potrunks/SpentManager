import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import SalaryService from "../../services/SalaryService";
import Loading from "../page/Loading";
import Confirm from "../Popup/Confirm";
import InputTestService from "../../services/InputTestService";

const ModifySalary = () => {
  const { idSalaryToModify } = useParams();
  const [salary, setSalary] = useState({
    idSalary: idSalaryToModify,
    valueSalary: "",
  });
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [confirmPopup, setConfirmPopup] = useState(false);
  const confirmMessage = "Etes-vous sur de vouloir modifier le salaire ?";

  const handleChange = (e) => {
    const value = e.target.value;
    setSalary({ ...salary, [e.target.name]: value });
  };

  const clearInput = (e) => {
    e.preventDefault();
    setSalary({
      valueSalary: "",
    });
  };

  const displayConfirmPopup = (e) => {
    if (
      InputTestService.verifyIntegrityNewSpendingPeriod(salary) === true &&
      confirmPopup === false
    ) {
      setConfirmPopup(true);
    }
  };

  const modifySalary = (e) => {
    setConfirmPopup(false);
    SalaryService.updateSalary(
      sessionStorage.getItem("idUserConnected"),
      salary
    ).then((response) => {
      if (response.data === true) {
        console.log("Salary id " + idSalaryToModify + " updated successfully");
        navigate("/success");
      } else {
        document.getElementById("API-error-box").firstChild.innerHTML =
          "Vous ne pouvez pas modifier un salaire qui n'est pas le votre !!!";
        document.getElementById("API-error-box").style.display = "flex";
      }
    });
  };

  useEffect(() => {
    if (sessionStorage.getItem("idUserConnected") === null) {
      console.log("User no connected");
      navigate("/");
    } else {
      const fetchData = async () => {
        setLoading(true);
        console.log("Start fetching salary id " + idSalaryToModify);
        try {
          const responseFetchSalary = await SalaryService.getSalaryByID(
            idSalaryToModify
          );
          setSalary(responseFetchSalary.data);
        } catch (error) {
          console.log(error);
        }
        setLoading(false);
        console.log("Salary id " + idSalaryToModify + " fetched succesfully");
      };
      fetchData();
    }
  }, []);

  return (
    <div className="app-main-container">
      {loading && <Loading />}
      {!loading && (
        <div className="main-form-container">
          <div className="main-title-form">
            <span>Modification salaire</span>
          </div>
          <div className="API-error-box" id="API-error-box">
            <span></span>
          </div>
          <div className="main-input-field" id="form">
            <div className="input-field">
              <span>Salaire (euros)</span>
              <input
                placeholder=""
                id="valueSalary"
                type="number"
                name="valueSalary"
                value={salary.valueSalary}
                onChange={(e) => handleChange(e)}
              ></input>
            </div>
          </div>
          <div className="main-button-container">
            <button onClick={(e) => displayConfirmPopup(e)}>Modifier</button>
            <button onClick={clearInput}>Effacer le champ</button>
            <button onClick={() => navigate("/menu")}>Annuler</button>
          </div>
        </div>
      )}
      {confirmPopup && (
        <Confirm
          parentSetConfirmPopup={setConfirmPopup}
          parentMethodToConfirm={modifySalary}
          parentConfirmMessage={confirmMessage}
        />
      )}
    </div>
  );
};

export default ModifySalary;
