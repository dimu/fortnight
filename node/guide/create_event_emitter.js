var EventEmitter = require('events').EventEmitter;
var channel = new EventEmitter();

//事件触发
channel.on('xx', function(arg1, arg2){
    console.log("xxddd");
    console.log(arg1);
    console.log(arg2)
});

//通过箭头函数监听
channel.on('click', (arg1, arg2) => {
    console.log('onclick event, args is %s, %d', arg1, arg2);
});

channel.emit('xx', 'aa', 'bb');
channel.emit('click', 'dwx', 18);