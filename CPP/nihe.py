#coding:utf-8
import numpy
from scipy import log
from scipy.optimize import curve_fit
import matplotlib.pyplot as plt
import time 
import matplotlib.font_manager as fm

myfont = fm.FontProperties(fname='/Users/zhangzhaobo/CLionProjects/CCF/zui.ttf')


def func(x, a, b):
    y = a * log(x) + b
    return y

def polyfit(x, y, degree):
    results = {}
    popt, pcov = curve_fit(func, x, y)
    results['polynomial'] = popt

    # r-squared
    yhat = func(x ,popt[0] ,popt[1] )                         # or [p(z) for z in x]
    ybar = numpy.sum(y)/len(y)          # or sum(y)/len(y)
    ssreg = numpy.sum((yhat-ybar)**2)   # or sum([ (yihat - ybar)**2 for yihat in yhat])
    sstot = numpy.sum((y - ybar)**2)    # or sum([ (yi - ybar)**2 for yi in y])
    results['determination'] = ssreg / sstot
    return results


x = [200,400,800,1000,2000,3000,4000,5000,6000,8000,10000,12000,15000,17500,20000,22500,25000,27500,30000,32500,35000,37500,40000,42500,45000,47500,50000,52500,55000,57500,60000,62500,65000,67500,70000,72500,75000,77500,80000,82500,85000,87500,90000,92500,95000,97500,100000,110000,120000,125000,130000,140000,150000,160000,175000,]
y = [33.0,30.0,36.0,37.2,31.7,34.0,35.5,37.6,36.1,37.8,40.3,42.4,35.7,40.0,46.1,42.1,42.0,45.1,45.6,48.3,45.6,48.3,48.7,46.8,48.0,48.0,46.8,46.9,48.3,49.6,49.2,48.9,49.0,50.0,51.9,48.9,50.4,53.2,53.3,50.7,50.9,51.4,50.1,51.9,51.6,53.2,52.1,51.2,53.6,51.7,54.7,54.4,53.5,54.4,56.3,]
z0 = polyfit(x, y, 2)



x1 = [200,400,800,1000,2000,3000,4000,5000,6000,8000,10000,12000,15000,17500,20000,22500,25000,27500,30000,32500,35000,37500,40000,42500,45000,]
y1 = [55.0,49.5,51.0,56.0,54.3,53.9,54.2,55.4,56.8,55.9,56.1,55.2,56.1,56.8,56.4,57.4,56.8,56.8,57.4,57.6,56.7,58.1,57.6,57.2,58.4,]
z1 = polyfit(x1, y1, 2)
def showDynamic(dx,dy):
    s = []
    for i in x:
        s.append(i)
    for i in range(100):
        s.append(x[-1]+i*dx)
    yvals=func(s,z0['polynomial'][0],z0['polynomial'][1])

    s1 = []
    for i in x1:
        s1.append(i)
    for i in range(100):
        s1.append(x1[-1]+i*dy)
    yvals1 = func(s1,z1['polynomial'][0],z1['polynomial'][1])


    plot1=plt.plot(x, y, '*',label='Initial data point')
    plot2=plt.plot(s, yvals, 'r',label='Fitting curve')
    plt.legend(loc='upper left', prop = myfont)  

    plot3=plt.plot(x1, y1, '*')
    plot4=plt.plot(s1, yvals1, 'b')

    plt.xlabel('Amount')
    plt.ylabel('Accuracy (%)')
    plt.legend(loc=4)  #指定legend的位置,读者可以自己help它的用法
    plt.title('曲线拟合',fontproperties = myfont)
    plt.annotate('SVM', xy=(12000, 54.5), xytext=(24000, 50), arrowprops=dict(facecolor='blue', shrink=0.01), )  
    plt.annotate('DecisionTree', xy=(17000, 38), xytext=(30000, 33), arrowprops=dict(facecolor='red', shrink=0.01), )  
    plt.show()
    plt.close()

for i in range(10):
    dx = pow(2,i+5)
    dy = dx + (x[-1] - x1[-1])//100;
    showDynamic(dx,dy)
    time.sleep(0.1)


# from scipy import log
# import matplotlib.pyplot as plt
# from scipy.optimize import curve_fit
# import numpy as np
# #用指数形式来拟合
# x = np.array([200,400,800,1000,2000,3000,4000,5000,6000,8000,10000,12000,15000,17500,20000,22500,25000,27500,30000,32500,35000,37500,40000,42500,45000,47500,50000,52500,55000,57500,60000,62500,65000,67500,70000,72500,75000,77500,80000,82500,85000,87500,90000,92500,95000,97500,100000,110000,120000,125000,130000,140000,150000,160000,175000])
# y = np.array([33.0,30.0,36.0,37.2,31.7,34.0,35.5,37.6,36.1,37.8,40.3,42.4,35.7,40.0,46.1,42.1,42.0,45.1,45.6,48.3,45.6,48.3,48.7,46.8,48.0,48.0,46.8,46.9,48.3,49.6,49.2,48.9,49.0,50.0,51.9,48.9,50.4,53.2,53.3,50.7,50.9,51.4,50.1,51.9,51.6,53.2,52.1,51.2,53.6,51.7,54.7,54.4,53.5,54.4,56.3])
# def func(x, a, b):
#     y = a * log(x) + b
#     return y
# popt, pcov = curve_fit(func, x, y)
# a=popt[0]  #popt里面是拟合系数，读者可以自己help其用法
# b=popt[1]
# s = []
# for i in x:
#     s.append(i)
# for i in range(100):
#     s.append(x[-1]+i*1500)
# yvals=func(s,a,b)
# plot1=plt.plot(x, y, '*',label='original values')
# plot2=plt.plot(s, yvals, 'r',label='curve_fit values')
# plt.xlabel('x axis')
# plt.ylabel('y axis')
# plt.legend(loc=4)  #指定legend的位置,读者可以自己help它的用法
# plt.title('curve_fit')
# plt.show()