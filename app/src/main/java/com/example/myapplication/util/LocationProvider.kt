package com.example.myapplication.util

import android.Manifest
import android.app.Activity
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.myapplication.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task


/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */
const val GOOGLE_SERVICE_RESOLVE_REQUEST = 101
const val LOCATION_SETTING_REQUEST = 111

class LocationProvider(
    private val context: Activity,
    private val lifecycle: Lifecycle
) : LocationCallback(),
    OnSuccessListener<Location>, LifecycleObserver {

    private var locationCallback: LocationUpdateCallback? = null

    private fun checkPlayServices(): Boolean {
        val resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GoogleApiAvailability.getInstance().isUserResolvableError(resultCode)) {

                GoogleApiAvailability.getInstance()
                    .getErrorDialog(context, resultCode, GOOGLE_SERVICE_RESOLVE_REQUEST).show()
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.play_service_error),
                    Toast.LENGTH_LONG
                ).show()
            }
            return false
        }
        return true
    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest().apply {
            priority =
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            interval = 10 * 1000
            fastestInterval = 1000
            smallestDisplacement = 1F
        }

    }


    private fun getLocationSettingsTask(locationRequest: LocationRequest): Task<LocationSettingsResponse> {
        val locationRequestBuilder = LocationSettingsRequest.Builder()
        locationRequestBuilder.addLocationRequest(locationRequest)
        val locationSettingRequest = locationRequestBuilder.build()
        val settingsClient = LocationServices.getSettingsClient(context)
        return settingsClient.checkLocationSettings(locationSettingRequest)

    }

    fun requestLocationUpdates(locationUpdateCallback: LocationUpdateCallback) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            locationCallback = locationUpdateCallback
            if (checkPlayServices()) {
                startLocationListening()
            }
        }
    }


    private fun getLastLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this)
    }


    private fun publishLocation(location: Location?) {
        if (location != null) {
            locationCallback?.onLocationUpdated(location)
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopLocationUpdates() {
        locationCallback = null
        LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(this)
    }

    private fun startLocationListening() {
        val locationRequest = getLocationRequest()

        val result = getLocationSettingsTask(locationRequest)
        result.addOnCompleteListener { task ->
            try {
                task.getResult(ApiException::class.java)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getLastLocation()
                    LocationServices.getFusedLocationProviderClient(context)
                        .requestLocationUpdates(locationRequest, this, null)
                }
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                        try {
                            val resolvable =
                                exception as ResolvableApiException
                            resolvable.startResolutionForResult(context, LOCATION_SETTING_REQUEST)
                        } catch (e: SendIntentException) { // Ignore the error.
                        } catch (e: ClassCastException) { // Ignore, should be an impossible error.
                        }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.device_error_location),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }


    override fun onLocationResult(locationResult: LocationResult?) {
        publishLocation(locationResult?.lastLocation)
    }

    override fun onSuccess(location: Location?) {
        publishLocation(location)
    }

}


interface LocationUpdateCallback {
    fun onLocationUpdated(location: Location)

}
