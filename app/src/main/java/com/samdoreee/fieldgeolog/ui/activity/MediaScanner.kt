package com.samdoreee.fieldgeolog

import android.content.Context
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.MediaScannerConnectionClient
import android.net.Uri
import android.text.TextUtils


/**
 * 이미지 저장 후 미디어 스캐닝을 수행해줄 때 사용하는 유틸 클래스
 */
class MediaScanner private constructor(private val mContext: Context) {
    private lateinit var mMediaScanner: MediaScannerConnection

    //private                 MediaScannerConnection.MediaScannerConnectionClient mMediaScannerClient;
    private var mFilePath = ""

    init {
        val mediaScanClient: MediaScannerConnectionClient
        mediaScanClient = object : MediaScannerConnectionClient {
            override fun onMediaScannerConnected() {
                mMediaScanner.scanFile(mFilePath, null)
                //                mFilePath = path;
            }
            override fun onScanCompleted(path: String, uri: Uri) {
                println("::::MediaScan Success::::")
                mMediaScanner.disconnect()
            }
        }
        mMediaScanner = MediaScannerConnection(mContext, mediaScanClient)
    }

    fun mediaScanning(path: String) {
        if (TextUtils.isEmpty(path)) return
        mFilePath = path
        if (!mMediaScanner.isConnected) mMediaScanner.connect()

        //mMediaScanner.scanFile( path,null );
    }

    companion object {
        @Volatile
        private var mMediaInstance: MediaScanner? = null
        fun getInstance(context: Context?): MediaScanner? {
            if (null == context) return null
            if (null == mMediaInstance) mMediaInstance = MediaScanner(context)
            return mMediaInstance
        }

        fun releaseInstance() {
            if (null != mMediaInstance) {
                mMediaInstance = null
            }
        }
    }
}