/*
package kr.ac.gachon.searchdogs.service

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kr.ac.gachon.searchdogs.R

class Permission {

    private val neededPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private fun checkPermission(activity: Activity): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT

        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            val permissionsNotGranted = ArrayList<String>()

            for (permission in neededPermissions) {
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNotGranted.add(permission)
                }
            }
            if (permissionsNotGranted.size > 0) {
                var shouldShowAlert = false

                for (permission in permissionsNotGranted) {
                    shouldShowAlert = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
                }

                val arr = arrayOfNulls<String>(permissionsNotGranted.size)
                val permissions = permissionsNotGranted.toArray(arr)

                if (shouldShowAlert) {
                    showPermissionAlert(permissions, activity)
                } else {
                    requestPermissions(permissions, activity)
                }
                return false
            }
        }
        return true
    }

    private fun showPermissionAlert(permissions: Array<String?>, activity: Activity) {
        val alertBuilder = AlertDialog.Builder(activity)

        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(R.string.permission_required)
        alertBuilder.setMessage(R.string.permission_message)
        alertBuilder.setPositiveButton(android.R.string.yes) { _, _ -> requestPermissions(permissions, activity) }

        val alert = alertBuilder.create()

        alert.show()
    }

    private fun requestPermissions(permissions: Array<String?>, activity: Activity) {
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                for (result in grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this@MainActivity, R.string.permission_warning, Toast.LENGTH_LONG).show()
                        setViewVisibility(R.id.showPermissionMsg, View.VISIBLE)
                        return
                    }
                }

                setupSurfaceHolder()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
*/
