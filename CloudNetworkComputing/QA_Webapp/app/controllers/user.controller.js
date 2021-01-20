const db = require("../models/db");
const syncSQL = require("../models/db.sync.js");
const bcrypt = require('bcryptjs');
const utils = require("../Utils/utils.js");
const logger = require("../Utils/logger.js");
const User = db.user;
const Op = db.Sequelize.Op;
var SDC = require('statsd-client'),
    sdc = new SDC({host: 'localhost'});

const saltRounds = 1;
exports.create = (req, res) => {
  logger.info("Create User API");
  var timer = new Date();
  sdc.increment('createUser');
  if (!req.body) {
    logger.warn("Content cannot be empty");
    res.status(400).send({
      message: "Content cannot be empty!"
    });
  }

  if(process.env.LOCAL_DEVELOPMENT) 
  {
    console.log("This should come only when I set the environment flag");
  }

  if(req.body.first_name == null || req.body.last_name == null || req.body.username == null || req.body.password == null) {
    res.status(400).send({message: "Fill in ALL required fields"});
    return;
  }
  if(req.body.first_name === "" || req.body.last_name === "" || req.body.username === "" || req.body.password === "") {
    res.status(400).send({message: "Required Fields cannot be empty"});
    return;
  }  


  const emailcheckRE = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  if(!emailcheckRE.test(String(req.body.username).toLowerCase())) {
    res.status(400).send({message: "Incorrect email or password format"});
    return;
  }
  logger.info("Email verified");

  //Check Strong Password
  const passwordcheckRE = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
  if(!passwordcheckRE.test(String(req.body.password))) {
    logger.error("Incorrect password format");
    res.status(400).send({message: "Incorrect password format"});
    return;
  }
  logger.info("Password format verified");
  //User Object
  const user = {
    first_name: req.body.first_name,
    last_name: req.body.last_name,
    password: req.body.password,
    username: req.body.username,
  }
  //Before putting into DB
  hashPassword(user);
  //Creating User
  var timerDB = new Date();
  User.create(user)
    .then(data => {
      delete data.dataValues.password;
      res.send(data);
    })
    .catch(err => {
      logger.error("User NOT created. Error");
      res.status(400).send();
    });
    sdc.timing('createUser_DB', timerDB); 
    sdc.timing('createUser', timer); 
};
//Retrieve one User data by Email/Username
exports.findOne = (req, res) => {
  logger.info("FindOne User API");
  var timer = new Date();
  sdc.increment('findOneUser');
  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

  if(!utils.loginCheck(req, res))
  {
    res.status(400).send({ message: `Authentication Failed`});
    return;
  }
  var timerDB = new Date();
  User.findOne({where: {username:login}})
    .then(data => {
      delete data.dataValues.password;
      res.send(data);
    })
    .catch(err => {
      res.status(400).send();
    });
    sdc.timing('findOneUser_DB', timerDB);
    sdc.timing('findOneUser', timer); 
};

//Retrieve User -- NOT Authenticated
exports.getUserById = (req, res) => {
  logger.info("Find User by ID API");
  var timer = new Date();
  sdc.increment('GetUserByID');
  // const queryObject = url.parse(req.url,true).query;

  // User.findOne({where: {id:queryObject.id}})
  var timerDB = new Date();
  User.findOne({where: {id:req.params.id}})
    .then(data => {
      delete data.dataValues.password;
      res.send(data);
    })
    .catch(err => {
      res.status(400).send();
    });
    sdc.timing('GetUserByID_DB', timerDB);
    sdc.timing('GetUserByID', timer); 
};

//
exports.update = (req, res) => {
  logger.info("Update User API");
  var timer = new Date();
  sdc.increment('put: /v1/user/self');
  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

  if(!utils.loginCheck(req, res)) {
    res.status(400).send({ message: `Authentication Failed`});
    return;
  }
  //Body NOt empty
  if (!req.body) {
    logger.error("Content cannot be empty");
    res.status(400).send({
      message: "Content cannot be empty!"
    });
  }

  const user = {};
  if(req.body.first_name != null) {
    user.first_name = req.body.first_name;
  }
  if(req.body.last_name != null) {
    user.last_name = req.body.last_name;
  }
  if(req.body.password != null) {
    user.password = req.body.password;
    hashPassword(user);
  }

  //UPDATE User
  User.update(user,{where: {username:login}})
  .then(num => {
    if (num == 1) {
      logger.info("User updated successfully");
      res.status(204).send({
        message: "User was updated successfully."
      });
    } else {
      res.send({
        message: `Cannot update User with username=${login}`
      });
    }
  })
  .catch(err => {
    res.status(400).send({
      message: "Error updating User with username=" + login
    });
  });
  sdc.timing('put: /v1/user/self', timer); 
};

function hashPassword (user) {
  console.log("this is user password"+user.password);
  const hashedPassword = bcrypt.hashSync(user.password, saltRounds);
  console.log(hashedPassword);
  user.password = hashedPassword;
  return hashedPassword
}

