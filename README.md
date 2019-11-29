# Android  身份证拍照 识别 文字识别 （百度）
[百度智能云文档](https://ai.baidu.com/docs#/OCR-Android-SDK/b3e93a0a)
[身份证识别DEMO](https://github.com/abbott2012/IdentityCardDiscern)
 **1. 为您自己的工程添加必要的权限**
如果您在自己的工程中集成SDK，请确保已经在工程AndroidManifest.xml文件中添加如下权限：
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```
|名称  | 用途 |
|--|--|
| INTERNET |  应用联网，发送请求数据至服务器，获得识别结果|
| CAMERA |  调用相机进行拍照（仅UI部分需要）|
| WRITE_EXTERNAL_STORAGE|  图片裁剪临时存储|
| READ_EXTERNAL_STORAGE |  图片裁剪临时存储|
 **3. 开发包添加** 

 1. 前往 [SDK下载页面](http://ai.baidu.com/sdk/#ocr)下载Android SDK压缩包。
 2. (必须)将下载包libs目录中的ocr-sdk.jar文件拷贝到工程libs目录中，并加入工程依赖。
 3. (必须)将libs目录下armeabi，arm64-v8a，armeabi-v7a，x86文件夹按需添加到android studio工程src/main/jniLibs目录中， eclipse用户默认为libs目录。
 4. (可选)如果需要使用UI模块，请在Android studio中以模块方式导入下载包中的ocr-ui文件夹。

 **4. Proguard配置** 
 如果您在自己的工程中集成SDK，请在Proguard配置文件中增加, 防止release发布时打包报错：

```
-keep class com.baidu.ocr.sdk.**{*;}
-dontwarn com.baidu.ocr.**
```

 **5. 初始化**
Api Key和 Secret Key在百度智能云上面注册的时候会有
```

OCR.getInstance(context).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
    @Override
    public void onResult(AccessToken result) {
        // 调用成功，返回AccessToken对象
        String token = result.getAccessToken();
    }
    @Override
    public void onError(OCRError error) {
        // 调用失败，返回OCRError子类SDKError对象
    }
}, getApplicationContext(), "您的应用AK", "您的应用SK");
```
 **6. 调起拍照  或者 照片**

```
  Intent intent = new Intent(RealNameAuthenticationActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        DataFileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
```
 **7. 获取拿到的照片**

```
     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = DataFileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);

                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }
    }

```
 **8. 解析身份证图片**
 

```
    /**
     * 解析身份证图片信息
     *
     * @param idCardSide 身份证正反面
     * @param filePath   图片路径
     */
    private void recIDCard(final String idCardSide, String filePath) {

        Log.i("charge ID card", idCardSide);

        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(40);

        OCR.getInstance(RealNameAuthenticationActivity.this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult idCardResult) {

                Log.i("charge ID card", String.valueOf(idCardResult));

                if (idCardResult != null) {

                    if (idCardSide.equals("back")) {
                        fileCardnationalStr = filePath;
                        File fileCardnational = new File(filePath);

                        if (fileCardnational.exists()) {

                            Bitmap bm = BitmapFactory.decodeFile(filePath);
                            idcardnationalbutton.setBackground(null);
                            idcardnationalbutton.setImageBitmap(bm);
                        }
                        String signDate = "";
                        String expiryDate = "";
                        String issueAuthority = "";
                        if (idCardResult.getSignDate() != null) {
                            signDate = idCardResult.getSignDate().toString();
                        }
                        if (idCardResult.getExpiryDate() != null) {
                            expiryDate = idCardResult.getExpiryDate().toString();
                        }
                        if (idCardResult.getIssueAuthority() != null) {
                            issueAuthority = idCardResult.getIssueAuthority().toString();
                        }

//                        mContent.setText("签发机关: " + issueAuthority + "\n\n" +
//                                "有效期限: " + signDate + "-" + expiryDate + "\n\n");
                    } else {
                        fileCardheadsStr = filePath;

                        File fileCardhead = new File(filePath);

                        if (fileCardhead.exists()) {

                            Bitmap bm = BitmapFactory.decodeFile(filePath);
                            idcardheadbutton.setBackground(null);
                            idcardheadbutton.setImageBitmap(bm);
                        }
                        String name = "";
                        String sex = "";
                        String nation = "";
                        String num = "";
                        String address = "";
                        if (idCardResult.getName() != null) {
                            name = idCardResult.getName().toString();
                        }
                        if (idCardResult.getGender() != null) {
                            sex = idCardResult.getGender().toString();
                        }
                        if (idCardResult.getEthnic() != null) {
                            nation = idCardResult.getEthnic().toString();
                        }
                        if (idCardResult.getIdNumber() != null) {
                            num = idCardResult.getIdNumber().toString();
                        }
                        if (idCardResult.getAddress() != null) {
                            address = idCardResult.getAddress().toString();
                        }
                        nameEt.setText(name);
                        cardEt.setText(num);
//                        mContent.setText("姓名: " + name + "\n\n" +
//                                "性别: " + sex + "\n\n" +
//                                "民族: " + nation + "\n\n" +
//                                "身份证号码: " + num + "\n\n" +
//                                "住址: " + address + "\n\n");
                    }


                }

            }

            @Override
            public void onError(OCRError ocrError) {

                Toast.makeText(RealNameAuthenticationActivity.this, "识别出错,请查看log错误代码", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onError: " + ocrError.getMessage());

            }
        });

    }```
