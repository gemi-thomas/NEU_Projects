const db = require("../models/db");
const syncSQL = require("../models/db.sync.js");
const bcrypt = require('bcryptjs');
const utils = require("../Utils/utils.js");
const logger = require("../Utils/logger.js");
const Question = db.questions;
const Category = db.category;
const Answers = db.answers;
const QuestionImage = db.questionimage;
const AWS = require('aws-sdk');
const fs=require('fs');
var SDC = require('statsd-client'),
    sdc = new SDC({host: 'localhost'});


exports.createQuestion = async (req, res) => {
  logger.info("Create Question API");
  var timer = new Date();
  sdc.increment('createQuestion');
    if(!utils.loginCheck(req, res)) {
        logger.error("Authentication failed");
        res.status(400).send({ message: `Authentication Failed`});
        return;
    }
    const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
    const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

    var userid = syncSQL.query("SELECT id FROM Users WHERE username ='"+ login+ "'");

    const question = {
        question_text: req.body.question_text,
        UserId: userid[0].id
    }
   var timerDB = new Date();
   var q_result = await Question.create(question);
    if(req.body.categories != null) {
      var catg = req.body.categories;
      var i;
      for(i=0;i<catg.length;i++) {
        var strcatg = catg[i].category;
        var thiscatval = strcatg.toLowerCase();
  		  var vals = await Category.findOne( { where : {category:thiscatval}});


        if (vals === null)
        { logger.warn("category for question Not found");
          console.log("Not found "+  thiscatval);
          vals = await Category.create({category:thiscatval});
        }
        await q_result.addCategory(vals);
      }
    }
    var final_result = await Question.findAll({where : {question_id : q_result.dataValues.question_id}, 
    include:[ { model:Category, as: "categories", 
          attributes	 : ["category_id", "category"],
            through : { attributes : []}
          }]
        });
    res.send(final_result);
    sdc.timing('createQuestion_DB', timerDB);
    sdc.timing('createQuestion', timer); 
};

//Retrieve ALL questions -- NOT Authenticated
exports.getAllQuestions = async (req, res) => {
  var timer = new Date();
  sdc.increment('getAllQuestions');
  logger.info("Get All Questions API");
  var timerDB = new Date();
  var final_result = await Question.findAll({include:[ { model:Category, as: "categories", 
                                              attributes	 : ["category_id", "category"],
                                              through : { attributes : []}
                                            },{model : Answers, as: "answers", include: "attachments"},"attachments"]
                                          });
  sdc.timing('getAllQuestions_DB', timerDB);
  sdc.timing('getAllQuestions', timer);                                          
  res.send(final_result);
    
};

//Retrieve QUESTION by Id -- NOT Authenticated
exports.getQuestionById = async (req, res) => {
  logger.info("Get Question by ID API");
  var timer = new Date();
  sdc.increment('getQuestionById');
  var timerDB = new Date();
  var final_result = await Question.findOne({where : {question_id:req.params.question_id},
                                            include: [{ model:Category, as: "categories", 
                                            attributes	 : ["category_id", "category"],
                                            through : { attributes : []}
                                          },{model : Answers, as: "answers", include: "attachments"}, "attachments"]});
  sdc.timing('getQuestionById_DB', timerDB);
  if(final_result == null) {
    logger.warn("Question ID Not found");
    return res.status(404).send({ message: `Not found`});
  }

   var i;
   for (i = 0; i < final_result.dataValues.answers.length; i++)
   {
      final_result.dataValues.answers[i].dataValues.question_id =  final_result.dataValues.answers[i].questionQuestionId;
      delete final_result.dataValues.answers[i].dataValues.questionQuestionId;
   }
  res.send(final_result);
  sdc.timing('getQuestionById', timer);      
};

