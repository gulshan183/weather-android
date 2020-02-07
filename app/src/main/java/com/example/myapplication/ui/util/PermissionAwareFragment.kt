package com.example.myapplication.ui.util

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */
private const val REQUEST_PERMISSION = 100
abstract class PermissionAwareFragment : Fragment() {
    abstract fun onPermissionGranted()
    abstract fun onPermissionNotGranted()

    protected fun requestPermissions(permissions: Array<String>) {
        val requestPermissions = permissions.filter {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) != PackageManager.PERMISSION_GRANTED
        }
        if (requestPermissions.isNotEmpty()) {
            requestPermissions(requestPermissions.toTypedArray(), REQUEST_PERMISSION)
        } else {
            onPermissionGranted()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                onPermissionGranted()
            } else {
                onPermissionNotGranted()
            }
        }
    }

}