package com.example.newsappzeesh.view.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappzeesh.modal.Article
import com.example.newsappzeesh.my_adapter.MyAdapter
import com.example.newsappzeesh.R
import com.example.newsappzeesh.databinding.FragmentIndiaBinding
import com.example.newsappzeesh.viewModel.ApiViewModel
import com.facebook.shimmer.ShimmerFrameLayout

class India : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mAdapter: MyAdapter
    private lateinit var clickListners: MyAdapter.MyInterface
    private lateinit var apiViewModel: ApiViewModel
    private lateinit var fragmentIndiaBinding: FragmentIndiaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentIndiaBinding = FragmentIndiaBinding.inflate(inflater, container, false)
        return fragmentIndiaBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //   Implementing both methods of Inner Interface of Adapter..
        clickListners = object : MyAdapter.MyInterface {
            override fun itemViewClickListener(newsData: Article) {
//                to open the URL in to chrome custom tab..
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(mContext, Uri.parse(newsData.newsUrl))
            }
            override fun shareButtonClickListener(newsData: Article) {
                val intent = Intent(Intent.ACTION_SEND).setType("text/plain")
                intent.putExtra(Intent.EXTRA_TEXT, newsData.newsUrl)

                val chooser: Intent = Intent.createChooser(intent, "Share with...")
                startActivity(chooser)
            }
        }
        setupRecyclerview()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        apiViewModel = ViewModelProvider(requireActivity()).get(ApiViewModel::class.java)
        apiViewModel.getIndia().observe(viewLifecycleOwner, Observer {

            fragmentIndiaBinding.indiaShimmerEffectID.stopShimmer()
            fragmentIndiaBinding.indiaShimmerEffectID.hideShimmer()
            fragmentIndiaBinding.indiaShimmerEffectID.visibility = View.GONE

            fragmentIndiaBinding.indiaRecyclerID.visibility = View.VISIBLE
            mAdapter.updateAdapter(it)
            fragmentIndiaBinding.indiaRecyclerID.adapter = mAdapter
        })
    }

//
//    @SuppressLint("NewApi")
//    private fun fetchDataIndianNews(){
//        val newsUrl = "https://newsapi.org/v2/top-headlines?country=in&apiKey=730a60dec330429c8fc1a2d3eeec28fd"
//
//// Request a json response from the provided URL.
//        val jsonObjectRequest = object : JsonObjectRequest(
//                Request.Method.GET,
//                newsUrl,
//                null,
//                Response.Listener<JSONObject> { response ->
//                    val jsonArray = response.getJSONArray("articles")
//                    val list = ArrayList<News>()
//                    for (i in 0 until jsonArray.length()){
//                        val jsonObjects = jsonArray.getJSONObject(i)
//
//                        val title = jsonObjects.getString("title")
//                        val content = jsonObjects.getString("description")
//                        val newsUrl = jsonObjects.getString("url")
//                        val source_name = jsonObjects.getJSONObject("source").getString("name")
//                        val imageUrl = jsonObjects.getString("urlToImage")
//
//                        // time from API call
//                        val timeFromAPI = jsonObjects.getString("publishedAt")
//                        // here we are Using Incoming time format i.e.  yyyy-MM-dd'T'HH:mm:ss'Z'  for ex. 2020-10-25T08:13:17.5645451Z
//                        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//                        // this will give something like this:  1603593272000
//                        val time = simpleDateFormat.parse(timeFromAPI).time
//                        // getting system current time
//                        val now = System.currentTimeMillis()
//                        // this wil give difference in string with ago added
//                        val timeAgo = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
//
//                        val data = News(title, content, newsUrl, source_name, imageUrl, timeAgo.toString())
//                        list.add(data)
//                    }
//                    mAdapter.updateAdapter(list)
//                },
//                Response.ErrorListener {
//                    Toast.makeText(mContext, it.message, Toast.LENGTH_LONG).show()
//                }) {
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["User-Agent"] = "Mozilla/5.0"
//                return headers
//            }
//        };
////        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'")
//        MySingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest)
//    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun setupRecyclerview() {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        fragmentIndiaBinding.indiaRecyclerID.layoutManager = linearLayoutManager
        mAdapter = MyAdapter(clickListners)
    }
}