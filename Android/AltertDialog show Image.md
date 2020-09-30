# Android AlertDialog display ImageView

## 方式一:使用xml

```java
AlertDialog dialog = new AlertDialog.Builder(this)
    .setTitle("title")
    .setView(R.layout.your_layout_id)// your layout contains a ImageView
    .setPositiveButton("positive", null)
    .show();
ImageView v = dialog.findViewById(R.id.your_image_view_id);
```

## 方式二：纯Java
```java
AppCompatImageView imageView = new AppCompatImageView(this);
imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
imageView.setImageResource(R.drawable.your_drawable);
new AlertDialog.Builder(this)
	.setTitle("title")
	.setView(imageView)// customView must be a ViewGroup
	.setPositiveButton("positive", null)
	.show();
```

## 记录

`AlertDialog#setView(v)`其中的View必须是一个ViewGroup，直接使用ImageView是不显示的

