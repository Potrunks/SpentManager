import React from "react";
import loadingGIF from "./loading.gif";

const Loading = () => {
  return (
    <div className="loading-main-container">
      <img src={loadingGIF} alt="Loading"></img>
    </div>
  );
};

export default Loading;
