package com.samdoreee.fieldgeolog

import android.content.Context
import android.util.Log
import com.amazonaws.auth.BasicSessionCredentials
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.samdoreee.fieldgeolog.data.model.Constants
import java.io.File

class S3FileDownloader(
    private val context: Context,
) {
    private val AWSAccessKeyId: String = "AKIAWTFYC3FPRCM4TVFA"
    private val SecretAccessKey: String = "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"
    private val bucketName: String = "fieldgeolog/uploaded"
    private val regions:  Regions = Regions.AP_NORTHEAST_2
    private var credentials = BasicSessionCredentials(AWSAccessKeyId, SecretAccessKey, "")
    private var sS3Client = AmazonS3Client(credentials, Region.getRegion(regions))
    private var mTransferUtility = TransferUtility.builder()
        .context(context)
        .s3Client(sS3Client)
        .defaultBucket(bucketName)
        .build()

    interface DownloadListener {
        fun onProgressUpdate(id: Int, current: Int){
            Log.d(Constants.TAG, "id는 : $id")
        }

        fun onDownloadCompleted(file: File?){
            Log.d(Constants.TAG, "파일 등장 : $file")
        }

        fun onDownloadFailed(){
            Log.d(Constants.TAG, "다운 실패")
        }
    }

    fun downloadFile(fileName: String, downloadListener: DownloadListener?) {
        val file = File(context.getExternalFilesDir(null), fileName)
        val observer = mTransferUtility.download(
            bucketName,
            fileName,
            file
        )

        observer.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                when (state) {
                    TransferState.COMPLETED -> downloadListener?.onDownloadCompleted(file)
                    TransferState.FAILED, TransferState.CANCELED -> downloadListener?.onDownloadFailed()
                    else -> {}  // Handle other states as needed
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val percentDone = (bytesCurrent.toFloat() / bytesTotal.toFloat() * 100).toInt()
                downloadListener?.onProgressUpdate(id, percentDone)
            }

            override fun onError(id: Int, ex: Exception) {
                downloadListener?.onDownloadFailed()
            }
        })
    }
}
