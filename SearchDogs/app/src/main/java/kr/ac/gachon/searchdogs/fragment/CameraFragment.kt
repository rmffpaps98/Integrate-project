/**
 * 카메라 화면을 구성
 *
 * @since: 2019.10.01
 * @author: 류일웅
 */
package kr.ac.gachon.searchdogs.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_camera.*
import kr.ac.gachon.searchdogs.activity.DogImageActivity
import kr.ac.gachon.searchdogs.activity.MainActivity
import kr.ac.gachon.searchdogs.R
import kr.ac.gachon.searchdogs.service.Permission
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class CameraFragment : Fragment() {

    private val permission = Permission()

    // fotoapprat 라이브러리(카메라 라이브러리)를 사용을 위한 변수 선언
    private var fotoapparat: Fotoapparat? = null
    private var fotoapparatState : FotoapparatState? = null
    private var cameraStatus : CameraState? = null
    private var flashState: FlashState? = null

    // TODO: 사진 저장 경로를 어플 폴더 안으로 지정하기
    private val filename = FILE_NAME
    private val sd = Environment.getExternalStorageDirectory()
    private val dest = File(sd, filename)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)

        createFotoapparat(view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cameraStatus = CameraState.BACK
        flashState = FlashState.OFF
        fotoapparatState = FotoapparatState.OFF

        fragmentCamera_fab_camera.setOnClickListener {
            takePhoto()
        }

        fragmentCamera_fab_switchCamera.setOnClickListener {
            switchCamera()
        }

        fragmentCamera_fab_flash.setOnClickListener {
            changeFlashState()
        }
    }

    override fun onStop() {
        super.onStop()
        fotoapparat?.stop()
        FotoapparatState.OFF
    }

    override fun onStart() {
        super.onStart()
        if (permission.hasNoPermissions(activity!!)) {
            permission.requestPermission(activity!!)
        } else {
            fotoapparat?.start()
            fotoapparatState = FotoapparatState.ON
        }
    }

    override fun onResume() {
        super.onResume()
        if(!permission.hasNoPermissions(activity!!) && fotoapparatState == FotoapparatState.OFF) {
            val intent = Intent(activity!!, MainActivity::class.java)

            startActivity(intent)
            activity!!.finish()
        }
    }

    /**
     * ##################################################
     * Fotoapprat 라이브러리 초기화하는 기능
     *
     * @since: 2019.10.01
     * @author: 류일웅
     * @param: view
     * @return:
     * ##################################################
     */
    private fun createFotoapparat(view: View){
        val cameraView = view.findViewById<CameraView>(R.id.fragmentCamera_cameraView)

        fotoapparat = Fotoapparat(
            context = activity!!,
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

    /**
     * ##################################################
     * 사진 촬영 기능
     *
     * @since: 2019.10.01
     * @author: 류일웅
     * @param:
     * @return:
     * ##################################################
     */
    private fun takePhoto() {
        if (permission.hasNoPermissions(activity!!)) {
            permission.requestPermission(activity!!)
        }else{
            val photoResult = fotoapparat?.takePicture()
            var bitmap: Bitmap? = null

            photoResult?.toBitmap()?.transform { bitmapPhoto ->
                // 촬영한 사진을 bitmap으로 변환
                bitmap = bitmapPhoto.bitmap
            }?.whenAvailable {
                saveTakePhotoToBitmap(bitmap!!)
            }
        }
    }

    /**
     * ##################################################
     * bitmap으로 변환한 사진을 핸드폰에 저장하는 기능
     *
     * @since: 2019.10.01
     * @author: 류일웅
     * @param: bitmap
     * @return:
     * ##################################################
     */
    private fun saveTakePhotoToBitmap(bitmap: Bitmap) {
        try {
            val stream: OutputStream = FileOutputStream(dest)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val intent = Intent(activity!!, DogImageActivity::class.java)

        intent.putExtra(INTENT_CAMERA_TAG, dest.absolutePath)
        startActivity(intent)
    }

    /**
     * ##################################################
     * 사진촬영에서의 Flash 컨트롤 기능
     *
     * @since: 2019.10.01
     * @author: 류일웅
     * @param:
     * @return:
     * ##################################################
     */
    private fun changeFlashState() {
        fotoapparat?.updateConfiguration(
            CameraConfiguration(
                flashMode = if(flashState == FlashState.TORCH) off() else torch()
            )
        )

        if (flashState == FlashState.TORCH) {
            flashState = FlashState.OFF
        }
        else {
            flashState = FlashState.TORCH
        }
    }

    /**
     * ##################################################
     * 사진촬영에서의 앞, 뒤 화면 전환 기능
     *
     * @since: 2019.10.01
     * @author: 류일웅
     * @param:
     * @return:
     * ##################################################
     */
    private fun switchCamera() {
        fotoapparat?.switchTo(
            lensPosition =  if (cameraStatus == CameraState.BACK) front() else back(),
            cameraConfiguration = CameraConfiguration()
        )

        if (cameraStatus == CameraState.BACK) {
            cameraStatus = CameraState.FRONT
        }
        else {
            cameraStatus = CameraState.BACK
        }
    }

    companion object {
        const val INTENT_CAMERA_TAG = "CameraBitmap"
        const val FILE_NAME = "test22.jpeg"
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