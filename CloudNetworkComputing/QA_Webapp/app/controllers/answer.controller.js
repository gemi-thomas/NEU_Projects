const db = require("../models/db");
const syncSQL = require("../models/db.sync.js");
const utils = require("../Utils/utils.js");
const Answer = db.answers;
const Question = db.questions;
const User = db.user;
const AnswerImage = db.answerimage;
const AWS = require('aws-sdk');
const fs=require('fs');
const logger = require("../Utils/logger.js");
var SDC = require('statsd-client'),
    sdc = new SDC({host: 'localhost'});

exports.createAnswer = async (req, res) => {
  logger.info("Create Answer API");
  var timer = new Date();
  sdc.increment('createAnswer');
    if(!utils.loginCheck(req, res)) {
        res.status(400).send({ message: `Authentication Failed`});
        return;
    }
    const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
    const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

    var userid = syncSQL.query("SELECT id FROM Users WHERE username ='"+ login+ "'");
    // const queryObject = url.parse(req.url,true).query;
    // console.log("********************************************QID = " + req.params.question_id);
    // console.log("********************************************UUID = "+ userid[0].id);

    var result = await Question.findOne({where : {question_id:req.params.question_id}});
    if(result === null) {
    res.status(404).send({ message: `Question Not Found`});
    return;
    }

    var question_user = await User.findOne({where : {id:result.dataValues.UserId}});
    var question_user_email = question_user.dataValues.username;

    const answer = {
        answer_text: req.body.answer_text,
        user_id: userid[0].id,
        questionQuestionId: req.params.question_id
    }
    var timerDB = new Date();

    var answer_id_for_sns = "";
    await Answer.create(answer)
    .then(data => {
      res.send(data);
      answer_id_for_sns = data.dataValues.answer_id;
      console.log(" answer id " + answer_id_for_sns);
    })
    .catch(err => {
      res.status(400).send({
        message:
          err.message || "Some error occurred while creating the User."
      });
    });

     //SNS
     if(process.env.LOCAL_DEVELOPMENT) 
     {
       AWS.config.update({
         accessKeyId: process.env.DEV_ACCESS_KEY,
         secretAccessKey: process.env.DEV_SECRET_KEY,
         region: 'us-east-1',
       });
     } else {
      AWS.config.update({
        region: 'us-east-1'
      });
    }
     
     var SNSbody = "QuestionID: "+req.params.question_id+"\n"+"Question posted by user: "+question_user_email+"\n"+
     "Answer ID: "+answer_id_for_sns+"\n"+"Answer text: "+req.body.answer_text+"\n"+"Answer user email: "+login+"\n";
     console.log(SNSbody);
     const sns= new AWS.SNS();
     var SNSStr = {
       to_email1: login,
       to_email2: question_user_email,
       answer_text: req.body.answer_text,
       question_id: req.params.question_id,
       from_email: "info@"+req.hostname,
       subject: "Question is answered",
       body: SNSbody+"https://"+req.hostname+"/v1/question/"+req.params.question_id+"/answer/"+answer_id_for_sns
     }
 
     let params = {
       Message: JSON.stringify(SNSStr),
       Subject: "SNS WebAPP subject",
       TopicArn: process.env.SNS_ARN
   };
 
   sns.publish(params, function(err, data) {
       if (err) console.log(err, err.stack); 
       else console.log(data);
   }); 

    sdc.timing('createAnswer_DB', timerDB);  
    sdc.timing('createAnswer', timer); 
};

//Retrieve Answer By Question ID -- NOT Authenticated
exports.getAnswerByQid = async (req, res) => {
  logger.info("Get Answer by Question ID API");
  var timer = new Date();
  sdc.increment('getAnswerByQid');
  var result = await Answer.findOne({where : {questionQuestionId:req.params.question_id, answer_id:req.params.answer_id},
                                     include : "attachments"});
                                     
  
  if (result == null) {
    res.status(404).send({ message: `Not Found`});
    return;
  }

    result.dataValues.question_id =  result.dataValues.questionQuestionId;
    delete result.dataValues.questionQuestionId;
    res.send(result);
    sdc.timing('getAnswerByQid', timer); 
};

