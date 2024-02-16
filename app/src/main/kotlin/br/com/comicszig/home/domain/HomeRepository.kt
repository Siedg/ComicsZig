package br.com.comicszig.home.domain

import br.com.comicszig.home.model.ComicItemModel

interface HomeRepository {

    suspend fun getComics(): ComicItemModel
}
