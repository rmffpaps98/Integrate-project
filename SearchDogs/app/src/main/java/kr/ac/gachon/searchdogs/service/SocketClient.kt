package kr.ac.gachon.searchdogs.service

import android.graphics.Bitmap
import android.util.Config
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cz.msebera.android.httpclient.client.methods.HttpPost
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient
import kr.ac.gachon.searchdogs.activity.DogImageActivity
import java.io.IOException
import java.io.PrintWriter
import java.net.Socket
import java.net.UnknownHostException

class SocketClient : AppCompatActivity() {

    private val ipAddress = "192.168.0.16"
    private val port = 5000

    private val fileUploadUrl = "http://${ipAddress}:${port}/getNoteText"

    private val dialogTitle = "알림"
    private val dialogSuccess = "사진을 전송 했습니다"
    private val dialogUnknownHost = "서버에 접속할 수 없습니다"
    private val dialogError = "오류가 발생했습니다. 잠시 후에 시도 해 주세요"
    private val dialogPositiveButton = "예"

    fun connect() {
        Thread(object: Runnable {
            override fun run() {
                try {
                    val client = Socket(ipAddress, port)

                    // TODO: 사진 전송하기
//                    var printWriter = PrintWriter(client.getOutputStream(), true)
//                    printWriter.write("HI ")
//                    printWriter.flush()
//                    printWriter.close()

                    Log.d("CHECK", "Android: connected")

                    if (client.isConnected) {
                        runOnUiThread(object: Runnable {
                            override fun run() {
                                Log.d("CHECK", "Android: send completed")
                            }
                        })
                    }
                } catch (e2: UnknownHostException) {
                    runOnUiThread(object: Runnable {
                        override fun run() {
                            Log.d("CHECK", "Android: error occured UnknownHostException")
                        }
                    })
                } catch (e1: IOException) {
                    Log.d("CHECK", "IOException")
                    runOnUiThread(object: Runnable {
                        override fun run() {
                            Log.d("CHECK", "Android: error occured IOException")
                        }
                    })
                }
            }
        }).start()
    }

}
