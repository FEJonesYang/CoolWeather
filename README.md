# CoolWeather



## 一、功能需求及技术可行性分析

### 0、 程序运行截图

<img src="https://tva1.sinaimg.cn/large/008i3skNly1grgetjytm2j30u00wchdu.jpg" alt="截屏2021-06-13 10.03.04" style="zoom:50%;" />

<img src="https://tva1.sinaimg.cn/large/008i3skNly1grgewgowsdj30u00wfjvg.jpg" alt="截屏2021-06-13 10.05.57" style="zoom:50%;" />



### 1、 第三方平台接入

#### 一、[和风天气](https://dev.qweather.com/docs/start/#使用api)

- 申请和风天气的对应 apiKey，具体和风天气的官网。
- 可以使用和风天气为我们封装好了的SDK，也可以使用它提供的 Api 文档，在这里我使用的是后者。

#### 二、[高德地图](https://lbs.amap.com)

同样，接入高德地图也需要获取到 apiKey，这里有两个需要注意的地方：

<img src="https://tva1.sinaimg.cn/large/008i3skNly1grgbwd1kb8j30yf0u0k4d.jpg" style="zoom:33%;" />



- 第一处是发布版本的 SHA1，我们需要自己创建一个 xxx.jks 文件，然后使用 keytool -list -v -keystore xxx.jks 命令获取到它的值。
- 第二处是发布版本的 SHA1 ，需要进入 .android 文件，执行 keytool -list -v -keystore debug.keystore。

**注意**：MacOS 需要在命令前面加上：/Applications/Android\ Studio.app/Contents/jre/jdk/Contents/Home/bin/



### 2、具备的功能

- 支持定位到当前城市，获取定位城市的天气信息。
- 支持城市搜索，可以定位到全国任意的城市。
- 城市切换后，还可以支持重新定位到当前的城市。
- 接入了高德的地图 SDK，支持制图模式下，点击全国任何一个地方，展示该地方的天气状况。



### 3、技术可行性分析

1. 如何获取天气数据的接口
   - 和风天气数据接口，详见和风天气提供的 Api。
   - 高德定位 SDK 以及地图 SDK，详见高德地图开发者平台。

2. 编写步骤
   - 基本配置
   - 遍历全国省市县数据
   - 显示天气数据
   - 编写天气界面
   - 将天气显示到界面上
   - 获取必应天气壁纸
   - 手动更新天气
   - 手动切换城市
   - 后台自动更新天气
   - 修改图标和名称



## 二、技术栈以及收获

1. 技术栈

- 图片加载框架：Glide
- JSON解析框架：Gson
- 网络访问框架：OKHttp3
- 事件传递机制：EventBus

2. 收获

- 对上述框架在项目中是如何使用的？
- 一个app的完整开发流程？



## 三、框架以及技术使用概要

1. **项目结构图**

   - 项目结构图

     <img src="https://tva1.sinaimg.cn/large/008i3skNly1grgcwd62rpj30io0t0ahq.jpg" style="zoom:33%;" />

   - 流程图

<img src="https://tva1.sinaimg.cn/large/008i3skNly1grgctg2ptrj30p40lgqdc.jpg" style="zoom:50%;" />







