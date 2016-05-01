# 内存清理程序

## v1

介绍： http://www.jianshu.com/p/5ea7081a6818 

![](./v1-pro-structure.png)

![](./v1-demo.gif)

![](./v1-layout-land.png)

## v2

![](./v2-pro-structure.png)

![](./v2-demo.gif)

## 获取内存使用情况

**方式1：**
```
MemoryInfo mi = new MemoryInfo();
ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
activityManager.getMemoryInfo(mi);
long availableMegs = mi.availMem / 1048576L;

//Percentage can be calculated for API 16+
long percentAvail = mi.availMem / mi.totalMem;  // totalMem是 API16+才存在
```

**方式2：**

```
$ cat /proc/meminfo
```

写个程序分析文件`/proc/meminfo`即可。

