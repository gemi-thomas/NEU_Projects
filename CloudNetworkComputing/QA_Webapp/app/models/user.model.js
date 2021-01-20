module.exports = (sequelize, Sequelize) => {
    const User = sequelize.define("User", {
      id: {
        primaryKey:true,
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4
      },
      first_name: {
        type: Sequelize.STRING
      },
      last_name: {
        type: Sequelize.STRING
      },
      username: {
        type: Sequelize.STRING,
        allowNull: false,
        unique:true
      },
      password: {
        type: Sequelize.STRING
      }
      //account_created, account_updated will be created automatically by Sequielizer!! so No need to include in table.
      //ID is also provided by seq , incremental id, but we want to use a UUID format!
    });
    return User;
  };