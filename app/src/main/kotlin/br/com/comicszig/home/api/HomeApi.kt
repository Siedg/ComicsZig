package br.com.comicszig.home.api

import br.com.comicszig.home.model.ComicItemModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET("comics")
    fun getComics(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ) : Call<ComicItemModel>
}
