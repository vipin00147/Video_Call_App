package com.example.videocall.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.example.videocall.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var name : String
    private val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    private val requestcode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViewClickListener()
        askPermissions()
    }

    private fun setViewClickListener() {
        login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.login -> {
                checkValidation()
            }
        }
    }

    private fun checkValidation() {
        name = username.text.toString().trim()
        if(name.isNotEmpty()) {

            if (isPermissionGranted()) {
                startCallActivity()
            }
            else {
                askPermissions()
            }
        }
    }

    private fun startCallActivity() {
        val intent = Intent(this, CallActivity::class.java)
        intent.putExtra("username", name)
        startActivity(intent)
    }

    private fun askPermissions() {
        ActivityCompat.requestPermissions(this, permissions, requestcode)
    }

    private fun isPermissionGranted(): Boolean {

        permissions.forEach {
            if (ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED)
                return false
        }

        return true
    }
}