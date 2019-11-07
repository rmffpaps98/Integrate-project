/**
 * 품종 결과 화면창에서 버튼 구현하는 기능(테스트중)
 *
 * @author: 류일웅
 * @since: 2019.11.04
 */
package kr.ac.gachon.searchdogs.activity

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kr.ac.gachon.searchdogs.R

class ButtonTestActivity : AppCompatActivity() {

    private val xDelta = 200
    private val toXDelta = 333
    private val yDelta = 750
    private val toYDelta = 1049

    private var mImageView: ImageView? = null
    private var mButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_test)

        mImageView = findViewById(R.id.img_test)
        mButton = findViewById(R.id.btn_test)

//        mImageView!!.setImageResource(R.drawable.test_dog)
        Glide
            .with(this)
            .asBitmap()
            .load(R.drawable.test_dog)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(mImageView!!)

        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(xDelta,toXDelta,0,0)

        mButton!!.setPadding(550, 716, 0, 0)
        mButton!!.layoutParams = params
    }

}
