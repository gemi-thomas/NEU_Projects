module.exports = (sequelize, Sequelize) => {
    const Question = sequelize.define("question", {
    question_id: {
        primaryKey:true,
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4
        },
    question_text: {
        type: Sequelize.STRING
        }
    });
    return Question;
  };