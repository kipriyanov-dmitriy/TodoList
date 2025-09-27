package com.example.todo.core

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.TypedValue
import androidx.core.graphics.createBitmap
import java.io.File

object WallpaperUtils {
    fun updateWallpaper(context: Context, tasks: List<String>) {
        val wm = WallpaperManager.getInstance(context)
        val displayMetrics = context.resources.displayMetrics
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)

        val paint = Paint().apply {
            color = Color.BLACK
            textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                20f,
                displayMetrics
            )
            isAntiAlias = true
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
        }
        canvas.drawTaskList(tasks, width / 2f, height / 3f, paint, lineSpacing = 10f)
        val crop = Rect(0, 0, width, height)
        wm.suggestDesiredDimensions(width, height)
        wm.setBitmap(bitmap, crop, true, WallpaperManager.FLAG_SYSTEM)
    }
}

fun Canvas.drawTaskList(
    tasks: List<String>,
    x: Float,
    y: Float,
    paint: Paint,
    lineSpacing: Float = 0f
) {
    val fm = paint.fontMetrics
    val lineHeight = (fm.descent - fm.ascent) + lineSpacing

    var baseline = y - fm.ascent

    for (task in tasks) {
        this.drawText(task, x, baseline, paint)
        baseline += lineHeight
    }
}

fun saveBitmapToGallery(context: Context, bitmap: Bitmap, albumName: String) {
    val filename = "wallpaper_debug_${System.currentTimeMillis()}.png"
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/$albumName")
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        }
    }

    var uri: Uri? = null
    try {
        uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        if (uri != null) {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
            // Уведомляем галерею о новом файле (особенно важно для Android < 10)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    albumName
                )
                if (!file.exists()) file.mkdirs()
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(File(file, filename).absolutePath),
                    null,
                    null
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}