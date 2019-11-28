package com.demo.identitycarddiscern

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.baidu.ocr.sdk.exception.OCRError
import com.baidu.ocr.sdk.model.AccessToken
import com.baidu.ocr.sdk.OnResultListener
import com.baidu.ocr.sdk.OCR
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

import com.baidu.ocr.sdk.model.IDCardParams
import com.baidu.ocr.sdk.model.IDCardResult
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

private val REQUEST_CODE_CAMERA = 102
var fileCardheadsStr = ""
var fileCardnationalStr = ""

class RealNameAuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        var  idcardheadbutton = findViewById(R.id.idcardheadbutton) as? ImageView
//        var  idcardnationalbutton = findViewById(R.id.idcardheadbutton) as? ImageView
//        var  name_et = findViewById(R.id.name_et) as? EditText
//        var  card_et = findViewById(R.id.card_et) as? EditText
//        OCR.getInstance(this).initAccessTokenWithAkSk(object : OnResultListener<AccessToken> {
//            override fun onResult(result: AccessToken) {
//                // 调用成功，返回AccessToken对象
//                val token = result.accessToken
//            }
//
//            override fun onError(error: OCRError) {
//                // 调用失败，返回OCRError子类SDKError对象
//            }
//        }, applicationContext, "您的应用AK", "您的应用SK")

    }
//
//
//        @OnClick(R.id.tv_sure, R.id.idcardheadbutton, R.id.idcardnationalbutton)
//    fun onViewClicked(view: View) {
//        when (view.id) {
//            R.id.idcardheadbutton -> {
//                var intent = Intent(this@RealNameAuthenticationActivity, CameraActivity::class.java)
//                intent.putExtra(
//                    CameraActivity.KEY_OUTPUT_FILE_PATH,
//                    DataFileUtil.getSaveFile(application).absolutePath
//                )
//                intent.putExtra(
//                    CameraActivity.KEY_CONTENT_TYPE,
//                    CameraActivity.CONTENT_TYPE_ID_CARD_FRONT
//                )
//                startActivityForResult(intent, REQUEST_CODE_CAMERA)
//            }
//            R.id.idcardnationalbutton -> {
//                intent = Intent(this@RealNameAuthenticationActivity, CameraActivity::class.java)
//                intent.putExtra(
//                    CameraActivity.KEY_OUTPUT_FILE_PATH,
//                    DataFileUtil.getSaveFile(application).absolutePath
//                )
//                intent.putExtra(
//                    CameraActivity.KEY_CONTENT_TYPE,
//                    CameraActivity.CONTENT_TYPE_ID_CARD_BACK
//                )
//                startActivityForResult(intent, REQUEST_CODE_CAMERA)
//            }
//        }
//    }
//
//
//    /**
//     * 解析身份证图片
//     *
//     * @param idCardSide 身份证正反面
//     * @param filePath   图片路径
//     */
//    private fun recIDCard(idCardSide: String, filePath: String) {
//
//        Log.i("charge ID card", idCardSide)
//
//        val param = IDCardParams()
//        param.imageFile = File(filePath)
//        // 设置身份证正反面
//        param.idCardSide = idCardSide
//        // 设置方向检测
//        param.isDetectDirection = true
//        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
//        param.imageQuality = 40
//
//        OCR.getInstance(this@RealNameAuthenticationActivity)
//            .recognizeIDCard(param, object : OnResultListener<IDCardResult> {
//                @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
//                override fun onResult(idCardResult: IDCardResult?) {
//
//                    Log.i("charge ID card", idCardResult.toString())
//
//                    if (idCardResult != null) {
//
//                        if (idCardSide == "back") {
//                            fileCardnationalStr = filePath
//                            val fileCardnational = File(filePath)
//
//                            if (fileCardnational.exists()) {
//
//                                val bm = BitmapFactory.decodeFile(filePath)
//                                idcardnationalbutton.setBackground(null)
//                                idcardnationalbutton.setImageBitmap(bm)
//                            }
//                            var signDate = ""
//                            var expiryDate = ""
//                            var issueAuthority = ""
//                            if (idCardResult.signDate != null) {
//                                signDate = idCardResult.signDate.toString()
//                            }
//                            if (idCardResult.expiryDate != null) {
//                                expiryDate = idCardResult.expiryDate.toString()
//                            }
//                            if (idCardResult.issueAuthority != null) {
//                                issueAuthority = idCardResult.issueAuthority.toString()
//                            }
//
//                            //                        mContent.setText("签发机关: " + issueAuthority + "\n\n" +
//                            //                                "有效期限: " + signDate + "-" + expiryDate + "\n\n");
//                        } else {
//                            fileCardheadsStr = filePath
//
//                            val fileCardhead = File(filePath)
//
//                            if (fileCardhead.exists()) {
//
//                                val bm = BitmapFactory.decodeFile(filePath)
//                                idcardheadbutton.setBackground(null)
//                                idcardheadbutton.setImageBitmap(bm)
//                            }
//                            var name = ""
//                            var sex = ""
//                            var nation = ""
//                            var num = ""
//                            var address = ""
//                            if (idCardResult.name != null) {
//                                name = idCardResult.name.toString()
//                            }
//                            if (idCardResult.gender != null) {
//                                sex = idCardResult.gender.toString()
//                            }
//                            if (idCardResult.ethnic != null) {
//                                nation = idCardResult.ethnic.toString()
//                            }
//                            if (idCardResult.idNumber != null) {
//                                num = idCardResult.idNumber.toString()
//                            }
//                            if (idCardResult.address != null) {
//                                address = idCardResult.address.toString()
//                            }
//                            name_et.setText(name)
//                            card_et.setText(num)
//                            //                        mContent.setText("姓名: " + name + "\n\n" +
//                            //                                "性别: " + sex + "\n\n" +
//                            //                                "民族: " + nation + "\n\n" +
//                            //                                "身份证号码: " + num + "\n\n" +
//                            //                                "住址: " + address + "\n\n");
//                        }
//
//
//                    }
//
//                }
//
//                override fun onError(ocrError: OCRError) {
//
//                    Toast.makeText(
//                        this@RealNameAuthenticationActivity,
//                        "识别出错,请查看log错误代码",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    Log.d("MainActivity", "onError: " + ocrError.message)
//
//                }
//            })
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
//            if (data != null) {
//                val contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE)
//                val filePath = DataFileUtil.getSaveFile(applicationContext).absolutePath
//                if (!TextUtils.isEmpty(contentType)) {
//                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT == contentType) {
//                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath)
//
//                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK == contentType) {
//                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath)
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        // 释放内存资源
//        OCR.getInstance(this).release()
//    }
}

