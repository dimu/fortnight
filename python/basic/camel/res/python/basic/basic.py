'''
Created on 2017年5月23日

@author: dwx
'''

import math
import random
import base64
import time
import calendar
#import datetime
#import moduleres

numbers = [2, 4, 6, 8]
#make a copy of numbers
numbers1 = numbers[:]
print(numbers1 == numbers) #True
print(numbers1 is numbers) #False 拷贝
numbers2 = numbers1
print(numbers2 is numbers1) #True
product = 1
for number in numbers:
    product = product * number
print('The product is:', product)

print(float(12))  #转浮点数
print(int(12.00)) #转整形
print(complex(1,2))  #转复数

# math function 基本的函数
print(abs(-12))  #绝对值
print(math.ceil(12.3)) #最小的比当前值大的整数
print(math.exp(2)) #e的x次方
print(math.log(math.exp(2))) #以e为底的对数
print(math.log10(100))
print(max(1,2,3))
print(math.modf(math.pi))
print(math.pow(3,2))
print(round(10.4))
print(round(10.5))
print(round(0.5))
print(round(1.5))
print(round(10.6))
print(math.sqrt(4))

#############随机数操作###############
print(random.choice([1,2,3,4]))
print(random.choice('abc'))

print(random.randrange(0,10,2))
print(random.random())
random.seed()
print(random.random())
random.seed(10)
print(random.random())

#################string操作#############
print('abc'.capitalize())
print('abcdedf'.center(20,'h')) #扩展指定数据到特定的长度，默认用空格补足
print('abcdabcea'.count('a'))  #统计字符出现的次数
bytestring = 'abcd'.encode('utf_8', 'strict') #转换成utf-8的编码
print(bytestring)
print(base64.encodebytes(bytestring))
#print('abcd'.encode('base64', 'strict')) 


#list操作 ，支持所有的list常见操作，插入，删除，
#连接，反转，排序，判断，遍历等 
chaList = ['aa','bb', 'cc', 'dd']
print(chaList[1])  #return bb 
print(chaList[1:]) #return ['bb','cc','dd']
chaList[1] = 'ff'
print(chaList) #return ['aa','ff','cc','dd']
del chaList[1]
print(chaList) #return ['aa', 'cc', 'dd']

print(chaList + ['gg','hh']) #连接操作
print(len(chaList))  #长度计算
print('aa' in chaList) #判断是否包含
for item in chaList:  #循环遍历
    print(item)
print(max(chaList)) #比较大小
chaList.append('df')
print(max(chaList)) #字典序
print(list((12,34,56))) #将tuple转化为list

chaList.insert(0, 'xx') #插入算法
print(chaList)
chaList.reverse() #反转
print(chaList)
chaList.sort(key=None, reverse=True) #reverse为true表示倒序
print(chaList)

#tuple操作，list不可变
tup1 = () #define an empty tuple
print(tup1)
tup2 = (11,) #1个元组必须以,结尾
print(tup2)
tup3 = tup1 + tup2
print(tup3)

#dict类型操作，类似于map操作
dict1 = {'id':122222, 'name':'dd', 'age':21}
print(dict1)
print(dict1['id'])

#循环遍历dict
for item in dict1.keys():
    print(item, dict1[item])
print(dict1.items())
print(dict1.__contains__('id'))
print(dict1.values())
print(len(dict1))

#Date&Time
ti = time.time()
print(ti)  #返回从1970年1月1日0分0秒的秒数   1495587962.8316133
print(time.localtime())
print(time.localtime(time.time()))
print(time.asctime(time.localtime()))
tiStr = '2017-05-24 09:29:35'
print(time.strptime(tiStr, '%Y-%m-%d %H:%M:%S'))

#calendar 
cal = calendar.month(2017,5) #获取指定月份的日历
print(cal)

#全局变量-》局部引用
Money = 2000
def AddMoney():
    # Uncomment the following line to fix the code:
    global Money
    Money = Money + 1

print (Money)
AddMoney()
print (Money)

cont = dir(math)
print(cont)

# ddd = 123
# def ff():
#     ddd = ddd +1
#     print(ddd)
# ff()

def KelvinToFahrenheit(Temperature):
    try:
        assert (Temperature >= 0),"Colder than absolute zero!"
        return ((Temperature-273)*1.8)+32
    except AssertionError :  #catch exception
        print(Temperature, " wrong value")
        return
        

print (KelvinToFahrenheit(273))
print (int(KelvinToFahrenheit(505.78)))
print (KelvinToFahrenheit(-5))

#try except else
try:
    fh = open("testfile", "r")
    fh.write("This is my test file for exception handling!!")
except IOError:
    print ("Error: can\'t find file or read data")
else:
    print ("Written content in the file successfully")
    fh.close()
    
#try finally
try:
    fh = open("testfile", "w")
    try:
        fh.write("This is my test file for exception handling!!")
    finally:
        print ("Going to close the file")
        fh.close()
except IOError:
    print ("Error: can\'t find file or read data")
    
    
# Define a function here. exception information as argument ,like java e
def temp_convert(var):
    try:
        return int(var)
    except ValueError as Argument:
        print ("The argument does not contain numbers\n", Argument)

# Call above function here.
temp_convert("xyz")

#raise exception the same as throw exception instance
def functionName( level ):
    if level <1:
        raise Exception(level)
        # The code below to this would not be executed
        # if we raise the exception
    return level

try:
    l = functionName(-10)
    print ("level = ",l)
except Exception as e:
    print ("error in level argument",e.args[0])
    print(e)
    
#user defined exception
class Networkerror(RuntimeError): #RuntimeError子类
    def __init__(self, arg): #暂时认为是构造函数
        self.args = arg
        
        
# try:
#     raise Networkerror("Bad hostname")
# except Networkerror, e: #e as Networkerror instance
#     print e.args
s1, s2 = 1,2
print(s1,s2)
#formatting expression
print('%s, good morning, and %s happy birthday' %('Lucy','Nacy'))

st3 = 'abc'
#dir函数返回对象的所有属性
print(dir(st3))
#print help doc
help(st3.replace)
#help(st3)

#解析矩阵
matrix1 = [[1,2,3,10],[4,5,6],[7,8,9,11]]
print(matrix1[0])
print(matrix1[0][1])

#列表解析
col2 = [row[2] for row in matrix1]
print(col2)

#等价于
col3 = []
for row in matrix1:
    col3.append(row[2])
print(col3)

#复杂的列表解析,获取第三列的偶数
col4 = [row[2] for row in matrix1 if row[2]%2!=0]
print(col4)

#求和解析
dict2 = {i:sum(matrix1[i]) for i in range(len(matrix1))}
print(dict2)

list4 = list(map(sum, matrix1))
print(list4)

#采用set，不可变的无序集合
set1 = set('abcd')
print(set1)
set2 = {'e','f','g','a'}
#交
print(set1 & set2)
#并
print(set1 | set2)
#补
print(set1 - set2)

rawp = r'\temp\abcd'
print(rawp)
bstr = b'abcd'
print(bstr)
