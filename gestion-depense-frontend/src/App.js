import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import CreateAccount from "./components/form/CreateAccount";
import AddSpent from "./components/spent/AddSpent";
import DisplaySpents from "./components/spent/DisplaySpents";
import ModifySpent from "./components/spent/ModifySpent";
import Navbar from "./components/UI/Navbar";
import "./style/navbar.css";
import "./style/form.css";
import LoginAccount from "./components/form/LoginAccount";

function App() {
  return (
    <>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route index element={<LoginAccount />} />
          <Route path="/" element={<LoginAccount />} />
          <Route
            path="/displaySpents/:idUserConnected"
            element={<DisplaySpents />}
          />
          <Route path="/addSpent" element={<AddSpent />} />
          <Route path="/modifySpent/:id" element={<ModifySpent />} />
          <Route path="/createAccount" element={<CreateAccount />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
