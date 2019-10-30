package com.uis.groupadater.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import kotlinx.android.synthetic.main.ui_demo_main.*
import okhttp3.OkHttpClient
import java.math.BigDecimal
import java.math.RoundingMode
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManagerFactory

class DemoUi: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFresco()
        FLog.setMinimumLoggingLevel(FLog.ASSERT)
        setContentView(R.layout.ui_demo_main)
        //dv_image.setImageURI("http://www.baidu.com/img/bd_logo1.png")
        //dv_image.setImageURI("https://img22.st.iblimg.com/market-2/images/activity/1688657991.jpg")
        bt_single.setOnClickListener{
            val intent = Intent(this,SingleRecyclerUi::class.java)
            startActivity(intent)
        }

        bt_double.setOnClickListener{
            val intent = Intent(this,DoubleRecyclerUi::class.java)
            startActivity(intent)
        }

        bt_viewpager.setOnClickListener{
            val intent = Intent(this,ViewpagerRecyclerUi::class.java)
            startActivity(intent)
        }
    }

    fun initFresco() {
        val builder = OkHttpClient.Builder()
        val configBuilder = OkHttpImagePipelineConfigFactory.newBuilder(applicationContext, builder.build())
        Fresco.initialize(applicationContext,configBuilder.build())
    }
}