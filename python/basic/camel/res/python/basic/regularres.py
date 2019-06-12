'''
research about python regular expressions 

Created on 2017年5月26日

@author: dwx
'''
import re
match1 = re.match('Hello[ \t]*(.*)world', 'Hello    python world')
#print(match1)
if match1:
    group1 = match1.group(1)
    print(group1)
else:
    print("no matching")
    
line = "Cats are smarter than dogs"

matchObj = re.match( r'(.*) are (.*?) .*', line, re.M|re.I)

if matchObj:
    print ("matchObj.group() : ", matchObj.group())
    print ("matchObj.group(1) : ", matchObj.group(1))
    print ("matchObj.group(2) : ", matchObj.group(2))
else:
    print ("No match!!")