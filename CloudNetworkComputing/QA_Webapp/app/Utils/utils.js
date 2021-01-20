const bcrypt = require('bcryptjs');
const syncSQL = require("../models/db.sync.js");
const logger = require("../Utils/logger.js");

module.exports = {
loginCheck: function (req, res) {

    const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
    const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')
    // console.log("Crypted file "+ b64auth);
    // console.log("Username: "+ login + " Password: " + password);
    
    var passwordDB = syncSQL.query("SELECT password FROM Users WHERE username ='"+ login+ "'");
    if(passwordDB == null || passwordDB[0] == null) return false;
    // console.log(passwordDB[0].password);

    if(decryptPassword(password, passwordDB[0].password)) {
      if(req.body.username) {
        if(req.body.username === login) {
          // console.log("User Aunthentication completed Successfully");
          return true;
        }
        else {
          // console.log("User Aunthentication failed");
          return false;
        }
      }
      // console.log("User Aunthentication completed Successfully");
      return true;
    }
    else {
      // console.log("User Aunthentication failed here");
      return false;
    }  
  }

};

function decryptPassword(password, pswdDB) {
    console.log(password +" " + pswdDB);
  
    var result = bcrypt.compareSync(password, pswdDB);
    if (result) {
        console.log("Password correct");
        return true;
    } else {
        logger.warn("Password in-correct");
        console.log("Password wrong");
        return false;
    }
}
