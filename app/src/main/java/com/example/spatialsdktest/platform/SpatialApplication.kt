package com.example.spatialsdktest.platform

import android.app.Application
import com.pico.spatial.ui.foundation.dsl.launch
import com.example.spatialsdktest.mainApp

class SpatialApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        launch(::mainApp)
    }
}
