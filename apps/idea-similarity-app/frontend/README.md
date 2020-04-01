# Idea Similarity App Frontend

    yarn install
    
## Running

In the project directory, you can run:

    yarn start
    
Runs the app in the development mode.<br />
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.


## Building a Production build

    yarn build
    
Builds the app for production to the `build` folder.<br />
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br />
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

## Rendering an mturk external-hit question

    Preview:
    http://localhost:3000/?assignmentId=ASSIGNMENT_ID_NOT_AVAILABLE&hitId=3XYBQ44Z6P472XXYNEGTT0021N3WT5
    
    Start:
    http://localhost:3000/?assignmentId=3JRJSWSMQHLB90A7D1LYD1KK4MU3EW&hitId=3XYBQ44Z6P472XXYNEGTT0021N3WT5&workerId=A1053TFZ0N36YJ&turkSubmitTo=https%3A%2F%2Fworkersandbox.mturk.com
    
    Restart:
    http://localhost:3000/?assignmentId=3JRJSWSMQHLB90A7D1LYD1KK4MU3EW&hitId=3XYBQ44Z6P472XXYNEGTT0021N3WT5&workerId=A1053TFZ0N36YJ&turkSubmitTo=https%3A%2F%2Fworkersandbox.mturk.com
    
    Returned the HIT:
    http://localhost:3000/?assignmentId=ASSIGNMENT_ID_NOT_AVAILABLE&hitId=3E22YV8GG16JNBVDEWGMS7MEJ8EPN2
    
    Local-Testing:
    http://localhost:3000/?assignmentId=a&hitId=h&workerId=w&turkSubmitTo=http%3A%2F%2Flocalhost:8080%2Fapi%2F