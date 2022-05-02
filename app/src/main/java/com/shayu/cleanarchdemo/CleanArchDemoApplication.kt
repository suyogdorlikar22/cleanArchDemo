package com.shayu.cleanarchdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


/**
 *
 * @author SHYAM BORSE
 *
 *         © Copyright APAC Financial Services
 *
 *         File Name : CleanArchDemoApplication.java
 *
 *         Modification History
 *
 *         16-Oct-2020 Shyam Borse : Initial version
 *                               01-Jul-2021 First Last : Fix issue with getting reportee details method
 */
@HiltAndroidApp
class CleanArchDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}