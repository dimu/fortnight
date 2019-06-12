'''
research about how to send email in python

Created on 2017年5月26日

@author: dwx
'''
import smtplib #load smtplib module 
#from email.header import Header
from email.mime.text import MIMEText

#send string message
sender = "wenxiang0705@163.com" #mail sender
receivers = ["wenxiang0705@qq.com"] #mail receiver

#message内容必须遵守相关协议，例如RFCs,本次为文本字符串
message = """From: From Person <wenxiang0705@163.com>
To: To Person <wenxiang0705@qq.com>
Subject: SMTP e-mail test

This is a test e-mail message.
"""

def send(message):
    try:
        smtpObj = smtplib.SMTP()
        #默认的stmp端口为25，如果有些smtp服务提供商修改了端口，需要显示配置
        smtpObj.connect('smtp.163.com')
        #需要修改密码
        smtpObj.login("wenxiang0705@163.com", "****")
        global sender, receivers
        smtpObj.sendmail(sender, receivers, message)         
        print("Successfully sent email")
    except Exception as e:
        print("Error: unable to send email", e)
    

#hmtl message
htmlMes = MIMEText('<html><h1>你好</h1></html>', 'html', 'utf-8')
htmlMes['Subject'] = 'python html mail test'

#将MIMEText对象转换为string
send(htmlMes.as_string())

htmlMes1 = """From: From Person <wenxiang0705@163.com>
To: To Person <"wenxiang0705@qq.com">
MIME-Version: 1.0
Content-type: text/html
Subject: SMTP HTML e-mail test

This is an e-mail message to be sent in HTML format

<b>This is HTML message.</b>
<h1>This is headline.</h1>
"""
#send(htmlMes1)
