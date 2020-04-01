import React from "react";
import { useStorageState } from "react-storage-hooks";

//TODO remove this
export const SubmitView = () => {
  const [mturkSession] = useStorageState(localStorage, 'innovonto-idea-similarity-app-session', null);

  return (
    <div className="App">
      <h1>Submit</h1>
      <p>{JSON.stringify(mturkSession)}</p>
    </div>
  )
};

export default SubmitView