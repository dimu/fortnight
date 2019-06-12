'''
research about python multithread

Created on 2017年5月26日

@author: dwx
'''
import threading
import time

exitFlag = 0

#定义thread类
class myThread (threading.Thread):
    def __init__(self, threadID, name, counter):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.counter = counter
    def run(self):
        print ("Starting " + self.name)
        print_time(self.name, self.counter, 5)
        print ("Exiting " + self.name)

def print_time(threadName, delay, counter):
    while counter:
        if exitFlag:
            threadName.exit()
        time.sleep(delay)
        print ("%s: %s" % (threadName, time.ctime(time.time())))
        counter -= 1

# Create new threads
thread1 = myThread(1, "Thread-1", 1)
thread2 = myThread(2, "Thread-2", 2)

# Start new Threads
thread1.start()
thread2.start()
thread1.join()  #一直等待当前线程终止，等待过程中会阻塞当前main thread
thread2.join()  #可以注释thread1与thread2的join方法，看看执行效果
print ("Exiting Main Thread")


class myThread1 (threading.Thread):
    def __init__(self, threadID, name, counter):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.counter = counter
    def run(self):
        print ("Starting " + self.name)
        # Get lock to synchronize threads,获取原子锁
        threadLock.acquire()
        print_time(self.name, self.counter, 3)
        # Free lock to release next thread，释放原子锁
        threadLock.release()

def print_time1(threadName, delay, counter):
    while counter:
        time.sleep(delay)
        print ("%s: %s" % (threadName, time.ctime(time.time())))
        counter -= 1

#线程锁对象
threadLock = threading.Lock()
threads = []

# Create new threads
thread1 = myThread1(1, "Thread-1", 1)
thread2 = myThread1(2, "Thread-2", 2)

# Start new Threads
thread1.start()
thread2.start()

# Add threads to thread list
threads.append(thread1)
threads.append(thread2)

# Wait for all threads to complete
for t in threads:
    t.join()
print ("Exiting Main Thread")
