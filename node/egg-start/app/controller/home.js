'use strict';

const Controller = require('egg').Controller;

class HomeController extends Controller {
  async index() {
    console.log('queryid:' + this.app.request.query.id);
    this.ctx.body = 'hello world!';
    this.logger.info('home controller ');
  }
}

module.exports = HomeController;
