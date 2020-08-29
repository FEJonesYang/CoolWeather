# Cool-Weather



## 一、功能需求及技术可行性分析 

### 1、具备的功能

- 可以罗列出全国所有的省市县
- 可以查看全国任意城市的天气信息
- 可以自由地切换城市，去查看其他城市地天气
- 提供手动更新以及后台自动更新天气的功能



### 2、技术可行性分析

1. 如何获取天气数据的接口
   - 省市县数据接口：http://guolin.tech/api/china
   - 和风天气数据接口

2. 编写步骤
   - 基本配置
   - 创建数据库和表
   - 遍历全国省市县数据
   - 显示天气数据
   - 编写天气界面
   - 将天气显示到界面上
   - 获取必应天气壁纸
   - 手动更新天气
   - 手动切换城市
   - 后台自动更新天气
   - 扩展功能【有空再写】
   - 修改图标和名称



## 二、技术栈以及收获

1. 技术栈

- 图片加载框架：Glide
- JSON解析框架：Gson
- 数据库框架：Litepal
- 网络访问框架：OKHttp3

2. 收获

- 对上述框架在项目中是如何使用的？
- 一个app的完整开发流程？



## 三、框架以及技术使用概要

1. 项目结构图

![项目系统结构图](https://tva1.sinaimg.cn/large/007S8ZIlly1gi7j4n0k05j30lw0msmzf.jpg)



2. [LitePal使用指南](https://github.com/guolindev/LitePal)

   - **Include library**

     ```java
     implementation 'org.litepal.guolindev:core:3.2.1'
     ```

   - **Configure litepal.xml**

   - **Configure LitePalApplication**

     ```java
     android:name="org.litepal.LitePalApplication"
     ```

   - **Create tables**
     - 表类继承**LitePalSupport** 
     - 在litepal.xml中添加映射关系

   - 增、删、查、改



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



6. 下拉刷新实现

   - XML文件需要加入**SwipeRefreshLayout**控件

   - 设置下拉监听事件

     ```java
     swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                 @Override
                 public void onRefresh() {
     
                 }
             });
     ```

   - 停止刷新

     ```
     swipeRefreshLayout.setRefreshing(false);
     ```

     