//Update answer by qid -- Authenticated
exports.updateAnswerByQid = async (req, res) => { 
  logger.info("Update Answer API");
  var timer = new Date();
  sdc.increment('updateAnswerByQid');
  if(!utils.loginCheck(req, res)) {
    res.status(400).send({ message: `Authentication Failed`});
    return;
  }
  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

  if(req.body.answer_text == null || req.body.answer_text === "") {
    console.log("what is happening here?")
    return res.status(401).send({message: "Unauthorized. No content to update!"});
  }

  var userfromLogin = syncSQL.query("SELECT id FROM Users WHERE username ='"+ login+ "'");
  var temp_result = await Answer.findOne({where : {questionQuestionId:req.params.question_id, answer_id:req.params.answer_id}});
  if (temp_result === null) {
    return res.status(404).send({message: "Not found!"});
  }
  console.log(req.params.question_id+" "+req.params.answer_id);

  if(userfromLogin === null || userfromLogin[0].id !== temp_result.user_id) {
    return res.status(401).send({message: "Unauthorized. Answer can be updated only by user who posted it!"});
  }

  var questionTag = await Question.findOne({where : {question_id:req.params.question_id}});
  var question_user = await User.findOne({where : {id:questionTag.dataValues.UserId}});
  var question_user_email = question_user.dataValues.username;

  var result = await Answer.update({answer_text: req.body.answer_text}, {where : {questionQuestionId:req.params.question_id, answer_id:req.params.answer_id}});
  var answer_id_for_sns = req.params.answer_id;
  //SNS
  if(process.env.LOCAL_DEVELOPMENT) 
  {
    AWS.config.update({
      accessKeyId: process.env.DEV_ACCESS_KEY,
      secretAccessKey: process.env.DEV_SECRET_KEY,
      region: 'us-east-1',
    });
  } else {
    AWS.config.update({
      region: 'us-east-1'
    });
  }

  var SNSbody = "QuestionID: "+req.params.question_id+"\n"+"Question posted by user: "+question_user_email+"\n"+
  "Answer ID: "+answer_id_for_sns+"\n"+"Answer text: "+req.body.answer_text+"\n"+"Answer user email: "+login+"\n";
  console.log(SNSbody);
  const sns= new AWS.SNS();
  var SNSStr = {
    to_email1: login,
    to_email2: question_user_email,
    answer_text: req.body.answer_text,
    question_id: req.params.question_id,
    from_email: "info@"+req.hostname,
    subject: "Answer is Updated",
    body: SNSbody+"https://"+req.hostname+"/v1/question/"+req.params.question_id+"/answer/"+answer_id_for_sns
  }

  let params = {
    Message: JSON.stringify(SNSStr),
    Subject: "SNS WebAPP subject",
    TopicArn: process.env.SNS_ARN
  };

  sns.publish(params, function(err, data) {
      if (err) console.log(err, err.stack); 
      else console.log(data);
  }); 
  res.status(204).send();
  sdc.timing('updateAnswerByQid', timer); 
}

//Delete answer by qid -- Authenticated
exports.deleteAnswerByQid = async (req, res) => {
  // console.log("*********************************************QID"+req.params.question_id);
  // console.log("*********************************************AID"+req.params.answer_id);
  logger.info("Delete Answer by QID API");
  var timer = new Date();
  sdc.increment('deleteAnswerByQid');
  if(!utils.loginCheck(req, res)) {
    res.status(400).send({ message: `Authentication Failed`});
    return;
  }
  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

  var userfromLogin = syncSQL.query("SELECT id FROM Users WHERE username ='"+ login+ "'");

  var temp_result = await Answer.findOne({where : {questionQuestionId:req.params.question_id, answer_id:req.params.answer_id}});
  if (temp_result === null) {
    return res.status(401).send({message: "Answer not found!"});
  }
  // console.log(req.params.question_id+" "+req.params.answer_id);

  if(userfromLogin === null || userfromLogin[0].id !== temp_result.user_id) {
    return res.status(401).send({message: "Answer can be deleted only by user who posted it!"});
  }
  
  await deleteS3AnswerObjects(req.params.answer_id);
  console.log("Calling deleteS3AnswerObjects with  " + req.params.answer_id);
  var result = await Answer.destroy({where : {questionQuestionId:req.params.question_id, answer_id:req.params.answer_id}});
  // if(result.length == 0) {
  //   return res.status(404).send({message: 'No content found'});
  // }
  
  var questionTag = await Question.findOne({where : {question_id:req.params.question_id}});
  var question_user = await User.findOne({where : {id:questionTag.dataValues.UserId}});
  var question_user_email = question_user.dataValues.username;
  var answer_id_for_sns = req.params.answer_id;

   //SNS
   if(process.env.LOCAL_DEVELOPMENT) 
   {
     AWS.config.update({
       accessKeyId: process.env.DEV_ACCESS_KEY,
       secretAccessKey: process.env.DEV_SECRET_KEY,
       region: 'us-east-1',
     });
   } else {
    AWS.config.update({
      region: 'us-east-1'
    });
  }
 
   var SNSbody = "QuestionID: "+req.params.question_id+"\n"+"Question posted by user: "+question_user_email+"\n"+
   "Deleted answer ID: "+answer_id_for_sns+"\n"+"Answer user email: "+login+"\n";
   console.log(SNSbody);
   const sns= new AWS.SNS();
   var SNSStr = {
     to_email1: login,
     to_email2: question_user_email,
     answer_text: req.body.answer_text,
     question_id: req.params.question_id,
     from_email: "info@"+req.hostname,
     subject: "Answer is Deleted",
     body: SNSbody+"https://"+req.hostname+"/v1/question/"+req.params.question_id
   }
 
   let params = {
     Message: JSON.stringify(SNSStr),
     Subject: "SNS WebAPP subject",
     TopicArn: process.env.SNS_ARN
   };
 
   sns.publish(params, function(err, data) {
       if (err) console.log(err, err.stack); 
       else console.log(data);
   }); 

  res.status(204).send({message: 'Deleted successfully!'});
  sdc.timing('deleteAnswerByQid', timer); 
};

