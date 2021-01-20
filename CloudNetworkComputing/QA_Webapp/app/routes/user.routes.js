module.exports = app => {
    const user = require("../controllers/user.controller.js");
    const question = require("../controllers/question.controller.js");
    const answer = require("../controllers/answer.controller.js");
    const multer  = require('multer');

    const storage = multer.diskStorage({
      destination : 'uploads/',
      filename: function (req, file, cb) {
        cb(null, file.originalname);
      }
    });

    const upload = multer({ storage: storage });

    app.post("/v1/user", user.create);
    app.get("/v1/user/self", user.findOne);
    app.put("/v1/user/self", user.update);
    app.get("/v1/user/:id", user.getUserById); 

    app.post("/v1/question/", question.createQuestion); 
    app.get("/v1/questions", question.getAllQuestions); 
    app.get("/v1/question/:question_id", question.getQuestionById); 
    app.delete("/v1/question/:question_id", question.deleteQuestion); 
    app.put("/v1/question/:question_id", question.updateQuestionByQid);

    app.post("/v1/question/:question_id/answer", answer.createAnswer); 
    app.get("/v1/question/:question_id/answer/:answer_id", answer.getAnswerByQid); 
    app.delete("/v1/question/:question_id/answer/:answer_id", answer.deleteAnswerByQid); 
    app.put("/v1/question/:question_id/answer/:answer_id", answer.updateAnswerByQid); 

    //app.post("/v1/question/:question_id/file", question.uploadFile);
    app.post('/v1/question/:question_id/file', upload.single('pic'), function (req, res) {
      //Multer middleware adds file(in case of single file ) or files(multiple files) object to the request object.
      //req.file is the demo_file
      question.uploadFile(req.file.path, req.file.filename, req, res);
    })
    
    app.post("/v1/question/:question_id/answer/:answer_id/file",  upload.single('pic'), function (req, res) {
       answer.uploadFile(req.file.path, req.file.filename, req, res);
    });

    app.delete("/v1/question/:question_id/file/:file_id", question.deleteFile);
    app.delete("/v1/question/:question_id/answer/:answer_id/file/:file_id", answer.deleteFile);
  };