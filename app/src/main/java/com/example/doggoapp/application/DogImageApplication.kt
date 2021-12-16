package com.example.doggoapp.application

import android.app.Application
import com.example.doggoapp.data.ImageDatabase

class ImageApplication : Application() {
    val database: ImageDatabase by lazy {
        ImageDatabase.getDatabase(this)
    }
}