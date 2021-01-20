module.exports = (sequelize, Sequelize) => {
    const AnswerImage = sequelize.define("answerImage", {
    file_id: {
        primaryKey:true,
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4
        },
    target_path: {
        type: Sequelize.STRING
    }
    });
    return AnswerImage;
  };