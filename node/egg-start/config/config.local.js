const path = require('path')

exports.logger = {
    level: 'DEBUG', //NONE, DEBUG, INFO, WARN, ERROR
}

module.exports = appInfo => {
    return {
        customLogger: {
            userLogger: {
                file: path.join(appInfo.root, 'logs/user.log'),
            }
        }
    }
}
