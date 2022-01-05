## PhoneShare

### 介绍
利用Android系统原生的API方法实现分享文字、图片、文件等功能，其中`Share`类为分享的工具类，通过调用`share`方法实现分享功能。

### 截图
![截图](./resources/screenshots/截图.jpg)

### 使用
#### 分享文字
```java
SystemShare.getInstance()
    .setText("文字内容")
    .setChooserTitle("标题")
    .setShareType(ShareBuilder.SHARE_TEXT)
    .build()
    .share(mActivity);
```
#### 分享单个文件
```java
Uri fileUri = UriUtil.getUriFromFile(this,"你的文件路径");
SystemShare.getInstance()
   .setChooserTitle("标题")
   .setShareType(SystemShare.SHARE_FILE)
   .setShareFiles(Arrays.asList(fileUri))
   .build()
   .share(this);
```

#### 分享多个文件
```java
Uri fileUri = UriUtils.getUriFromFile(this,"你的文件路径1");
Uri fileUri2 = UriUtils.getUriFromFile(this,"你的文件路径2");
ArrayList<Uri> uris = new ArrayList<>();
uris.add(fileUri);
uris.add(fileUri2);
SystemShare.getInstance()
    .setChooserTitle("标题")
    .setShareType(ShareBuilder.SHARE_MULTIPLE_FILES)
    .setShareFiles(uris)
    .build()
    .share(this);
```

#### 弹窗分享你的应用
```java
ShareModel model = AppFileUtil.getAloneApp(this, this.getPackageName());
Uri fileUri = UriUtil.getUriFromFile(this, model.getFilePath());
SystemShare.getInstance()
    .setContext(this)
    .setChooserTitle("标题")
    .setShareType(SystemShare.SHARE_APK_FILE)
    .setShareApkFile(fileUri)
    .showBlueTooth().build();

#### 直接蓝牙分享你的应用
```java
 SystemShare.getInstance()
    .setChooserTitle("标题")
    .setShareType(SystemShare.SHARE_APK_FILE)
    .setShareApkFile(fileUri)
    .build()
    .share(this);
### 注意事项
1. 运行项目时分享图片如遇崩溃请替换本地已有的图片路径；
2.实现基本的系统分享功能
3. `Share`类自动通过后缀名识别mimeType,无需用户指定；
