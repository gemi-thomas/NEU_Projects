module.exports = (sequelize, Sequelize) => {
    const QuestionImage = sequelize.define("questionImage", {
    file_id: {
        primaryKey:true,
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4
        },
    target_path: {
        type: Sequelize.STRING
    }
    });
    return QuestionImage;
  };