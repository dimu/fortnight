'''
python socket res:

low level socket res

Created on 2017年5月27日

@author: dwx
'''
import socket

#创建ipv4的tcp连接
serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

host = '127.0.0.1'
port = 1322

#套接字绑定服务器端口
serversocket.bind((host, port))

#设置每个套接字尝试的连接次数
serversocket.listen()

while True:
    #accept返回一个(socket，address)类型对象
    clientsocket,addr = serversocket.accept()
    print("Get a connection from %s" % str(addr))
    
    msg='Thank you for connecting'+ "\r\n"
    clientsocket.send(msg.encode('ascii'))
    clientsocket.close()