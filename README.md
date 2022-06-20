### ktor server and client demo
#### 介绍
> 将图床当网盘使用
> 原理:将文件信息编码为图片，上传图床，也可以加密字符串(用于私密通信)
#### api
> 上传文件：
> post localhost:8080/upload  body:form-data file:your_file
> 返回信息
> {
"code": 0,
"data": {
"image_height": 51,
"image_url": "http://i0.hdslb.com/bfs/album/*****.png",
"image_width": 1439
},
"message": "success"
}
> 下载文件
> get localhost:8080/data?url=http://i0.hdslb.com/bfs/album/*****.png
> 返回信息
> 返回文件流
#### 注意
> 改项目仅用于学习ktor,请勿用于非法用途,
> 默认使用bilibili的图床，修改SessionData中sessoionData即可使用或者启动时传入参数eg:java -jar app.jar your_sessiondata,(f12查看cookie填入)
#### todo_list
> 提供大文件上传功能(将文件分割成多个节点上传)
> 使用协程优化