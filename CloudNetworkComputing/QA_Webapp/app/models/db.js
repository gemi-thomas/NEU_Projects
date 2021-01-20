const dbConfig = require("../config/db.config.js");
const Sequelize = require("sequelize");
const logger = require("../Utils/logger.js");

// Create a connection to the database
const sequelize = new Sequelize(dbConfig.DB, dbConfig.USER, dbConfig.PASSWORD, {
  host: dbConfig.HOST,
  dialect: dbConfig.dialect,
  operatorsAliases: false,
  dialectOptions: {
    ssl: 'Amazon RDS'
  },
  
  pool: {
    max: dbConfig.pool.max,
    min: dbConfig.pool.min,
    acquire: dbConfig.pool.acquire,
    idle: dbConfig.pool.idle
  }
});

sequelize.query("SHOW STATUS LIKE 'Ssl_cipher'", { type: sequelize.QueryTypes.SELECT })
   .then((result) => {
       logger.info("Using SSL connection: status:" + result[0].Value);
});


const db = {};

db.Sequelize = Sequelize;
db.sequelize = sequelize;

db.user = require("./user.model.js")(sequelize, Sequelize);
db.questions = require("./question.model.js")(sequelize, Sequelize);
db.answers = require("./answer.model.js")(sequelize, Sequelize);
db.category = require("./category.model.js")(sequelize, Sequelize);
db.questionimage = require("./questionimage.model.js")(sequelize, Sequelize);
db.answerimage = require("./answerimage.model.js")(sequelize, Sequelize);

db.user.hasMany(db.questions, { as: "questions" });
db.questions.belongsTo(db.user);

db.questions.hasMany(db.answers, { as: "answers" });
db.answers.belongsTo(db.questions);

//One-to-Many question to images
db.questions.hasMany(db.questionimage, {as: "attachments"});
db.questionimage.belongsTo(db.questions);

//One-to-Many answer to images
db.answers.hasMany(db.answerimage, {as: "attachments"});
db.answerimage.belongsTo(db.answers);


db.questions.belongsToMany(db.category, {
  through: "question_category",
  as: "categories",
  foreignKey: "question_id",
});
db.category.belongsToMany(db.questions, {
  through: "question_category",
  as: "question",
  foreignKey: "category_id",
});


module.exports = db;

