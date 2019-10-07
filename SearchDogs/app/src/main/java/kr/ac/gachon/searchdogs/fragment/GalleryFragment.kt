package kr.ac.gachon.searchdogs.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_gallery.*
import kr.ac.gachon.searchdogs.MainActivity.Companion.isSetDogImage
import kr.ac.gachon.searchdogs.R

class GalleryFragment : Fragment() {

    private var mImageView: ImageView? = null
    private var mButtonView: Button? = null
    private var llSendCheckBottomSheet: LinearLayout? = null

    /**
     * TODO:
     *  1. 갤러리 메뉴를 다시 눌러도 이미지 유지하기
     *  2. 이미지 하단 짤리는 부분 수정하기
     * by 류일웅
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        mImageView = view.findViewById(R.id.img_dogGallery)
        mButtonView = view.findViewById(R.id.btn_gallery)
        llSendCheckBottomSheet = view.findViewById(R.id.ll_bnv_layout)

        view.findViewById<Button>(R.id.btn_gallery).setOnClickListener { view ->
            startGallery()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            val returnUri = data!!.data
            val bitmapImage = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, returnUri)

            img_dogGallery.setImageBitmap(bitmapImage)
            Log.d("CHECK", "이미지를 설정함")
            isSetDogImage = true
            mButtonView!!.visibility = View.INVISIBLE
        }
    }

    private fun startGallery() {
        val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        cameraIntent.type = "image/*"
        if (cameraIntent.resolveActivity(activity!!.packageManager) != null) {
            startActivityForResult(cameraIntent, 1000)
        }
    }

}