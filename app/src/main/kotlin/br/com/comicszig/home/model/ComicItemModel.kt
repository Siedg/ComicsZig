package br.com.comicszig.home.model

import com.google.gson.annotations.SerializedName

data class ComicItemModel(
    val data: DataModel
)

data class DataModel(
    val results: List<ResultModel>
)

data class ResultModel(
    val title: String,
    val description: String?,
    val thumbnail: ThumbnailModel
)

data class ThumbnailModel(
    val path: String,
    val extension: String,
)
