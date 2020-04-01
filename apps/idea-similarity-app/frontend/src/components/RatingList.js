import React from "react";
import RatingElement from "./RatingElement";
import { Space } from "antd";

export const RatingList = (props) => {
  //TODO get mturkStartType from Props
  //TODO if startType == START, get pairs from backend (for the current session)
  //TODO show loading indicator
  const mockData = [
    {
      id: "617af92e-604d-45ee-9a3f-33e9bbb8e53a",
      leftIdea: {
        id: "a49e929a-9879-4780-b5cb-fa81a33afcd2",
        content: "The technology can be used to investigate how many active wild animals there are in the field."
      },
      rightIdea: {
        id: "4062c61c-741b-4e87-9eba-38a5c8559708",
        content: "it could monitor children or animals when not able to watch them directly and to watch for any actions that aren't familiar with the system."
      }
    }
  ];
  const ratingElements = mockData.map((ratingPair, index) =>
    <RatingElement
      index={index}
      id={ratingPair.id}
      leftIdea={ratingPair.leftIdea}
      rightIdea={ratingPair.rightIdea}
    />);
  return <div>
    <Space direction="vertical">
      {ratingElements}
    </Space>
  </div>
};

export default RatingList

