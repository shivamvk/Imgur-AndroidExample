package io.shivamvk.imgur_androidexample.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64.encodeToString
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.shivamvk.imgur_androidexample.databinding.ActivityUploadImageBinding
import io.shivamvk.imgur_androidexample.utils.Constants
import io.shivamvk.imgur_androidexample.utils.PermissionManager
import io.shivamvk.imgur_androidexample.utils.Permissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection


class UploadImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadImageBinding
    private lateinit var mCurrentPhotoPath: String
    private val REQUEST_IMAGE_CAPTURE: Int = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Upload Image"
        binding = ActivityUploadImageBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        cameraPermission()
        takePicture()
    }

    private fun takePicture(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    /*private fun uploadImageToImgur(image: Bitmap) {
        getBase64Image(image, complete = { base64Image ->
            GlobalScope.launch(Dispatchers.Default) {
                val url = URL("https://api.imgur.com/3/image")

                val boundary = "Boundary-${System.currentTimeMillis()}"

                val httpsURLConnection =
                    withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
                httpsURLConnection.setRequestProperty("Authorization", "Client-ID " + Constants.client_id)
                httpsURLConnection.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; boundary=$boundary"
                )

                httpsURLConnection.requestMethod = "POST"
                httpsURLConnection.doInput = true
                httpsURLConnection.doOutput = true

                var body = ""
                body += "--$boundary\r\n"
                body += "Content-Disposition:form-data; name=\"image\""
                body += "\r\n\r\n$base64Image\r\n"
                body += "--$boundary--\r\n"


                val outputStreamWriter = OutputStreamWriter(httpsURLConnection.outputStream)
                withContext(Dispatchers.IO) {
                    outputStreamWriter.write(body)
                    outputStreamWriter.flush()
                }
                val response = httpsURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                val jsonObject = JSONTokener(response).nextValue() as JSONObject
                val data = jsonObject.getJSONObject("data")
                val imgurUrl: String = data.getString("link")

            }
        })
    }

    private fun getBase64Image(image: Bitmap, complete: (String) -> Unit) {
        GlobalScope.launch {
            val outputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val b = outputStream.toByteArray()
            complete(Base64.encodeToString(b, Base64.DEFAULT))
        }
    }*/

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            setPicOnImageView(imageBitmap)
        }
    }

    fun checkForPermissions(){
        cameraPermission()
    }

    fun cameraPermission(){
        if(!PermissionManager().checkForPermission(applicationContext, Permissions().cameraPermission)){
            PermissionManager().requestForPermission(this, Permissions().cameraPermission)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay!
                } else {
                    // permission denied, boo!
                    Toast.makeText(applicationContext, "We need to access your camera to open it! ^_^", Toast.LENGTH_SHORT).show()
                    PermissionManager().requestForPermission(this, Permissions().cameraPermission)
                }
                return
            }
            101 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay!
                } else {
                    // permission denied, boo!
                    Toast.makeText(applicationContext, "We need to access your camera to open it! ^_^", Toast.LENGTH_SHORT).show()
                    PermissionManager().requestForPermission(this, Permissions().writeExternalStoragePermssion)
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun setPicOnImageView(imageBitmap: Bitmap){
        var displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        binding.ivUploadImage.setImageBitmap(imageBitmap)
    }
}
