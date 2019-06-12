'use strict';

const Controller = require('egg').Controller;

class UserController extends Controller {

  async create1() {
    const { ctx } = this;

    console.log('step into user create！');

    ctx.getLogger('userLogger').info('custom logger test');

    if (ctx.request.query.userid) {
      console.log('query param userid is' + ctx.request.query.userid);
      ctx.body = 'request param userid: ' + ctx.request.query.userid;
      const user = {};
      Object.assign(user, { name: 'zhangsan', age: 12 });
      ctx.service.userService.insert(user);
    } else {
      ctx.body = 'need input param userid';
    }
  }

  async getUser() {
    this.ctx.body = 'search user id is: ' + this.ctx.params.userid;
  }

}

module.exports = UserController;