//Retrieve QUESTION by Id -- NOT Authenticated
exports.updateQuestionByQid = async (req, res) => {
  logger.info("Update Question by ID API");
  var timer = new Date();
  sdc.increment('updateQuestionByQid');
  if(!utils.loginCheck(req, res)) {
    res.status(400).send({ message: `Authentication Failed`});
    return;
  }
  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

  var loginUID = syncSQL.query("SELECT id FROM Users WHERE username ='"+ login+ "'");
  var result = await Question.findOne({where : {question_id:req.params.question_id}});

  if(result === null) {
    res.status(404).send({ message: `Not Found`});
    return;
  }
  console.log("Question found " + JSON.stringify(result));                                      
  if(result.UserId !== loginUID[0].id) {
    res.status(401).send({ message: `Unauthorized to updated questions that are not posted by you!`});
    return;
  }

   var updated_result = await Question.update({question_text: req.body.question_text}, {where : {question_id:req.params.question_id}});
   var updated_qobj = await Question.findOne({where : {question_id:req.params.question_id}});

  // //res.status(204).send();
  
  var deletCategoryData = syncSQL.query("Delete FROM question_category WHERE question_id ='"+ req.params.question_id+ "'");
  if(req.body.categories != null) {
    var catg = req.body.categories;
    var i;
    for(i=0;i<catg.length;i++) {
      var strcatg = catg[i].category;
      var thiscatval = strcatg.toLowerCase();
      var vals = await Category.findOne( { where : {category:thiscatval}});


      if (vals === null)
      {
        console.log("Not found "+  thiscatval);
        vals = await Category.create({category:thiscatval});
      }
      await updated_qobj.addCategory(vals);
    }
  }
  console.log(JSON.stringify(updated_qobj));
  var final_result = await Question.findAll({where : {question_id : updated_qobj.dataValues.question_id}, 
  include:[ { model:Category, as: "categories", 
        attributes	 : ["category_id", "category"],
          through : { attributes : []}
        }]
      });
  res.send(final_result);
  sdc.timing('updateQuestionByQid', timer);   
};

//Retrieve QUESTION by Id -- NOT Authenticated
exports.deleteQuestion = async (req, res) => {
  logger.info("Delete Question API");
  var timer = new Date();
  sdc.increment('deleteQuestion');
  if(!utils.loginCheck(req, res)) {
    res.status(400).send({ message: `Authentication Failed`});
    return;
  }
  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

  var loginUID = syncSQL.query("SELECT id FROM Users WHERE username ='"+ login+ "'");
  var result = await Question.findOne({where : {question_id:req.params.question_id},
                                             include: "answers"});
  if(result === null) {
    res.status(404).send({ message: `Not Found`});
    return;
  }
  console.log("Question found " + JSON.stringify(result));                                      
  if(result.UserId !== loginUID[0].id) {
    res.status(401).send({ message: `Unauthorized to delete questions that are not posted by you!`});
    return;
  }
  var timerDB = new Date();
  if(result.answers === null || result.answers.length === 0) {
    await deleteS3Objects(req.params.question_id);
    await Question.destroy({where : {question_id:req.params.question_id}});
    return res.status(204).send();
  }
  sdc.timing('deleteQuestion_DB', timerDB);
  sdc.timing('deleteQuestion', timer);  
  return res.status(401).send({ message: `Unauthorized delete. Question can be deleted only if there are no answers to this question`});
};

