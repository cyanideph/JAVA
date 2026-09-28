package com.cyanideph.java

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class CrashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences(CrashHandler.PREFS, MODE_PRIVATE)
        val crash = prefs.getString(CrashHandler.KEY_CRASH, "Unknown crash") ?: "Unknown crash"
        val thread = prefs.getString(CrashHandler.KEY_THREAD, "unknown") ?: "unknown"

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 40, 32, 32)
            setBackgroundColor(Color.WHITE)
        }

        val title = TextView(this).apply {
            text = "Uzzap crashed"
            textSize = 26f
            setTextColor(Color.BLACK)
            typeface = Typeface.DEFAULT_BOLD
        }

        val subtitle = TextView(this).apply {
            text = "Crash captured. Copy the diagnostic below and send it to the developer."
            textSize = 16f
            setTextColor(Color.DKGRAY)
            setPadding(0, 12, 0, 20)
        }

        val diagnostic = TextView(this).apply {
            text = "Thread: $thread\n\n$crash"
            textSize = 12f
            setTextColor(Color.BLACK)
            setTextIsSelectable(true)
            typeface = Typeface.MONOSPACE
        }

        val scroll = ScrollView(this).apply {
            addView(diagnostic, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ))
        }

        val close = Button(this).apply {
            text = "CLOSE"
            setOnClickListener {
                prefs.edit().remove(CrashHandler.KEY_CRASH).remove(CrashHandler.KEY_THREAD).apply()
                finishAndRemoveTask()
            }
        }

        root.addView(title)
        root.addView(subtitle)
        root.addView(scroll, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))
        root.addView(close, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ))

        setContentView(root)
    }
}
