const log = require('simple-node-logger');
logger = log.createSimpleFileLogger('webApp.log');
logger.setLevel('trace');

module.exports = logger