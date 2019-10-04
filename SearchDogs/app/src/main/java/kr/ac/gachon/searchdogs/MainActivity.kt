package kr.ac.gachon.searchdogs

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dict.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var permissionList = arrayOf(   // 카메라, 저장소 권한 설정_j
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

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

        checkPermission()   //권한설정_j
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

}