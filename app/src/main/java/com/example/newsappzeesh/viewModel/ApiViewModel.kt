package com.example.newsappzeesh.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsappzeesh.repo.Repo
import com.example.newsappzeesh.modal.Article

public class ApiViewModel : ViewModel() {

    //    var mutableArticleData : MutableLiveData<List<Article>> = MutableLiveData<List<Article>>()
//    val repo : repo = repo()
    var repoBBC: Repo = Repo()
    var repoEnter: Repo = Repo()
    var repoIndia: Repo = Repo()
    var repoTech: Repo = Repo()

    fun getTechNews(): MutableLiveData<List<Article>> {
        return repoTech.getTechNews()
    }

    fun getEntertainment(): MutableLiveData<List<Article>> {
        return repoEnter.getEntertainmentNews()
    }

    fun getIndia(): MutableLiveData<List<Article>> {
        return repoIndia.getIndiaNews()
    }

    fun getBBC(): MutableLiveData<List<Article>> {
        return repoBBC.getBBCNews()
    }
}