import React from "react";
import { Card, Col, Row, Radio } from "antd";

export const RatingElement = (props) => {
  const { index, id, leftIdea, rightIdea } = props;
  //TODO get mturkStartType from Props
  //TODO if startType == START, get pairs from backend (for the current session)
  //TODO show loading indicator
  //TODO get things from props
  //TODO for Rating we need: onChange and value
  return <div>
    <h3>Idea Pair {index}</h3>
    <Row gutter={32}>
      <Col className="gutter-row" span={12}><Card><p>{leftIdea.content}</p></Card></Col>
      <Col className="gutter-row" span={12}><Card><p>{rightIdea.content}</p></Card></Col>
    </Row>
    <h3>Rating:</h3>
    <Radio.Group>
      <Radio value={0}>Completely Dissimilar</Radio>
      <Radio value={1}>Share some details</Radio>
      <Radio value={2}>Share multiple details</Radio>
      <Radio value={3}>Roughly Equivalent</Radio>
      <Radio value={4}>Mostly Equivalent</Radio>
      <Radio value={5}>Completely Equivalent</Radio>
    </Radio.Group>
  </div>
};

export default RatingElement
