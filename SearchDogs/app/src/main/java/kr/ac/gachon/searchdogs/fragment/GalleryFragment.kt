/**
 * 갤러리 화면을 구성
 *
 * @since: 2019.10.01
 * @author: 류일웅
 */
package kr.ac.gachon.searchdogs.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kr.ac.gachon.searchdogs.activity.DogImageActivity
import kr.ac.gachon.searchdogs.R
import kr.ac.gachon.searchdogs.service.Permission

class GalleryFragment : Fragment() {

    private val permission = Permission()

    private var mButtonView: Button? = null

    private val imagePickCode = 1000
    private val intentGalleryType = "image/*"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        mButtonView = view.findViewById(R.id.fragmentGallery__btn_galleryUpload)

        view.findViewById<Button>(R.id.fragmentGallery__btn_galleryUpload).setOnClickListener {
            startGallery()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * ##################################################
     * 스마트폰의 갤러리를 실행하는 기능
     *
     * @since: 2019.10.01
     * @author: 류일웅
     * @param:
     * @return:
     * ##################################################
     */
    private fun startGallery() {
        if (permission.hasNoPermissions(activity!!)) {
            permission.requestPermission(activity!!)
        }else{
            val galleryIntent = Intent(Intent.ACTION_PICK)

            galleryIntent.type = intentGalleryType

            // fragment가 MainActivity에 존재하는지 확인
            if (isAdded) {
                startActivityForResult(galleryIntent, imagePickCode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == imagePickCode && resultCode == Activity.RESULT_OK) {
            val returnUri = data?.data.toString()

            val intent = Intent(activity!!, DogImageActivity::class.java)

            intent.putExtra(INTENT_GALLERY_TAG, returnUri)

            startActivity(intent)
        }
        
        // 19.10.31 소켓통신 파일전송 기능 - 이정묵
        fun SendImage() {
            val filePath = data?.data.toString()
            val uri = Uri.parse("$filePath")
            val path = getRealPathFromURI(uri)
            val filename = path

            val content = Files.readAllBytes(Paths.get(filename))

            val ip = "121.169.158.111" // 192.168.0.0
            val port = 7070 // 여기에 port를 입력해주세요

            val socket = Socket(ip, port) // ip와 port를 입력하여 클라이언트 소켓을 만듭니다.
            val outStream = socket.outputStream // outputStream - 데이터를 내보내는 스트림입니다.
            val inStream = socket.inputStream // inputStream - 데이터를 받는 스트림입니다.

            val data = content // 데이터는 byteArray로 변경 할 수 있어야 합니다.
            println(data.size)
            outStream.write(data) // toByteArray() 파라미터로 charset를 설정할 수 있습니다. 기본값 utf-8

            val available = inStream.available() // 데이터가 있으면 데이터의 사이즈 없다면 -1을 반환합니다.
            if (available > 0) {
                val dataArr = ByteArray(available) // 사이즈에 맞게 byte array를 만듭니다.
                outStream.write(dataArr) // byte array에 데이터를 씁니다.
                val data = String(dataArr) // byte array의 데이터를 통해 String을 만듭니다.
                println("data : ${data}")
            }
        }
        SendImage()
    }
    
    // 19.10.31 갤러리 파일 절대 경로값 찾아주는 기능 - 이정묵
    fun getRealPathFromURI(contentUri:Uri):String {
        var res  = ""
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor  = activity?.contentResolver?.query(contentUri, proj, null, null, null)
        if (cursor!!.moveToFirst())
        {
            val column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

    companion object {
        const val INTENT_GALLERY_TAG = "GalleryBitmap"
    }

}
