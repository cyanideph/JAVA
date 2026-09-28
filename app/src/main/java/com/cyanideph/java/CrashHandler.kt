package com.cyanideph.java

import android.app.Application
import android.content.Intent
import android.os.Process
import java.io.PrintWriter
import java.io.StringWriter

class CrashHandler : Application() {
    override fun onCreate() {
        super.onCreate()

        val previousHandler = Thread.getDefaultUncaughtExceptionHandler()

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                val stack = StringWriter().also { writer ->
                    throwable.printStackTrace(PrintWriter(writer))
                }.toString()

                getSharedPreferences(PREFS, MODE_PRIVATE)
                    .edit()
                    .putString(KEY_CRASH, stack)
                    .putString(KEY_THREAD, thread.name)
                    .apply()

                val intent = Intent(this, CrashActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                startActivity(intent)
                Thread.sleep(350)
            } catch (_: Throwable) {
                previousHandler?.uncaughtException(thread, throwable)
                return@setDefaultUncaughtExceptionHandler
            }

            Process.killProcess(Process.myPid())
            System.exit(10)
        }
    }

    companion object {
        const val PREFS = "uzzap_crash_handler"
        const val KEY_CRASH = "last_crash"
        const val KEY_THREAD = "crash_thread"
    }
}
