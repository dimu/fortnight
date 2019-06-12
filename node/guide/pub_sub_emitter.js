var events = require('events');
var net = require('net');

var channel = new events.EventEmitter();
channel.clients = {};
channel.subscriptions = {};

channel.on('join', function(id, client){
    this.clients[id] = client;
    this.subscriptions[id] = function (senderId, message) {
        console.log('subscription accept data ' + senderId);
        if (id != senderId) {
            this.clients[id].write(message);
        }
    }

    console.log(this.subscriptions[id]);
    this.on('broadcast', this.subscriptions[id]);

}) ;

channel.on('leave', function (id) {
    //将客户端的监听器移除
    channel.removeListener('broadcast', this.subscriptions[id]);
    //触发broadcast事件，查看剩余客户端响应
    channel.emit('broadcast',id, id + " has left the chat.\n");
});


/**
 * 回调函数自动监听，connect事件
 * @type {Server}
 */
var server = net.createServer(function(client){
   var id = client.remoteAddress + ":" + client.remotePort;
   console.log(id);
   // client.on('connect', function() {
       channel.emit('join', id, client);
       // console.log('client connected!');
   // });


    //
   client.on('data', function(data) {
       console.log("data input " + data);
       channel.emit('broadcast', id, data.toString());
   });

   //客户端断开时候，要将注册的监听器删除，否则服务端往客户端写数据时候，会报异常
   client.on('close', function(){
      channel.emit('leave', id);
   });
});

server.listen('8900');
