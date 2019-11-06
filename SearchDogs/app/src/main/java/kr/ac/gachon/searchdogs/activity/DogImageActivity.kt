/**
 * 앨범에서 선택한 사진이나 사진촬영으로 촬영한 사진을 보여주는 화면
 *
 * @since: 2019.10.01
 * @author: 류일웅
 */
package kr.ac.gachon.searchdogs.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.database.*
import kr.ac.gachon.searchdogs.R
import kr.ac.gachon.searchdogs.fragment.CameraFragment.Companion.INTENT_CAMERA_TAG
import kr.ac.gachon.searchdogs.fragment.GalleryFragment.Companion.INTENT_GALLERY_TAG
import kr.ac.gachon.searchdogs.service.SocketClient
import java.io.FileOutputStream

class DogImageActivity : AppCompatActivity() {

    private var mCoordinateLayout: CoordinatorLayout? = null
    private var mImageView: ImageView? = null
    private var resultView : TextView? = null

    private val dialogTitle = "알림"
    private val dialogMessage = "정말로 품종을 확인하시겠습니까?"
    private val dialogPositiveButton = "예"
    private val dialogNegativeButton = "아니오"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_image)

        mCoordinateLayout = findViewById(R.id.dogImageResult_cl)
        mImageView = findViewById(R.id.dogImage_img)
        resultView = findViewById(R.id.LoadingText)

        if (intent.hasExtra(INTENT_GALLERY_TAG)) {
            showPickUpGalleryImage()
        }
        else if (intent.hasExtra(INTENT_CAMERA_TAG)) {
            showTakePhotoImage()
        }
        else if (intent.hasExtra("result")) {
            val data = intent.getStringExtra("result")
            println(data)

            resultView?.text = data
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * ##################################################
     * 갤러리에서 가져온 이미지를 보여주는 기능
     *
     * @since: 2019.10.01
     * @author: 류일웅
     * @param:
     * @return:
     * ##################################################
     */
    private fun showPickUpGalleryImage() {
        val bitmapStringURI = intent.getStringExtra(INTENT_GALLERY_TAG)
        val uri = Uri.parse(bitmapStringURI)
        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

        mImageView?.setImageBitmap(bitmap)
    }

    /**
     * ##################################################
     * 사진촬영한 이미지를 보여주는 기능
     *
     * @since: 2019.10.01
     * @author: 류일웅
     * @param:
     * @return:
     * ##################################################
     */
    private fun showTakePhotoImage() {
        val bitmapStringURI = intent.getStringExtra(INTENT_CAMERA_TAG)

        Glide
            .with(this)
            .asBitmap()
            .load(bitmapStringURI)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(mImageView!!)
    }

    /**
     * ##################################################
     * 사진을 올바르게 가져오는 기능
     * 현재 사용하지 않음(19.10.17)
     *
     * @since: 2019.10.07
     * @author: 류일웅
     * @param: photoPath, cameraStateData
     * @return: photoPath
     * ##################################################
     */
    private fun getRightAngleImage(photoPath: String): String {
        try {
            val ei = ExifInterface(photoPath)
            val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            var degree = 0F

            when (orientation) {
                ExifInterface.ORIENTATION_NORMAL -> degree = 0F
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90F
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180F
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270F
                else -> degree = 90F
            }

            return rotateImage(degree, photoPath)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return photoPath
    }

    /**
     * ##################################################
     * 사진을 회전하는 기능
     * 현재 사용하지 않음(19.10.17)
     *
     * @since: 2019.10.07
     * @author: 류일웅
     * @param: degree, imagePath, cameraStateData
     * @return: imagePath
     * ##################################################
     */
    private fun rotateImage(degree: Float, imagePath: String): String {
        var rotateDegree = degree

        if (rotateDegree <= 0) {
            return imagePath
        }

        try {
            var bitmap = BitmapFactory.decodeFile(imagePath)

            if (bitmap.width > bitmap.height) {
                val matrix = Matrix().apply { setRotate(rotateDegree) }

                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            }

            val fOut = FileOutputStream(imagePath)
            val out = FileOutputStream(imagePath)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)

            fOut.flush()
            fOut.close()

            bitmap.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return imagePath
    }

    /**
     * ##################################################
     * 확인 버튼을 눌렀을 시 강아지 품종을 확인할 것인지를
     * 물어보는 dialog 기능
     * 현재 사용하지 않음(19.11.05)
     *
     * @since: 2019.10.01
     * @author: 류일웅
     * @param:
     * @return:
     * ##################################################
     */
    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(dialogPositiveButton, ({ dialog, which ->
            // TODO: AI에 사진 전송하기
            val intent = Intent(this, SocketClient::class.java)
            startActivity(intent)
        }))
        builder.setNegativeButton(dialogNegativeButton, ({ dialog, which ->
            dialog.dismiss()
        }))
        builder.show()
    }

}