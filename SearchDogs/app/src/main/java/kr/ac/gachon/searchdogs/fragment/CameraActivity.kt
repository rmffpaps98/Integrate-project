/*
package kr.ac.gachon.searchdogs.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kr.ac.gachon.searchdogs.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class CameraActivity : AppCompatActivity(), SurfaceHolder.Callback, Camera.PictureCallback {

    private var surfaceHolder: SurfaceHolder? = null
    private var camera: Camera? = null

    private var surfaceView: SurfaceView? = null

    private val neededPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        surfaceView = findViewById(R.id.surfaceView)
        val result = checkPermission()
        if (result) {
            setupSurfaceHolder()
        }
    }

    private fun checkPermission(): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            val permissionsNotGranted = ArrayList<String>()
            for (permission in neededPermissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNotGranted.add(permission)
                }
            }
            if (permissionsNotGranted.size > 0) {
                var shouldShowAlert = false
                for (permission in permissionsNotGranted) {
                    shouldShowAlert = ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                }

                val arr = arrayOfNulls<String>(permissionsNotGranted.size)
                val permissions = permissionsNotGranted.toArray(arr)
                if (shouldShowAlert) {
                    showPermissionAlert(permissions)
                } else {
                    requestPermissions(permissions)
                }
                return false
            }
        }
        return true
    }

    private fun showPermissionAlert(permissions: Array<String?>) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(R.string.permission_required)
        alertBuilder.setMessage(R.string.permission_message)
        alertBuilder.setPositiveButton(android.R.string.yes) { _, _ -> requestPermissions(permissions) }
        val alert = alertBuilder.create()
        alert.show()
    }

    private fun requestPermissions(permissions: Array<String?>) {
        ActivityCompat.requestPermissions(this@MainActivity, permissions, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                for (result in grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this@MainActivity, R.string.permission_warning, Toast.LENGTH_LONG).show()
                        setViewVisibility(R.id.showPermissionMsg, View.VISIBLE)
                        return
                    }
                }

                setupSurfaceHolder()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setViewVisibility(id: Int, visibility: Int) {
        val view = findViewById<View>(id)
        view!!.visibility = visibility
    }

    private fun setupSurfaceHolder() {
        setViewVisibility(R.id.startBtn, View.VISIBLE)
        setViewVisibility(R.id.surfaceView, View.VISIBLE)

        surfaceHolder = surfaceView!!.holder
        surfaceHolder!!.addCallback(this)
        setBtnClick()
    }

    private fun setBtnClick() {
        val startBtn = findViewById<Button>(R.id.startBtn)
        startBtn?.setOnClickListener { captureImage() }
    }

    private fun captureImage() {
        if (camera != null) {
            camera!!.takePicture(null, null, this)
        }
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        startCamera()
    }

    private fun startCamera() {
        camera = Camera.open()
        camera!!.setDisplayOrientation(90)
        try {
            camera!!.setPreviewDisplay(surfaceHolder)
            camera!!.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {
        resetCamera()
    }

    private fun resetCamera() {
        if (surfaceHolder!!.surface == null) {
            // Return if preview surface does not exist
            return
        }

        // Stop if preview surface is already running.
        camera!!.stopPreview()
        try {
            // Set preview display
            camera!!.setPreviewDisplay(surfaceHolder)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Start the camera preview...
        camera!!.startPreview()
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        releaseCamera()
    }

    private fun releaseCamera() {
        camera!!.stopPreview()
        camera!!.release()
        camera = null
    }

    override fun onPictureTaken(bytes: ByteArray, camera: Camera) {
        Log.d("CHECK", "onPictureTaken에 들어옴")
        var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
                Log.d("CHECK", "photoFile 완료")
            } catch (ex: IOException) {
                // Error occurred while creating the File
                ex.printStackTrace()
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                var photoURI = FileProvider.getUriForFile(this,
                    "kr.ac.gachon.searchdogs.fileprovider", photoFile)
                Log.d("CHECK", photoURI.toString())
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
//        saveImage(bytes)




//        var cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
//        val intent = Intent(this, Photo::class.java)
//        intent.putExtra("bitmap", bitmapImage)
//        startActivity(intent)




        resetCamera()
    }

    @Throws(IOException::class)
    private fun createImageFile() : File {
        // Create an image file name
//        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        val imageFileName = "JPEG_" + "test2" + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
 prefix

            ".jpg",
 suffix

            storageDir
 directory

        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath()
        Log.d("CHECK", mCurrentPhotoPath)
        Log.d("CHECK", image.toString())
        return image
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val outStream: FileOutputStream
        try {
//            val fileName = "TUTORIALWING_" + System.currentTimeMillis() + ".jpg"
            val fileName = "test.png"
            val file = File(Environment.getExternalStorageDirectory(), fileName)
            outStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, outStream)
            outStream.flush()
            outStream.close()
            Toast.makeText(this@MainActivity, "Picture Saved: $fileName", Toast.LENGTH_LONG).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun saveImage(bytes: ByteArray) {
        val outStream: FileOutputStream
        try {
//            val fileName = "TUTORIALWING_" + System.currentTimeMillis() + ".jpg"
            val fileName = "test.jpg"
            val file = File(Environment.getExternalStorageDirectory(), fileName)
            outStream = FileOutputStream(file)
            outStream.write(bytes)
            outStream.close()
            Toast.makeText(this@MainActivity, "Picture Saved: $fileName", Toast.LENGTH_LONG).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val REQUEST_CODE = 100
        const val REQUEST_TAKE_PHOTO = 1
        const val TAKE_PICTURE = 1
        var mCurrentPhotoPath: String = ""
    }

    private fun openGallery() {
        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE)
            }
            else{
                //permission already granted
                pickImageFromGallery()
            }
        }
        else{
            //system OS is < Marshmallow
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

}
*/
