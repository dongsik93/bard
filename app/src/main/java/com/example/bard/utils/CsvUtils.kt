package com.example.bard.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.bard.BardBase.Companion.appContext
import com.opencsv.CSVReader
import java.io.*

/**
 * CsvUtils
 */
class CsvUtils {
    fun readCsvData(path: Uri?): Pair<List<Array<String>>, String> {
        return try {
            println(">>>>>>>>>> realPath ${getRealPath(path)}")
            val realPath = getRealPath(path)
            FileReader(realPath).use { fr ->
                val dataList = arrayListOf<Array<String>>()

                CSVReader(fr).use { csvReader ->
                    for (data in csvReader) {
                        dataList.add(data)
                    }
                }
                dataList to realPath
            }
        } catch (e: Exception) {
            e.printStackTrace()
            listOf<Array<String>>() to ""
        }
    }

    private fun getRealPath(importUri: Uri?): String {
        var arrayPath = ""
        val uri = Uri.parse(importUri.toString())
        try {
            appContext.contentResolver.openInputStream(uri).use { inputStream ->
                getDisplayNameColumn(appContext, uri)?.let {
                    val dir = getAttachmentCacheDir(appContext)
                    val file = File(dir, it)

                    writeFile(inputStream, file)
                    arrayPath = file.absolutePath
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return arrayPath
    }

    private fun writeFile(input: InputStream?, file: File) = try {
        BufferedOutputStream(FileOutputStream(file)).use { _stream ->
            val buf = ByteArray(1024)
            var len: Int

            input?.let { _input ->
                while (true) {
                    len = _input.read(buf)
                    if (len != -1) _stream.write(buf, 0, len) else break
                }

                input.close()
                _stream.flush()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    private fun getAttachmentCacheDir(context: Context): File {
        val file = File(context.externalCacheDir, "temp")
        if (!file.exists()) file.mkdir()
        return file
    }

    private fun getDisplayNameColumn(context: Context, uri: Uri, ): String? {
        val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
        try {
            context.contentResolver.query(uri, projection, null, null, null).use { cursor ->
                cursor?.let { _cursor ->
                    val displayNameIndex = _cursor.getColumnIndexOrThrow(projection.first())
                    cursor.let {
                        if (it.moveToFirst()) return it.getString(displayNameIndex)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}