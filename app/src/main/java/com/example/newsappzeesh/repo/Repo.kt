package com.example.newsappzeesh.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.newsappzeesh.network.ApiClient
import com.example.newsappzeesh.network.ApiInterface
import com.example.newsappzeesh.modal.Api
import com.example.newsappzeesh.modal.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repo {
    var mutableListBBC: MutableLiveData<List<Article>> = MutableLiveData()
    var mutableListEntertainment: MutableLiveData<List<Article>> = MutableLiveData()
    var mutableListIndia: MutableLiveData<List<Article>> = MutableLiveData()
    var mutableListTech: MutableLiveData<List<Article>> = MutableLiveData()
    val apiKey: String = "730a60dec330429c8fc1a2d3eeec28fd"

    private val apiInterface: ApiInterface =
        ApiClient.getClient()!!.create(ApiInterface::class.java)

    private var resp: Api? = null

    //    fun getApi() {
//        api_Interface = ApiClient.getClient()!!.create(ApiInterface::class.java)
//        var call : Call<List<Api>> = api_Interface.getList()

    fun getTechNews(): MutableLiveData<List<Article>> {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Api =
                    apiInterface.getTechNewsApi("us", "technology", apiKey)

                withContext(Dispatchers.Main) {
                    resp = response
                    mutableListTech.value = resp?.articles
                }
            } catch (e: Exception) {
                Log.d("tech_news_exception", "getTechNews: " + e.message)
            }
        }
        return mutableListTech
    }

    fun getEntertainmentNews(): MutableLiveData<List<Article>> {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Api = apiInterface.getEntertainmnetNewsApi(
                    "in",
                    "entertainment",
                    apiKey
                )

                withContext(Dispatchers.Main) {
                    resp = response
                    mutableListEntertainment.value = resp?.articles
                }
            } catch (e: Exception) {
                Log.d("Entertain_news", "getEntertainNews: " + e.message)
            }
        }
        return mutableListEntertainment
    }

    fun getIndiaNews(): MutableLiveData<List<Article>> {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Api =
                    apiInterface.getIndiaNewsApi("in", apiKey)
//                    var mutableData : MutableLiveData<List<Article>> = MutableLiveData()
//                    mutableData.postValue(response.articles)
//                    mutableListIndia = mutableData
//                    Log.d("indiarepo1", "getIndiaNews: ${mutableListIndia.value}")

                withContext(Dispatchers.Main) {
                    resp = response
                    mutableListIndia.value = resp?.articles
                }
            } catch (e: Exception) {
            }
        }
        return mutableListIndia
    }

    fun getBBCNews(): MutableLiveData<List<Article>> {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Api =
                    apiInterface.getBBCNewsApi("bbc.co.uk", "en", apiKey)

                withContext(Dispatchers.Main) {
                    resp = response
                    mutableListBBC.value = resp?.articles
                }
            } catch (e: Exception) {
                Log.d("BBC_News", "getBBCNews: " + e.message)
            }
        }
        return mutableListBBC
    }

}
