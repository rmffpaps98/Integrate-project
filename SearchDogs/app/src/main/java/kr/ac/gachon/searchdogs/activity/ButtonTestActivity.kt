/**
 * 품종 결과 화면창에서 버튼 구현하는 기능(테스트중)
 *
 * @author: 류일웅
 * @since: 2019.11.04
 */
package kr.ac.gachon.searchdogs.activity

import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.badge.BadgeUtils
import kr.ac.gachon.searchdogs.R
import java.util.*

class ButtonTestActivity : AppCompatActivity() {

    private val xDelta = 200
    private val toXDelta = 333
    private val yDelta = 750
    private val toYDelta = 1049

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_test)

        val image = findViewById<ImageView>(R.id.img_test)
        val button = findViewById<Button>(R.id.btn_test)

        image.setImageResource(R.drawable.test_dog)

        val ret = IntArray(4)
        val f = FloatArray(9)

        image.imageMatrix.getValues(f)

        val scaleX = f[Matrix.MSCALE_X]
        val scaleY = f[Matrix.MSCALE_Y]

        val d = image.drawable
        val origW = d.intrinsicWidth
        val origH = d.intrinsicHeight

        Log.d("CHECK", origW.toString() + " " + origH.toString())

        val actW = Math.round(origW * scaleX)
        val actH = Math.round(origH * scaleY)

        Log.d("CHECK", actW.toString() + " " + actH.toString())

        ret[2] = actW
        ret[3] = actH

        val imgViewW = image.width
        val imgViewH = image.height

        Log.d("CHECK", imgViewW.toString() + " " + imgViewH.toString())

        val top = ((imgViewH - actH) / 2).toInt()
        val left = ((imgViewW - actW) / 2).toInt()

        ret[0] = left
        ret[1] = top

        Log.d("CHECK", ret[0].toString() + " " + ret[1].toString() + " " + ret[2].toString() + " " + ret[3].toString() + " ")
    }

}
