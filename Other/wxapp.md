## 20190429

### 文件说明

*.js 逻辑控制

*.json 不知道

*.wxml 类似于html，控制View显示

*.wxss 类似于css，样式控制

app.js 全局初始化

app.wxss 全局样式

### 居中

```css
.test-view{
	/*有直接子节点上的align-self值设置为一个组。 align-self属性设置项目在其包含块中的对齐方式。*/
  align-items: center;
  display: flex;
}

.test-text{
    /**激活flex 布局，必须写**/
  display: flex;
    /**属性定义了浏览器如何分配顺着父容器主轴的弹性元素之间及其周围的空间。**/
  justify-content: center; 
}
```



**getApp()**

返回App实例

