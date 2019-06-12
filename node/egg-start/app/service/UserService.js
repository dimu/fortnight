'use strict'

const Service = require('egg').Service;

class UserService extends Service {
    async insert(user) {
        await this.ctx.db.insert();
    }
}

module.exports = UserService;