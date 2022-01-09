package com.ahmed.panoramviewerdemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.vr.sdk.widgets.pano.VrPanoramaView
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val vrPanoramaView: VrPanoramaView by lazy {
        findViewById(R.id.panoramaView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showWithResource()
//        showWithUrl()
    }

    private fun showWithResource() {
        val inputStream = assets.open("abcd.jpeg")
        val options: VrPanoramaView.Options = VrPanoramaView.Options()
        vrPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options)
        inputStream.close()
    }

    private fun showWithUrl() {
        val options: VrPanoramaView.Options = VrPanoramaView.Options()
        Thread {
            val bitmap = getBitmapFromUtl("https://pixexid.com/image/13r0c84-360-panorama-rincon-park")
            vrPanoramaView.post {
                vrPanoramaView.loadImageFromBitmap(bitmap, options)
            }
        }.start()
    }

    private fun getBitmapFromUtl(url: String): Bitmap? {
        return try {
            val connection = URL(url).openConnection()
            connection.doInput = true
            connection.connect()
            val inputStream = connection.getInputStream()
            val bitmap =  BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}