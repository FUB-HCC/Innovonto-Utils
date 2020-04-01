import React, { useEffect, useState } from "react";
import { useStorageState } from 'react-storage-hooks';
import { Form, Alert, Input, Button } from "antd";
import 'antd/dist/antd.css';
import { useLocation } from 'react-router-dom'
import labelingBlueprint from "../assets/labeling_blueprint.png"
import RatingList from "../components/RatingList";
import { requestIdeaPairs } from "../middleware/requests";


export const MturkStartTypes = Object.freeze({
    PREVIEW: "PREVIEW",
    START: "START",
    INVALID: "INVALID"
  }
);

const PreviewPageStartTypeNotification = (props) => {
  switch (props.startType) {
    case MturkStartTypes.PREVIEW:
      return <Alert message="
                This is the preview page! Please accept the HIT before working on the question!
                The Ideas here a just examples, you will get different ratings pairs once you accept the hit.
           " type="warning" showIcon/>;
    case MturkStartTypes.START:
      return <p></p>;
    default:
      return <Alert
        message="Something went wrong with loading the HIT data. Please try to reload your browser window. If the error persists, please contact us via the requester contact interface."
        type="error" showIcon/>;
  }
};

//TODO this button has to submit everything to the backend via axios. If the submit is successful the same data has to be submitted to mturk
const RatingSubmitButton = (props) => {
  switch (props.startType) {
    case MturkStartTypes.PREVIEW:
      return <Button type="primary" size="large" disabled>Accept to Submit</Button>;
    case MturkStartTypes.START:
      return <Button type="primary" size="large">Submit</Button>;
    default:
      return <Alert
        message="Something went wrong with loading the HIT data. Please try to reload your browser window. If the error persists, please contact us via the requester contact interface."
        type="error" showIcon/>;
  }
};

export const StartView = () => {
  const location = useLocation();
  const [error, setError] = useState(null);
  const [ideaPairs, setIdeaPairs] = useState(null);
  //TODO this always results in "restart": I think I haven't understood react hooks properly.
  const [mturkSession, setMturkSession] = useStorageState(localStorage, 'innovonto-idea-similarity-app-session', null);
  useEffect(() => requestIdeaPairs(mturkSession, setIdeaPairs, setError), [
    mturkSession
  ]);

  let query = new URLSearchParams(location.search);
  let hitId = query.get("hitId");
  let workerId = query.get("workerId");
  let assignmentId = query.get("assignmentId");
  let turkSubmitTo = query.get("turkSubmitTo");
  var currentMturkStartType = MturkStartTypes.INVALID;
  if (hitId && (assignmentId === "ASSIGNMENT_ID_NOT_AVAILABLE")) {
    currentMturkStartType = MturkStartTypes.PREVIEW;
  }
  if (hitId && workerId && assignmentId && turkSubmitTo) {
    if (mturkSession) {
      //TODO this is only a restart if the hitId and the assignmentId are same. Otherwise its a new hit with old data.
      //TODO how to handle multiple windows?
      if ((mturkSession.hasOwnProperty("hitId") && mturkSession.hitId === hitId) &&
        (mturkSession.hasOwnProperty("assignmentId") && mturkSession.assignmentId === assignmentId)) {
        currentMturkStartType = MturkStartTypes.START;
      } else {
        setMturkSession({
          hitId: hitId,
          workerId: workerId,
          assignmentId: assignmentId,
          turkSubmitTo: turkSubmitTo
        });
      }
    } else {
      setMturkSession({
        hitId: hitId,
        workerId: workerId,
        assignmentId: assignmentId,
        turkSubmitTo: turkSubmitTo
      });
    }
  }

  return (
    <div className="App">
      <h1>Idea Similarity Comparison</h1>
      <PreviewPageStartTypeNotification startType={currentMturkStartType}/>

      <p>In this task, you have to classify idea texts: Please rate how similar they are, on a scale from 0 to 5.
        You will have to rate <strong>20 pairs of ideas</strong>. You will receive <strong>$ 1</strong> for this task.
      </p>

      <h2>Tutorial</h2>
      <p>For each pair of ideas, you have to judge the similarity as a value between 0 (The two ideas are completely
        dissimilar)
        and 5 (The two ideas are completely equivalent, as they mean the same thing). In order to assess the
        similarity of an idea pair,
        please use the sentence similarity classification shown in the image below:</p>
      <div className="image-container text-center">
        <img alt="Similarity Classification Scheme Table" className="image img-thumbnail mx-auto"
             src={labelingBlueprint}/>
      </div>
      <p>Because we are comparing ideas, please consider all sentences in the idea, not only one. The image above
        should give you a feeling
        of similarity classifications.</p>

      <h2>Tips</h2>
      <ul>
        <li>Assign labels as precisely as possible according to the underlying meaning of the two ideas rather than
          their superficial similarities or differences
        </li>
        <li>Be careful of wording differences that have an important impact on what is being said or described</li>
        <li>Ignore grammatical errors and awkward wordings as long as they do not obscure what is being conveyed.
        </li>
      </ul>

      <h2>Task</h2>
      <PreviewPageStartTypeNotification startType={currentMturkStartType}/>
      <form id="rating-form" method="post" action={mturkSession.turkSubmitTo + "/mturk/externalSubmit"}>
        <input type="hidden" name="assignmentId" value={mturkSession.assignmentId}/>
        <input type="hidden" name="workerId" value={workerId}/>
        <input type="hidden" name="hitId" value={hitId}/>
        <RatingList startType={currentMturkStartType} session={mturkSession}/>

        <div>
          <h2>Feedback</h2>

          <p>We try to provide the best possible user experience, so it's always valuable to get feedback. If you have
            some advice or recommendations for us, please put it here:</p>
          <Form.Item label="Feedback">
            <Input.TextArea rows={4}/>
          </Form.Item>

          <RatingSubmitButton startType={currentMturkStartType}/>
        </div>
      </form>

    </div>
  )
};

export default StartView