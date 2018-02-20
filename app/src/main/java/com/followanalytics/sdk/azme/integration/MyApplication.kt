package com.followanalytics.sdk.azme.integration

import android.app.Application
import android.util.Log
import com.followanalytics.FollowAnalytics
import com.followapps.android.FollowApps

/**
 * Created by damiii on 13/02/2018.
 */
class MyApplication : Application() {


    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    override fun onCreate() {
        super.onCreate()
        FollowApps.setVerbose(true)
        FollowAnalytics.init(this)
    }

}