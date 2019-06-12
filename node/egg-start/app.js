// 用于自定义启动时的初始化工作
module.exports = app => {
  app.once('server', server => {
    console.log('server started!:' + server);
  });

  app.on('request', ctx => {
    console.log(ctx.startTime);
    console.log('request param:' + ctx);
  });

  app.on('response', ctx => {
    console.log('request.use time:' + Date.now() - ctx.starttime);
    console.log('response.log');
  });
};
