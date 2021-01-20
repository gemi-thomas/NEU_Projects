module.exports = (sequelize, Sequelize) => {
    const Answer = sequelize.define("answer", {
    answer_id: {
        primaryKey:true,
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4
        },
    user_id: {
        type: Sequelize.STRING,
        },
    answer_text: {
        type: Sequelize.STRING
        }
    });
    return Answer;
  };