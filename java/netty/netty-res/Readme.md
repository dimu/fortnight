## aim

basic knowledge about netty, from netty official guide.

[official 4.X version link](https://netty.io/wiki/user-guide-for-4.x.html)

## 前提条件
1. netty是基于nio设计的，采用异步思想开发

## 研究内容
1. 客户端往服务端发送数据，服务端同步返回
2. 客户端往服务端发送数据，服务端异步返回

## netty核心类


## netty集成gRPC序列化

gRPC框架主要解决序列化与反序列化问题，不处理service服务,在处理gRPC调用时，主要通过protobuf文件
定义message对象，然后在pipeline中添加ProtobufVarint32FrameDecoder,ProtobufDecoder,
ProtobufEncoder,ProtobufVarint32LengthFieldPrepender以及自己的handler类。

其中ProtobufVarint32FrameDecoder类，是解动态帧头，除去表头长度
ProtobufVarint32LengthFieldPrepender类，是ProtobufVarint32FrameDecoder的逆过程，在表头加入可变字节
最大程度为5个byte