var net = require("net");

net.createServer(function(socket){
    socket.on('data', function(data){
        socket.write(data);
        socket.write('\r\n');
    });

    socket.on('connect', function(client){
        console.log(client.remoteAddress);
    });
}).listen(8888);