//Upload an image to question
exports.uploadFile = async (source,targetName, req, res) => { 
  logger.info("Upload Question File API");
  var timer = new Date();
  sdc.increment('uploadFileQuestion');
  if(!utils.loginCheck(req, res)) {
    res.status(400).send({ message: `Authentication Failed`});
    fs.unlinkSync(source);
    return;
  }
  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

  var userid = syncSQL.query("SELECT id FROM Users WHERE username ='"+ login+ "'");

  var questionObj = await Question.findOne({where : {question_id:req.params.question_id}});
  console.log("----------------------------------------------");
  console.log(questionObj);
  console.log(userid);
  if(questionObj === null || userid[0].id !== questionObj.UserId) {
    res.status(400).send();
    fs.unlinkSync(source);
    return;
  }

  console.log("req.file.path "+ source);
  console.log("req.file.filename "+ targetName);
  if(process.env.LOCAL_DEVELOPMENT) 
  {
    AWS.config.update({
      accessKeyId: process.env.DEV_ACCESS_KEY,
      secretAccessKey: process.env.DEV_SECRET_KEY,
      region: 'us-east-1',
    });
  }

  //Creating a new instance of S3:
  const s3= new AWS.S3();
  var storagePlace = req.params.question_id+"/"+targetName;
  //var storagePlace = targetName;
  var timerS3 = new Date();
  console.log('preparing to upload...');
  fs.readFile(source, function (err, filedata) {
    if (!err) {
      const putParams = {
          Bucket      : process.env.S3_BUCKETNAME,
          Key         : storagePlace,
          Body        : filedata
      };
      s3.putObject(putParams, function(err, data){
        if (err) {
          console.log('Could nor upload the file. Error :',err);
          res.status(400).send();
          return;
        } 
        else{
          fs.unlinkSync(source);// Deleting the file from uploads folder(Optional).Do Whatever you prefer.
          console.log('Successfully uploaded the file at '+ JSON.stringify(data));
        }
      });
    }
    else{
      console.log({'err':err});
      res.send({success:false});
      return;
    }
  });
  sdc.timing('uploadFileQuestion_S3', timerS3); 
  const questionimage = {
    // etag_text: imageData.ETag,
    target_path: storagePlace,
    questionQuestionId: req.params.question_id
  }

  QuestionImage.create(questionimage)
  .then(data => {
    res.status(201).send(data);
  })
  .catch(err => {
    res.status(400).send({
      message:
        err.message || "Some error occurred while inserting question data into DB"
    });
  });
  sdc.timing('uploadFileQuestion', timer);  
};


exports.deleteFile = async (req, res) => { 
  logger.info("Delete Question File API");
  var timer = new Date();
  sdc.increment('deleteFileQustion');
  if(!utils.loginCheck(req, res)) {
    res.status(400).send({ message: `Authentication Failed`});
    return;
  }

  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

  var userid = syncSQL.query("SELECT id FROM Users WHERE username ='"+ login+ "'");

  var questionObj = await Question.findOne({where : {question_id:req.params.question_id}});
  if(questionObj === null || userid[0].id !== questionObj.UserId) {
    res.status(400).send();
    return;
  }

  //var result = await QuestionImage.findOne({where : {questionQuestionId:req.params.question_id, file_id:req.params.file_id}});
  var imageToDelete = await QuestionImage.findOne({where : {file_id:req.params.file_id}});
  if(imageToDelete === null) {
    res.status(404).send();
    return;
  }

  if(process.env.LOCAL_DEVELOPMENT) 
  {
    AWS.config.update({
      accessKeyId: process.env.DEV_ACCESS_KEY,
      secretAccessKey: process.env.DEV_SECRET_KEY,
      region: 'us-east-1',
    });
  }

  //Creating a new instance of S3:
  var timerS3 = new Date();
  const s3= new AWS.S3();

  console.log('preparing to delete...');
  try{
      const params = {
          Bucket      : process.env.S3_BUCKETNAME,
          Key         : imageToDelete.target_path
      };
      s3.deleteObject(params, function(err, data){
        if (err) {
          // console.log('Could nor delete the file from S3 :',err);
          res.status(404).send();
          return;
        } 
      });
    }
    catch (error) {
      console.log({'err':err});
      res.status(404).send();
      return;
    }
    sdc.timing('deleteFileQuestion_S3', timerS3);   
    var result = await QuestionImage.destroy({where : {file_id:req.params.file_id}});
    res.status(204).send({message: 'Deleted successfully!'});
     sdc.timing('deleteFileQuestion', timer);   
};

async function deleteS3Objects(questionID) {
  var result = await QuestionImage.findAll({where : {questionQuestionId:questionID}});
  if(result == null) {
    return;
  }
  if(process.env.LOCAL_DEVELOPMENT) 
  {
    AWS.config.update({
      accessKeyId: process.env.DEV_ACCESS_KEY,
      secretAccessKey: process.env.DEV_SECRET_KEY,
      region: 'us-east-1',
    });
  }

  //Creating a new instance of S3:
  const s3= new AWS.S3();
  console.log("Output " + JSON.stringify(result));

  for(var i=0;i<result.length;i++)
  {
    console.log(result[i].target_path);
    const params = {
      Bucket      : process.env.S3_BUCKETNAME,
      Key         : result[i].target_path
    };
    s3.deleteObject(params, function(err, data){
      if (err) {
         console.log('Could nor delete the file from S3 :',err);
        } 
    });
  }
 
}