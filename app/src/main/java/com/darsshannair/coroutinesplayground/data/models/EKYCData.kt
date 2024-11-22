package com.darsshannair.coroutinesplayground.data.models

data class EKYCData(
    val id: String,
    val frontPhotoPath: String,
    val backPhotoPath: String,
    val selfiePath: String,
    val isEKYCDone: Boolean
)