exports.uploadFile = async (source,targetName, req, res) => { 
  logger.info("Upload Answer File API");
  var timer = new Date();
  sdc.increment('uploadFileAnswer');
  if(!utils.loginCheck(req, res)) {
    res.status(400).send({ message: `Authentication Failed`});
    fs.unlinkSync(source);
    return;
  }
  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

  var userid = syncSQL.query("SELECT id FROM Users WHERE username ='"+ login+ "'");

  var answerObj = await Answer.findOne({where : {answer_id:req.params.answer_id}});
  console.log("----------------------------------------------");
  console.log(answerObj);
  console.log(userid);
  if(answerObj === null || userid[0].id !== answerObj.user_id || req.params.question_id !== answerObj.questionQuestionId) {
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
  var storagePlace = req.params.question_id+"/"+req.params.answer_id+"/"+targetName;
  //var storagePlace = targetName;
  var timerS3 = new Date();
  console.log('preparing to upload answer...');
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
  sdc.timing('uploadFileAnswer_S3', timerS3); 
  const answerimage = {
    // etag_text: imageData.ETag,
    target_path: storagePlace,
    answerAnswerId: req.params.answer_id
  }

  AnswerImage.create(answerimage)
  .then(data => {
    res.status(201).send(data);
  })
  .catch(err => {
    res.status(400).send({
      message:
        err.message || "Some error occurred while inserting answer data into DB"
    });
  });
  sdc.timing('uploadFileAnswer', timer); 
};

exports.deleteFile = async (req, res) => { 
  logger.info("Delete Answer File API");
  var timer = new Date();
  sdc.increment('deleteFileAnswer');
  if(!utils.loginCheck(req, res)) {
    res.status(400).send({ message: `Authentication Failed`});
    fs.unlinkSync(source);
    return;
  }
  const b64auth = (req.headers.authorization || '').split(' ')[1] || ''
  const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':')

  var userid = syncSQL.query("SELECT id FROM Users WHERE username ='"+ login+ "'");

  var answerObj = await Answer.findOne({where : {answer_id:req.params.answer_id}});
  console.log("----------------------------------------------");
  console.log(answerObj);
  console.log(userid);
  if(answerObj === null || userid[0].id !== answerObj.user_id || req.params.question_id !== answerObj.questionQuestionId) {
    res.status(400).send();
    fs.unlinkSync(source);
    return;
  }

  //var result = await QuestionImage.findOne({where : {questionQuestionId:req.params.question_id, file_id:req.params.file_id}});
  var imageToDelete = await AnswerImage.findOne({where : {file_id:req.params.file_id}});
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
          res.status(400).send({message: "Data could not be deleted from S3"});
          return;
        } 
      });
    }
    catch (error) {
      console.log({'err':err});
      res.status(404).send();
      return;
    }
  
    var result = await AnswerImage.destroy({where : {file_id:req.params.file_id}});
    res.status(204).send({message: 'Deleted successfully!'});
    sdc.timing('deleteFileAnswer', timer); 
};

async function deleteS3AnswerObjects(answerID) {
  var result = await AnswerImage.findAll({where : {answerAnswerId:answerID}});
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