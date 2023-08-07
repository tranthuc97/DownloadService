package com.thuctran.downloadservice

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thuctran.downloadservice.databinding.ActivityMainBinding
import com.thuctran.downloadservice.service.DownloadService

class MainActivity : AppCompatActivity() {
    var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initViews()
    }

    private fun initViews() {
        binding!!.btDownload.setOnClickListener{ downloadFile() }
    }

    private fun downloadFile() {
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
            ), 101)
            return
        }

        var intent = Intent(this,DownloadService::class.java)
        intent.putExtra(DownloadService().KEY_LINK,binding!!.edtLink.text.toString())
        startService(intent)
    }
}