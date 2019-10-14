package kr.ac.gachon.searchdogs.service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Config
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cz.msebera.android.httpclient.client.methods.HttpPost
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient
import kr.ac.gachon.searchdogs.R
import kr.ac.gachon.searchdogs.activity.DogImageActivity
import java.io.*
import java.lang.Exception
import java.net.ConnectException
import java.net.Socket
import java.net.SocketException
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun connect() {
        Thread(object: Runnable {
            override fun run() {
                var responseString: String? = null

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

    fun pingYourTCPServerWith(message: String) {
        Thread(object: Runnable {
            override fun run() {
                try {
                    // val socket = Socket("121.169.158.111", 7070)
                    val socket = Socket(ipAddress, port)
                    socket.use {

                        var responseString: String? = null

                        try {
                            val out = PrintWriter(BufferedWriter(it.getOutputStream().writer()),true)
                            out.println(message)
                            out.flush()
                            val dis = DataInputStream(FileInputStream(File(Environment.getDataDirectory(), message)))
                            val dos = DataOutputStream(it.getOutputStream())
                            val buf = ByteArray(1024)
                            var totalReadBytes:Long = 0
                            val readBytes : Int = 0
                            val test : Int = dis.read(buf)
                            while (test > 0) {
                                dos.write(buf, 0, readBytes)
                                totalReadBytes += readBytes.toLong()
                            }
                            dos.close()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        it.getOutputStream().write(message.toByteArray())
                        val bufferReader = BufferedReader(InputStreamReader(it.inputStream))
                        while (true) {
                            val line = bufferReader.readLine() ?: break
                            responseString += line
                            if (line == "exit") break
                        }
                        Log.d("CHECK", responseString)
                        bufferReader.close()
                        it.close()
//                        return responseString!!
                    }
                } catch (he: UnknownHostException) {
//                    val exceptionString = "An exception occurred:\n ${he.printStackTrace()}"
//                    return exceptionString
                    Log.d("CHECK", he.toString())
                } catch (ioe: IOException) {
//                    val exceptionString = "An exception occurred:\n ${ioe.printStackTrace()}"
//                    return exceptionString
                    Log.d("CHECK", ioe.toString())
                } catch (ce: ConnectException) {
//                    val exceptionString = "An exception occurred:\n ${ce.printStackTrace()}"
//                    return exceptionString
                    Log.d("CHECK", ce.toString())
                } catch (se: SocketException) {
//                    val exceptionString = "An exception occurred:\n ${se.printStackTrace()}"
//                    return exceptionString
                    Log.d("CHECK", se.toString())
                } catch (ne : KotlinNullPointerException) {
//                    val exceptionString = "An exception occurred:\n ${ne.printStackTrace()}"
//                    return exceptionString
                    Log.d("CHECK", ne.toString())
                }
            }
        }).start()

    }

}
