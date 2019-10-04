package kr.ac.gachon.searchdogs

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.log.loggers
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import io.fotoapparat.selector.front
import io.fotoapparat.selector.off
import io.fotoapparat.selector.torch
import io.fotoapparat.view.CameraView
import kotlinx.android.synthetic.main.activity_dict.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    var permissionList = arrayOf(   // 카메라, 저장소 권한 설정_j
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    var fotoapparat: Fotoapparat? = null
    val filename = "test.png"
    val sd = Environment.getExternalStorageDirectory()
    val dest = File(sd, filename)
    var fotoapparatState : FotoapparatState? = null
    var cameraStatus : CameraState? = null
    var flashState: FlashState? = null

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Gallery -> {

                    return@OnNavigationItemSelectedListener true
                }
                R.id.Camera -> {

                    return@OnNavigationItemSelectedListener true
                }
                R.id.Dictionary -> {
                    val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                    recyclerView_dict.addItemDecoration(divider)
                    recyclerView_dict.adapter = DictAdapter()
                    recyclerView_dict.layoutManager = LinearLayoutManager(this)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        checkPermission()   //권한설정_j

        createFotoapparat()

        cameraStatus = CameraState.BACK
        flashState = FlashState.OFF
        fotoapparatState = FotoapparatState.OFF

        fab_camera.setOnClickListener {
            takePhoto()
        }

        fab_switch_camera.setOnClickListener {
            switchCamera()
        }

        fab_flash.setOnClickListener {
            changeFlashState()
        }
    }

    fun checkPermission() { // 권한 설정 함수_j
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        for (pms: String in permissionList) {
            var permission_chk = checkSelfPermission(pms)

            if (permission_chk == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permissionList, 0)
                break
            }
        }
    }

    private fun createFotoapparat(){
        val cameraView = findViewById<CameraView>(R.id.camera_view)

        fotoapparat = Fotoapparat(
            context = this,
            view = cameraView,
            scaleType = ScaleType.CenterCrop,
            lensPosition = back(),
            logger = loggers(
                logcat()
            ),
            cameraErrorCallback = { error ->
                println("Recorder errors: $error")
            }
        )
    }

    private fun changeFlashState() {
        fotoapparat?.updateConfiguration(
            CameraConfiguration(
                flashMode = if(flashState == FlashState.TORCH) off() else torch()
            )
        )

        if(flashState == FlashState.TORCH) flashState = FlashState.OFF
        else flashState = FlashState.TORCH
    }

    private fun switchCamera() {
        fotoapparat?.switchTo(
            lensPosition =  if (cameraStatus == CameraState.BACK) front() else back(),
            cameraConfiguration = CameraConfiguration()
        )

        if(cameraStatus == CameraState.BACK) cameraStatus = CameraState.FRONT
        else cameraStatus = CameraState.BACK
    }

    private fun takePhoto() {
//        if (hasNoPermissions()) {
//            requestPermission()
//        }else{
//            fotoapparat
//                ?.takePicture()
//                ?.saveToFile(dest)
//        }
        checkPermission()

//        fotoapparat?.takePicture()?.saveToFile(dest)
        val photoResult = fotoapparat?.takePicture()

        // Asynchronously saves photo to file
        photoResult?.saveToFile(dest)
//
//        // Asynchronously converts photo to bitmap and returns the result on the main thread
//        photoResult
//            ?.toBitmap()
//            ?.whenAvailable { bitmapPhoto ->
//                val imageView = findViewById<ImageView>(R.id.take_photo)
//
//                imageView.setImageBitmap(bitmapPhoto?.bitmap)
//
//                val intent = Intent(this, TakePhoto::class.java)
//                startActivity(intent)
////                imageView.setRotation(bitmapPhoto?.rotationDegrees)
//            }
    }

    override fun onStart() {
        super.onStart()
//        if (hasNoPermissions()) {
//            requestPermission()
//        }else{
//            fotoapparat?.start()
//            fotoapparatState = FotoapparatState.ON
//        }
        checkPermission()

        fotoapparat?.start()
        fotoapparatState = FotoapparatState.ON
    }

//    private fun hasNoPermissions(): Boolean{
//        return ContextCompat.checkSelfPermission(this,
//            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
//            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//    }

//    fun requestPermission(){
//        ActivityCompat.requestPermissions(this, permissions,0)
//    }

    override fun onStop() {
        super.onStop()
        fotoapparat?.stop()
        FotoapparatState.OFF
    }

    override fun onResume() {
        super.onResume()
//        if(!hasNoPermissions() && fotoapparatState == FotoapparatState.OFF){
//            val intent = Intent(baseContext, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
        checkPermission()

        if (fotoapparatState == FotoapparatState.OFF) {
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}

enum class CameraState{

    FRONT, BACK

}

enum class FlashState{

    TORCH, OFF

}

enum class FotoapparatState{

    ON, OFF

}