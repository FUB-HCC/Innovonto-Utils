import axios from "axios";

export const requestIdeaPairs = (session, dispatch, errorDispatch) => {
  const  requestUrl = process.env.PUBLIC_URL + "/data/mockdata-rating-pairs.json";

  axios
    .get(requestUrl)
    .then(result => {
      dispatch(result.data);
    })
    .catch(error => {
      errorDispatch(error);
    });
};