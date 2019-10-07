package kr.ac.gachon.searchdogs.service

import android.content.Intent
import android.provider.MediaStore

class gallery() {

    public fun choosePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)


    }

}