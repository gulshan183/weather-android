package com.example.myapplication.ui.util

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */

/**
 * This class extends [Fragment] and make your fragment permissions aware.
 * It handles when to show runtime permissions and the cases of success or failure.
 */
private const val REQUEST_PERMISSION = 100
abstract class PermissionAwareFragment : Fragment() {
    abstract fun onPermissionGranted()
    abstract fun onPermissionNotGranted()

    /**
     * This func takes the permissions required and handle them gracefully
     *
     * @param permissions [Array] of permissions required to be fulfilled
     */
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