2. [Gson使用指南](https://github.com/google/gson)

   - **Include library**

     ```java
     implementation 'com.google.code.gson:gson:2.8.6'
     ```

   - 序列化

     ```
     Gson gson = new Gson();
     String json = gson.toJson(Object);
     ```

   - 反序列化

     ```
     Gson gson = new Gson();
     Object oj = gson.fromJson(json,Object.class);
     ```





4. [Glide使用指南](https://github.com/bumptech/glide)

   - **Include library**

     ```java
     implementation 'com.github.bumptech.glide:glide:4.11.0'
     annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
     ```

   - **For  a simple view**

     ```java
     ImageView imageView = (ImageView) findViewById(R.id.my_image_view);
     Glide.with(this).load(url).into(imageView);
     ```

   - **For a simple image list**

     ```java
     @Override public View getView(int position, View recycled, ViewGroup container) {
       final ImageView myImageView;
       if (recycled == null) {
         myImageView = (ImageView) inflater.inflate(R.layout.my_image_view, container, false);
       } else {
         myImageView = (ImageView) recycled;
       }

       String url = myUrls.get(position);

       Glide
         .with(myFragment)
         .load(url)
         .centerCrop()
         .placeholder(R.drawable.loading_spinner)
         .into(myImageView);

       return myImageView;
     }
     ```





5. [OKHttp使用指南](https://square.github.io/okhttp/)

   - **Include library**

     ```java
     implementation("com.squareup.okhttp3:okhttp:4.8.1")
     ```

   - Get a URL

     - 创建一个**OKHttpClinet**对象
     - 构造**Request**对象
     - 通过前面两步的对象构造Call对象
     - 通过Call.enqueue(Callback)方法提交异步请求

   - Post to a Server

     - 创建一个**OKHttpClinet**对象
     - 构造一个**RequestBody**对象
     - 构造**Request**对象
     - 通过前面三步的对象构造Call对象
     - 通过Call.enqueue(Callback)方法提交异步请求



6. EventBus 使用指南

   EventBus 是基于发布定于模式的，相对传统的接口回调和广播机制，它使得项目代码的耦合性降低，使用也相对简单。使用 EventBus 需要进行注册，但是记得在 Activity 销毁的时候进行反注册，否则会造成内存泄漏。普通事件要求发布者发布事件的时候，订阅者已经存在，否则接受不到事件，但是粘性事件的可以不要求已经存在订阅者，订阅者可以后面创建了之后接受传递的数据。



   - 普通事件

     ```java
     // 在获取到的数据之后进行数据的发送
     EventBus.getDefault().post(“message”);

     // 在需要接收数据的地方进行注册监听
     EventBus.getDefault().register(this);

     // 当组件销毁时需要进行反注册
     EventBus.getDefault().unregister(this);
     ```



   - 粘性事件

     ```java
     // 发送粘性事件EventBus.getDefault().postSticky(“message”);// 取消注册的时候有一点区别EventBus.getDefault().removeStickyEvent(LocationEventMessage.class);
     ```



7. 下拉刷新实现

- XML文件需要加入**SwipeRefreshLayout**控件

- 设置下拉监听事件

  ```java
  swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {            @Override            public void onRefresh() {            }        });
  ```

- 停止刷新

  ```
  swipeRefreshLayout.setRefreshing(false);
  ```



## 四、功能实现概述

### 1、 实现闪屏界面

- 使用 Handler 发送一个延时的任务，启动主界面，这样就实现了闪屏界面，具体代如下：

          new Handler().postDelayed(new Runnable() {        @Override        public void run() {            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);            startActivity(intent);        }    }, 5000);

- 避免出现白屏的现象

  - 在 style.xml 创建一个 style

    ```xml
    <style name="SplashTheme" parent="Theme.AppCompat.NoActionBar">    <item name="android:windowBackground">@mipmap/icon_flash</item></style>
    ```

  - 在 AndroidManifest.xml 中为这个Activity 指定一个theme

    ```xml
    android:theme="@style/SplashTheme"
    ```



###  2、定位功能实现

在 Application 中的 onCreate() ，获取到当前的经度纬度，然后通过 EventBus 发送粘性事件，把获取到的数据传递给展示天气信息的界面。这个是时候 WeatehrActivity 还有创建，所以需要发布粘性事件。配置方面根据高德地图提供的示例进行配置，下面看看它是如何发布事件：

```java
        mLocationListener = (location) -> {            if (location != null) {                if (location.getErrorCode() == 0) {                    //发送粘性事件,在展示天气数据的 WeatehrActivity 创建之后就会接收该数据，执行网络数据的获取                    EventBus.getDefault().postSticky(new LocationEventMessage(location));                } else {                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。                    Log.e("AmapError", "location Error, ErrCode:"                            + location.getErrorCode() + ", errInfo:"                            + location.getErrorInfo());                }            }        };
```



