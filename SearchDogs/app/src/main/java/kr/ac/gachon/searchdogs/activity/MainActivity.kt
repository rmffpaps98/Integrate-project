/**
 * 메인 화면
 *
 * @since: 2019.10.01
 * @author: 이정묵, 류일웅
 */
package kr.ac.gachon.searchdogs.activity

import android.os.Bundle
import android.os.StrictMode
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.ac.gachon.searchdogs.R
import kr.ac.gachon.searchdogs.fragment.CameraFragment
import kr.ac.gachon.searchdogs.fragment.DictFragment
import kr.ac.gachon.searchdogs.fragment.GalleryFragment


class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        val navView = findViewById<BottomNavigationView>(R.id.main_bnv)
        navView.setOnNavigationItemSelectedListener(this)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // 앱 실행시 초기 화면 설정
        navView.selectedItemId = R.id.Camera
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.Gallery -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fl, GalleryFragment())
                    .commit()
            }
            R.id.Camera -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fl, CameraFragment())
                    .commit()
            }
            R.id.Dictionary -> {
                supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fl, DictFragment())
                .commit()
            }


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
        return true
    }

}
