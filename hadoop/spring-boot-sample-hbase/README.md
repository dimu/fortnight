# spring-boot-sample-hbase
##union package:include example about how two hbase table intersection
  * UnionMapper.class  subclass extends TableMapper
  * UnionReducer.class subclass extends TableReducer, output the reducer value to a new table
  * UnioJob.class main application , you have to export the project as a runnable jar, and import
  to centos system , then under the ${HBASE_HOME}/bin , excute haddop jar /xxx/jar  xxxx.xxx.UnionJob

##HelloHbase: the basic java applicaion about how connect to hbase server using client API
  > simple expample about hbase put, get ,scan api                                  

##mrunion: the hbase mapreduce to join MultiTable
  * compile method
  export as a Runnable Jar -> import into centos -> run 'hadoop jar xxx.jar'
