package com.thuctran.downloadservice.service

import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.thuctran.downloadservice.MTask
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.net.URLConnection

@Suppress("UNREACHABLE_CODE")
class DownloadService : Service(), MTask.OnMainCallBack {
     val KEY_LINK: String = "KEY_LINK"

    companion object{
        val TAG:String = DownloadService::class.java.name
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG,"onCreate..")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG,"onStartCommand..")
        var link:String = intent!!.getStringExtra(KEY_LINK)!!       //intent lấy link truyền xuống để download
        startDownload(link)
        return super.onStartCommand(intent, flags, startId)

    }

    private fun startDownload(link: String) {
        var mTask:MTask = MTask(KEY_LINK, this)
        mTask.start(link)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"onDestroy..")
    }

    override fun execTask(key: String, data: Any?, task: MTask): Any? {

        //đọc ghi dữ liệu theo mảng byte
        var link:String = data as String
        var path: String = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.path      //đường dẫn
        path += File(link).name     //đường dẫn full

        var conn:URLConnection = URL(link).openConnection()     //tạo cổng kết nối
        var inPut:InputStream = conn.getInputStream()
        var outPut: OutputStream = FileOutputStream(path)

        var buff: ByteArray = byteArrayOf(1024.toByte())
        var len:Int = inPut.read(buff)
        while (len>0){
            outPut.write(buff, 0 ,len)
            len = inPut.read(buff)
        }
        outPut.close()
        inPut.close()
        return path
    }


    override fun completeTask(key: String, data: Any) {
        Toast.makeText(this,"download done!!",Toast.LENGTH_SHORT).show()
        Log.i("download", "link download: $data")
        stopSelf()      //kill khi download xong
    }
}