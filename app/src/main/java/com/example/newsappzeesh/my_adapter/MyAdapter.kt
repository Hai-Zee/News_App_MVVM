package com.example.newsappzeesh.my_adapter

import android.graphics.drawable.Drawable
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.newsappzeesh.modal.Article
import com.example.newsappzeesh.R
import com.facebook.shimmer.ShimmerFrameLayout
import java.text.SimpleDateFormat

class MyAdapter(val myInterface: MyInterface) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private val mList = ArrayList<Article>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.custom_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {

        val data: Article = mList.get(position)
        holder.title.text = data.title
        holder.description.text = data.description
        holder.source_name.text = data.source_name.name

//         time from API call
        val timeFromAPI = data.time
//        here we are Using Incoming time format i.e.  yyyy-MM-dd'T'HH:mm:ss'Z'  for ex. 2020-10-25T08:13:17.5645451Z
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//         this will give something like this:  1603593272000
        val time = simpleDateFormat.parse(timeFromAPI).time
//        getting system current time
        val now = System.currentTimeMillis()
//        this wil give difference in string with ago added
        val timeAgo = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)

//        Finally we will set the time here, below this line...
        holder.time.text = timeAgo.toString()
//        holder.time.text = data.time


//        Glide.with(holder.itemView.context).load(data.imageUrl).centerCrop().into(holder.image)
        Glide.with(holder.itemView.context).load(data.imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.imageProgessbar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.imageProgessbar.visibility = View.GONE
                    return false
                }
            }).into(holder.image)


        holder.itemView.setOnClickListener(View.OnClickListener {
            myInterface.itemViewClickListner(data)
        })

        holder.shareButton.setOnClickListener {
            myInterface.shareButtonClickListner(data)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView = itemView.findViewById(R.id.imageID)
        var title: TextView = itemView.findViewById(R.id.titleID)
        var description: TextView = itemView.findViewById(R.id.descriptionID)
        var source_name: TextView = itemView.findViewById(R.id.sourceID)
        var time: TextView = itemView.findViewById(R.id.timeID)
        var imageProgessbar: ProgressBar = itemView.findViewById(R.id.imageProgressBar)
        var shareButton: ImageButton = itemView.findViewById(R.id.shareButton)
//        var shimmerFrameLayout : ShimmerFrameLayout = itemView.findViewById(R.id.shimmerEffectID)
//        var constraintLayout : ConstraintLayout = itemView.findViewById(R.id.customViewConstraintID)

    }

    fun updateAdapter(list: List<Article>) {
        mList.clear()
        mList.addAll(list)
        Log.d("adapter1", "updateAdapter: $mList")
        notifyDataSetChanged()
    }

    interface MyInterface {
        fun itemViewClickListner(newsData: Article)
        fun shareButtonClickListner(newsData: Article)
    }
}

