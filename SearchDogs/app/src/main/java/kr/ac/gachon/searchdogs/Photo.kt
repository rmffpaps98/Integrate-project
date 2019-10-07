//package kr.ac.gachon.searchdogs
//
//import android.content.Context
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.Matrix
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.os.Environment
//import android.provider.MediaStore
//import android.util.Log
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.exifinterface.media.ExifInterface
//import com.bumptech.glide.Glide
//import com.squareup.picasso.Picasso
//import kotlinx.android.synthetic.main.activity_photo.*
//import kr.ac.gachon.searchdogs.MainActivity.Companion.mCurrentPhotoPath
//import java.io.File
//import java.io.FileOutputStream
//import java.io.IOException
//import java.io.InputStream
//import java.lang.Exception
//import java.lang.reflect.Array
//
//class Photo : AppCompatActivity() {
//
//    val fileName = "test.jpg"
//    val imgFile: File = File(Environment.getExternalStorageDirectory(), fileName)
//    val imageView: ImageView = findViewById(R.id.take_photo)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_photo)
//
////        val resultImage = (imgFile.absolutePath)
//
////        Picasso.get()
//////            .load("file:" + getRightAngleImage(imgFile.absolutePath))
//////            .into(imageView)
////        imageView.setImageBitmap()
////        Glide.with(this).load(resultImage).into(imageView)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        try {
//            Log.d("CHECK", "onActivityResult에 들어옴")
//            when (requestCode) {
//                0 -> {
//                    if (resultCode == RESULT_OK) {
//                        Log.d("CHECK", "RESULT_OK에 들어옴")
//                        var file = File(mCurrentPhotoPath)
//                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, Uri.fromFile(file))
//                        Log.d("CHECK", bitmap.toString())
//                        if (bitmap != null) {
//                            imageView.setImageBitmap(bitmap)
//                        }
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//}
