/**
 * 앨범에서 선택한 사진이나 사진촬영으로 촬영한 사진을 보여주는 화면
 *
 * @since: 2019.10.01
 * @author: 류일웅
 */
package kr.ac.gachon.searchdogs.activity

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import kr.ac.gachon.searchdogs.R
import kr.ac.gachon.searchdogs.fragment.CameraFragment.Companion.INTENT_CAMERA_TAG
import kr.ac.gachon.searchdogs.fragment.GalleryFragment.Companion.INTENT_GALLERY_TAG

class DogImageActivity : AppCompatActivity() {

    private var mCoordinateLayout: CoordinatorLayout? = null
    private var mImageView: ImageView? = null
    private var mImgButtonYes: ImageButton? = null
    private var mImgButtonNo: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dog_image_result)

        mCoordinateLayout = findViewById(R.id.dogImageResult_cl)
        mImageView = findViewById(R.id.dogImageResult_img)
        mImgButtonNo = findViewById(R.id.dogImageResult_imgBtn_no)
        mImgButtonYes = findViewById(R.id.dogImageResult_imgBtn_yes)

        if (intent.hasExtra(INTENT_GALLERY_TAG)) {
            showPickUpGalleryImage()
        }
        else if (intent.hasExtra(INTENT_CAMERA_TAG)) {
            showTakePhotoImage()
        }

        mImgButtonYes!!.setOnClickListener {
            showAlertDialog()
        }

        mImgButtonNo!!.setOnClickListener {
            finish()
        }
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
        val uri = Uri.parse(bitmapStringURI)
        val bitmap = BitmapFactory.decodeFile(uri.path)

        // TODO: 사진을 올바르게 보여주는 기능 구현하기
        mImageView?.setImageBitmap(bitmap)
    }

    /**
     * ##################################################
     * 확인 버튼을 눌렀을 시 강아지 품종을 확인할 것인지를
     * 물어보는 dialog 기능
     *
     * @since: 2019.10.01
     * @author: 류일웅
     * @param:
     * @return:
     * ##################################################
     */
    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(DIALOG_TITLE)
        builder.setMessage(DIALOG_MESSAGE)
        builder.setPositiveButton(DIALOG_POSITIVE_MESSAGE, ({ dialog, which ->
            // TODO: AI에 사진 전송하기
        }))
        builder.setNegativeButton(DIALOG_NEGATIVE_MESSAGE, ({ dialog, which ->
            dialog.dismiss()
        }))
        builder.show()
    }

    companion object {
        const val DIALOG_TITLE = "알림"
        const val DIALOG_MESSAGE = "정말로 품종을 확인하시겠습니까?"
        const val DIALOG_POSITIVE_MESSAGE = "예"
        const val DIALOG_NEGATIVE_MESSAGE = "아니오"
    }

}