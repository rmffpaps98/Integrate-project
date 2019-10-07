package kr.ac.gachon.searchdogs

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.ac.gachon.searchdogs.camera.CameraFragment
import kr.ac.gachon.searchdogs.fragment.BottomSheetFragment
import kr.ac.gachon.searchdogs.fragment.GalleryFragment


class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomSheetFragment.BottomSheetListener {

    private var mTextView: TextView? = null

    private var bottomNavigationViewHeight: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextView = findViewById(R.id.txt_check_type_title)

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        val navView = findViewById<View>(R.id.bnv_main) as BottomNavigationView
        navView.setOnNavigationItemSelectedListener(this)

        /**
        * TODO: 카메라 기능 완성되면
            navView.selectedItemId = R.id.camera 로 바꿔야함
        * by 류일웅
         */
        navView.selectedItemId = R.id.Camera
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.Gallery -> {
                val galleryFragment = GalleryFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_main, galleryFragment)
                    .commit()
            }
            R.id.Camera -> {
                val cameraFragment = CameraFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_main, cameraFragment)
                    .commit()
            }
            R.id.Dictionary -> {
                /**
                * TODO: 레이아웃 만든 후에 적용할 예정
                * by 류일웅
####################################################################################################
                val dictionaryFragment = DictionaryFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame, dictionaryFragment)
                    .commit()
            }
####################################################################################################
                 */

                /**
                * TODO: 나중에 따로 파일로 빼서 만들어야 할듯
                * by 류일웅
####################################################################################################
                val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                recyclerView_dict.addItemDecoration(divider)
                recyclerView_dict.adapter = DictAdapter()
                recyclerView_dict.layoutManager = LinearLayoutManager(this)
####################################################################################################
                 */
            }
        }
        return true
    }

    override fun onButtonClicked(text: String) {
        mTextView!!.text = text
    }

    override fun onStart() {
        super.onStart()

        if (isSetDogImage) {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetFragment()

        bottomSheetDialog.show(supportFragmentManager, "TAG")
    }

    companion object {
        var isSetDogImage = false
    }
}

// ======================================================================
// ======================================================================
/*
전에 만들었던거 백업
 */
/*
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

    //        val photoResult = fotoapparat?.takePicture()
    //        var bmp = photoResult as Bitmap
    //        take_photo.setImageBitmap(bmp)

    //        val intent = Intent(this, TakePhoto::class.java)
    //        startActivity(intent)

    //        fotoapparat?.takePicture()?.saveToFile(dest)

    //        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    //        startActivityForResult(intent, 123)

    //        val photoResult = fotoapparat?.takePicture()
    //        val intent = Intent(this, TakePhoto::class.java)
    //        val dest2 = File(getExternalFilesDir("photos"), "photo.jpg")
    //
    //        // Asynchronously saves photo to file
    //        photoResult?.saveToFile(dest2)
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

        val REQUEST_IMAGE_CAPTURE = 1

        private fun takePictureIntent() {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    CAMERA_REQUEST_CODE -> {

                        val extras = data?.getExtras()
                        val imageBitmap = extras?.get("data") as Bitmap
                        image.setImageBitmap(imageBitmap)

                    }
                }
            }
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
 */
// ======================================================================
// ======================================================================

// ======================================================================
// ======================================================================
/*
실행은 되나 원하는 방법은 아님
 */
/*
    lateinit var imageView: ImageView
    lateinit var captureButton: Button

    val REQUEST_IMAGE_CAPTURE = 1


    private val PERMISSION_REQUEST_CODE: Int = 101

    private var mCurrentPhotoPath: String? = null

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

        imageView = findViewById(R.id.image_view)
        captureButton = findViewById(R.id.btn_capture)

        if (checkPersmission()) takePicture() else requestPermission()
//        captureButton.setOnClickListener(View.OnClickListener {
//            if (checkPersmission()) takePicture() else requestPermission()
//        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    &&grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    takePicture()

                } else {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
                }
                return
            }

            else -> {

            }
        }
    }

    private fun takePicture() {

        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = createFile()

        val uri: Uri = FileProvider.getUriForFile(
            this,
            "kr.ac.gachon.searchdogs.fileprovider",
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            //To get the File for further usage
            val auxFile = File(mCurrentPhotoPath)


            var bitmap : Bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
            imageView.setImageBitmap(bitmap)

        }
    }

    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA),
            PERMISSION_REQUEST_CODE)
    }

    @Throws(IOException::class)
    private fun createFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }
 */
// ======================================================================
// ======================================================================