'use strict';

/**
 * @param {Egg.Application} app - egg application
 */
module.exports = app => {
  const { router, controller } = app;
  router.get('/', controller.home.index);

  router.get('/user/new', controller.user.userController.create1);
  router.get('/user/:userid', controller.user.userController.getUser);
};
