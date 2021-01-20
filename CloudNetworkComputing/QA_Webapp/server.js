const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const app = express();
const logger = require("./app/Utils/logger.js");


var corsOptions = {
  origin: "http://localhost:8081"
};
app.use(cors(corsOptions));

// parse requests of content-type: application/json
app.use(bodyParser.json());

// parse requests of content-type: application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({extended: true}));

const db = require("./app/models/db.js");
 db.sequelize.sync();
//To DROP and RE-SYNC fresh new DB
//db.sequelize.sync({ force: true }).then(() => {
//  console.log("Drop and re-sync db.");
//});

// simple route
app.get("/", (req, res) => {
  res.json({message: "Welcome to Gemi Assignement"});
});

require("./app/routes/user.routes.js")(app);

// set port, listen for requests
app.listen(8080, () => {
  logger.info("Server is running on port 8080.");
});
