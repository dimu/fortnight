'''
research about python class

Created on 2017年5月25日

@author: dwx
'''

class Employee:
    'Common base class for all employees'
    empCount = 0

    #class constructor, self argument refer to this
    def __init__(self, name, salary):
        self.name = name
        self.salary = salary
        Employee.empCount += 1
   
    def displayCount(self):
        print ("Total Employee %d" % Employee.empCount)

    def displayEmployee(self):
        print ("Name : ", self.name, ", Salary: ", self.salary)


# This would create first object of Employee class"
emp1 = Employee("Zara", 2000)  #Instantiation 
# This would create second object of Employee class"
emp2 = Employee("Manni", 5000)
emp1.displayEmployee()
emp1.salary = 3000
emp1.displayEmployee()
#del emp1.salary
#emp1.displayEmployee()#del salary attribute, throw AttributeError
emp2.displayEmployee()

hasattr(emp1, 'salary')    # Returns true if 'salary' attribute exists
getattr(emp1, 'salary')    # Returns value of 'salary' attribute
setattr(emp1, 'salary', 7000) # Set attribute 'salary' at 7000
delattr(emp1, 'salary')    # Delete attribute 'salary'
print ("Total Employee %d" % Employee.empCount)

print ("Employee.__doc__:", Employee.__doc__)
print ("Employee.__name__:", Employee.__name__)
print ("Employee.__module__:", Employee.__module__)
print ("Employee.__bases__:", Employee.__bases__)
print ("Employee.__dict__:", Employee.__dict__ )

#garbage collector reference count
a = 40      # Create object <40>
b = a       # Increase ref. count  of <40> 
c = [b]     # Increase ref. count  of <40> 

del a       # Decrease ref. count  of <40>
b = 100     # Decrease ref. count  of <40> 
c[0] = -1   # Decrease ref. count  of <40> 

#garbage collector 
class Point:
    def __init__( self, x=0, y=0):
        self.x = x
        self.y = y
    def __del__(self): #重写gc方法
        class_name = self.__class__.__name__
        print (class_name, "destroyed")

pt1 = Point()
pt2 = pt1
pt3 = pt1
print (id(pt1), id(pt2), id(pt3));   # prints the ids of the obejcts
del pt1
del pt2
del pt3


#class inheritance 类集成实例，python是多继承
class Parent:        # define parent class
    parentAttr = 100
    def __init__(self):
        print ("Calling parent constructor")

    def parentMethod(self):
        print ('Calling parent method')

    def setAttr(self, attr):
        Parent.parentAttr = attr 

    def getAttr(self):
        print ("Parent attribute :", Parent.parentAttr)
    
    def myMethod(self):
        print ('Calling parent method')

class Child(Parent): # define child class
    def __init__(self):
        print ("Calling child constructor")

    def childMethod(self):
        print ('Calling child method')
        
    def myMethod(self):
        print ('Calling child method')

c = Child()          # instance of child
c.childMethod()      # child calls its method
c.parentMethod()     # calls parent's method
c.setAttr(200)       # again call parent's method
c.getAttr()          # again call parent's method
d = Child() #another instance of child
d.getAttr() #class variable share the same value

print(issubclass(Child, Parent)) #
print(isinstance(c, Parent)) #type check
c.myMethod() #method override 方法重写

#Data Hiding 数据作用域
class JustCounter:
    __secretCount = 0
    publicCount = 0
    classCount = 0
  
    def count(self):
        self.__secretCount += 1
        self.publicCount += 1
        JustCounter.classCount += 1
        #JustCounter.__secretCount += 1 #第二次实例化时，会影响__secretCount初始值，可以打开注释看看两次运行的效果
        #私有变量
        print (self.__secretCount,self.publicCount,JustCounter.classCount,JustCounter.__secretCount)

counter = JustCounter()
counter.count()
counter.count()
#print (counter.__secretCount) #AttributeError
print(counter._JustCounter__secretCount,counter.publicCount) #相当于实例指向类再指向私有变量
counter1 = JustCounter()
counter1.count()
