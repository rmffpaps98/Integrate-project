/**
 * 품종 결과 화면창에서 버튼 구현하는 기능(테스트중)
 *
 * @author: 류일웅
 * @since: 2019.11.04
 */
package kr.ac.gachon.searchdogs.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kr.ac.gachon.searchdogs.R


class ButtonTestActivity : AppCompatActivity() {

    private lateinit var mImageView: ImageView
    private lateinit var mButton: Button

    private val top = 200
    private val left = 250

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_test)

        mImageView = findViewById(R.id.imgView_button_test)
        mButton = findViewById(R.id.btn_button_test)

        val dm = resources.displayMetrics
        val topSize = Math.round(top * dm.density)
        val leftSize = Math.round(left * dm.density)

        mButton.setPadding(leftSize, topSize, 0, 0)

        val param = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )

        param.leftMargin = left
        mButton.layoutParams = param

        Glide
            .with(this)
            .asBitmap()
            .load(R.drawable.test_dog)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(mImageView)
    }

}
