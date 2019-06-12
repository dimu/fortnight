#方法定义模式，方法名，注释文档，功能
#def functionname( parameters ):
#   "function_docstring"
#   function_suite
#   return [expression]

def sum1(str1, str2):
    "This print two arguments" #
    return str1 + str2 

print(sum1(1,2))

def changeme( mylist ):
    "This changes a passed list into this function"
    print ("Values inside the function before change: ", mylist)
    mylist[2]=50
    print ("Values inside the function after change: ", mylist)
    return

# Now you can call changeme function
mylist = [10,20,30]
changeme( mylist )
print ("Values outside the function: ", mylist)

# Function definition is here
def changeme1( mylist ):
    "This changes a passed list into this function"
    mylist = [1,2,3,4] # This would assi new reference in mylist
    print ("Values inside the function: ", mylist)
    return

# Now you can call changeme function
mylist = [10,20,30]
changeme1( mylist )
print ("Values outside the function: ", mylist)

# keywords arguments
def printinfo( name, age ):
    "This prints a passed info into this function"
    print ("Name: ", name)
    print ("Age ", age)
    return

# Now you can call printinfo function
printinfo( age = 50, name = "miki" )

#default argument
def printinfo1( name, age = 35 ):
    "This prints a passed info into this function"
    print ("Name: ", name)
    print ("Age ", age)
    return

# Now you can call printinfo function
printinfo1( age = 50, name = "miki" )
printinfo1( name = "miki" )

#不定长参数以*开始
def printinfo2( arg1, *vartuple ):
    "This prints a variable passed arguments"
    print ("Output is: ")
    print (arg1)
    for var in vartuple:
        print (var)
    return

# Now you can call printinfo function
printinfo2( 10 )
printinfo2( 70, 60, 50 )

# Function definition is here
sum1 = lambda arg1, arg2: arg1 + arg2


# Now you can call sum as a function
print ("Value of total : ", sum1( 10, 20 ))
print ("Value of total : ", sum1( 20, 20 ))


total = 0 # This is global variable.
# Function definition is here
def sum2( arg1, arg2 ):
    # Add both the parameters and return them."
    total = arg1 + arg2; # Here total is local variable.
    print ("Inside the function local total : ", total)
    return total

# Now you can call sum function
sum2( 10, 20 )
print ("Outside the function global total : ", total )