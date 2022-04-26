import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import AddSpent from "./components/spent/AddSpent";
import DisplaySpents from "./components/spent/DisplaySpents";
import ModifySpent from "./components/spent/ModifySpent";
import Navbar from "./components/UI/Navbar";
import "./style/navbar.css";

function App() {
  return (
    <>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route index element={<DisplaySpents />} />
          <Route path="/" element={<DisplaySpents />} />
          <Route path="/displaySpents" element={<DisplaySpents />} />
          <Route path="/addSpent" element={<AddSpent />} />
          <Route path="/modifySpent/:id" element={<ModifySpent />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
