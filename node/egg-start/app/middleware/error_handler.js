'use strict';

// 统一的异常中间件，
module.exports = () => {
  return async function errorHandler(ctx, next) {
    try {
      await next();
    } catch (err) {
      ctx.app.emit('error', err, ctx);

      const status = err.status || 500;

      const error = status === 500 && ctx.app.config.env === 'prod'
        ? 'Internal Server Error'
        : err.message;

      // 从 error 对象上读出各个属性，设置到响应中
      ctx.body = { error };
      if (status === 422) {
        ctx.body.detail = err.errors;
      }
      ctx.status = status;
    }
  };
};
