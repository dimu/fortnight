'''
file I/O res example
Created on 2017年5月24日

@author: dwx
'''
import os

#input Function
x = '12'
#x = input("age:")
print(x)
#treat all input as string 
print(x=='12') #True
print(x==12) #False


logfile = open("d:\\info1.log", 'r', 8192)
print(logfile.name)
print(logfile.closed)
print(logfile.mode)
buf = logfile.read(8192)
print(buf)
position = logfile.tell()
print(position)
buf = logfile.read(8192)
print(buf)
position = logfile.tell()
print(position)
logfile.close()

#os.renames('d:\\info1.log', 'd:\\info.log')

