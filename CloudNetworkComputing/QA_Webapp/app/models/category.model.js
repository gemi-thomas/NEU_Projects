module.exports = (sequelize, Sequelize) => {
    const Category = sequelize.define("category", {
      category_id: {
        primaryKey: true,
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4
      },
      category: {
        type: Sequelize.STRING
      }
    });
    return Category;
  };