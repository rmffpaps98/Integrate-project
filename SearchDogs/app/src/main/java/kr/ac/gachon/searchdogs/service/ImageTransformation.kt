package kr.ac.gachon.searchdogs.service

import android.content.Context
import android.graphics.Bitmap
import android.media.ExifInterface
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest

class ImageTransformation : BitmapTransformation {

    private var mContext: Context
    private var mOrientation: Int

    constructor(context: Context, orientation: Int) {
        mContext = context
        mOrientation = orientation
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val exifOrientationDegrees = getExifOrientationDegrees(mOrientation)

        return TransformationUtils.rotateImageExif(pool, toTransform, exifOrientationDegrees)
    }

    private fun getExifOrientationDegrees(orientation: Int): Int {
        var exifint: Int

        if (orientation <= 0) {
            return orientation
        }

        when (orientation) {
            90 -> exifint = ExifInterface.ORIENTATION_ROTATE_90
            180 -> exifint = ExifInterface.ORIENTATION_ROTATE_180
            270 -> exifint = ExifInterface.ORIENTATION_ROTATE_270
            else -> exifint = ExifInterface.ORIENTATION_NORMAL
        }

        return exifint
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}