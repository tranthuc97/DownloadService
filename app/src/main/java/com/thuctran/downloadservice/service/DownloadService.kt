package com.thuctran.downloadservice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

@Suppress("UNREACHABLE_CODE")
class DownloadService : Service() {
    private val KEY_LINK: String = "KEY_LINK"

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
        //
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"onDestroy..")
    }
}