package kr.ac.gachon.searchdogs.service

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kr.ac.gachon.searchdogs.R

class DetailDict : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_dict)

        val intent = getIntent()
        val recName = intent.getStringExtra("name")
        val recPsn = intent.getStringExtra("psn")
        val recBirth = intent.getStringExtra("birth")
        val recLife = intent.getStringExtra("life")


        val dogName = findViewById<TextView>(R.id.txt_Dt_name)
        val dogPsn = findViewById<TextView>(R.id.txt_Dt_psn)
        val dogBirth = findViewById<TextView>(R.id.txt_Dt_birth)
        val dogLife = findViewById<TextView>(R.id.txt_Dt_life)

        dogName.text = recName
        dogPsn.text = recPsn
        dogBirth.text = recBirth
        dogLife.text = recLife

    }

}
