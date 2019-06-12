'''
research about how to access database in python

Created on 2017年5月26日

@author: dwx
'''
import psycopg2

class LocalDataBase():
    "localDatabase connect test"
    
    #
    def initialDatabaseConnect(self): 
        self.conn = psycopg2.connect(host="localhost",port=5432,user="postgres",password="111111",database="demo")
    
    #查询
    def query(self, sql):
        cur = self.conn.cursor()
        print(cur.execute(sql))
        #获取所有的数据
        data = cur.fetchall()
        print(data)
        for item in data:
            print(item)
        self.conn.commit()
        self.conn.close()
    
    #新增    
    def insert(self,sql):
        self.initialDatabaseConnect()
        cur = self.conn.cursor()
        try:
            cur.execute(sql)
            self.conn.commit()
        except Exception as e:
            print(e)
            self.conn.rollback()
            
        self.conn.close()
        
test = LocalDataBase()
test.initialDatabaseConnect()
test.query("select * from c3_sm_person")       
test.insert("insert into c3_sm_department values(6, \'xx\',2,3,'',100)")