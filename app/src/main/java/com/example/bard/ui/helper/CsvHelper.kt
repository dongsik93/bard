package com.example.bard.ui.helper

import android.content.ContentResolver
import android.net.Uri
import com.example.bard.BardBase.Companion.appContext
import com.opencsv.CSVReader
import java.io.*

/**
 * CsvHelper
 */
class CsvHelper {
    fun readCsvData(path: Uri?): List<Array<String>> {
        return try {
            println(">>>>>>>>>> path ${createCopyAndReturnRealPath(path)}")
            FileReader(createCopyAndReturnRealPath(path)).use { fr ->
                val dataList = arrayListOf<Array<String>>()

                CSVReader(fr).use { csvReader ->
                    for (data in csvReader) {
                        dataList.add(data)
                    }
                }
                dataList
            }
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }
    }

    private fun createCopyAndReturnRealPath(uri: Uri?): String? {
        uri?.let { _uri ->
            val contentResolver: ContentResolver = appContext.contentResolver ?: return null

            val filePath: String = (appContext.applicationInfo.dataDir.toString() + File.separator
                    + System.currentTimeMillis())
            val file = File(filePath)
            try {
                val inputStream = contentResolver.openInputStream(_uri) ?: return null
                val outputStream: OutputStream = FileOutputStream(file)
                val buf = ByteArray(1024)
                var len: Int
                while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)

                outputStream.close()
                inputStream.close()
            } catch (ignore: IOException) {
                return null
            }
            return file.absolutePath
        } ?: return null
    }
}