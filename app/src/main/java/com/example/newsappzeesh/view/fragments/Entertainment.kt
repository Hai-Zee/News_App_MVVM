package com.example.newsappzeesh.view.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappzeesh.modal.Article
import com.example.newsappzeesh.my_adapter.MyAdapter
import com.example.newsappzeesh.R
import com.example.newsappzeesh.viewModel.ApiViewModel
import com.facebook.shimmer.ShimmerFrameLayout


class Entertainment : Fragment() {

    lateinit var mContext: Context
    lateinit var mRecyclerView: RecyclerView
    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    lateinit var mAdapter: MyAdapter
    lateinit var clickListners: MyAdapter.MyInterface
    private lateinit var apiViewModel: ApiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entertainment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView = view.findViewById(R.id.entertainmentRecyclerID)
        shimmerFrameLayout = view.findViewById(R.id.entertainmentShimmerEffectID)

        //   Implementing both methods of Inner Interface of Adapter..
        clickListners = object : MyAdapter.MyInterface {
            override fun itemViewClickListner(newsData: Article) {
//                to open the URL in to chrome custom tab..
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(mContext, Uri.parse(newsData.newsUrl))
            }

            override fun shareButtonClickListner(newsData: Article) {
                var intent = Intent(Intent.ACTION_SEND).setType("text/plain")
                intent.putExtra(Intent.EXTRA_TEXT, newsData.newsUrl)

                var chooser: Intent = Intent.createChooser(intent, "Share with...")
                startActivity(chooser)
            }
        }
        setupRecyclerview()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        apiViewModel = ViewModelProvider(requireActivity()).get(ApiViewModel::class.java)
        apiViewModel.getEntertainment().observe(viewLifecycleOwner, Observer {

            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.hideShimmer()
            shimmerFrameLayout.visibility = View.GONE

            mRecyclerView.visibility = View.VISIBLE
            mAdapter.updateAdapter(it)
            mRecyclerView.adapter = mAdapter
        })
    }

//
//    @SuppressLint("NewApi")
//    private fun fetchDataIndianNews(){
//        val newsUrl = "https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=730a60dec330429c8fc1a2d3eeec28fd"
//
//// Request a json response from the provided URL.
//        val jsonObjectRequest = object : JsonObjectRequest(
//            Request.Method.GET,
//            newsUrl,
//            null,
//            Response.Listener<JSONObject> { response ->
//                val jsonArray = response.getJSONArray("articles")
//                val list = ArrayList<News>()
//                for (i in 0 until jsonArray.length()){
//                    val jsonObjects = jsonArray.getJSONObject(i)
//
//                    val title = jsonObjects.getString("title")
//                    val content = jsonObjects.getString("description")
//                    val newsUrl = jsonObjects.getString("url")
//                    val source_name = jsonObjects.getJSONObject("source").getString("name")
//                    val imageUrl = jsonObjects.getString("urlToImage")
//
//                    // time from API call
//                    val timeFromAPI = jsonObjects.getString("publishedAt")
//                    // here we are Using Incoming time format i.e.  yyyy-MM-dd'T'HH:mm:ss'Z'  for ex. 2020-10-25T08:13:17.5645451Z
//                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//                    // this will give something like this:  1603593272000
//                    val time = simpleDateFormat.parse(timeFromAPI).time
//                    // getting system current time
//                    val now = System.currentTimeMillis()
//                    // this wil give difference in string with ago added
//                    val timeAgo = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
//
//                    val data = Article(title, content, newsUrl, source_name, imageUrl, timeAgo.toString())
//                    list.add(data)
//                }
//                mAdapter.updateAdapter(list)
//            },
//            Response.ErrorListener {
//                Toast.makeText(mContext, it.message, Toast.LENGTH_LONG).show()
//            }) {
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["User-Agent"] = "Mozilla/5.0"
//                return headers
//            }
//        }
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
        mRecyclerView.layoutManager = linearLayoutManager
        mAdapter = MyAdapter(clickListners)
    }

}

