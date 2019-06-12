'''
Created on 2017年5月27日

@author: dwx
'''
import socket

# create a socket object
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 

# get local machine name
# host = socket.gethostname()    
host = '127.0.0.1'                       

port = 1322

# connection to hostname on the port.客户端随机分配端口
s.connect((host, port))                               

# Receive no more than 1024 bytes
msg = s.recv(1024)                                     

s.close()

print (msg.decode('ascii'))