package com.djphy.example.googleplacesdynamicsearch.features.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djphy.example.googleplacesdynamicsearch.R
import com.djphy.example.googleplacesdynamicsearch.di.NetworkModule.Companion.GPLACES_KEY
import com.djphy.example.googleplacesdynamicsearch.remote.network.models.RestaurantList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_restaurant_item.view.*

class RestaurantAdapter : RecyclerView.Adapter<RestaurantAdapter.ItemViewHolder>() {

    private var mDataList = emptyList<RestaurantList.RestaurantItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_restaurant_item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mDataList[position])
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: RestaurantList.RestaurantItem?) {
            item?.let {
                //https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRvAAAAwMpdHeWlXl-lH0vp7lez4znKPIWSWvgvZFISdKx45AwJVP1Qp37YOrH7sqHMJ8C-vBDC546decipPHchJhHZL94RcTUfPa1jWzo-rSHaTlbNtjh-N68RkcToUCuY9v2HNpo5mziqkir37WU8FJEqVBIQ4k938TI3e7bf8xq-uwDZcxoUbO_ZJzPxremiQurAYzCTwRhE_V0&sensor=false&key=AddYourOwnKeyHere
                itemView.apply {
                    tvName.text = it.name
                    tvRating.text = it.rating
                    var pic = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference=${it.photosList?.get(0)?.p_ref}&sensor=false&key=${GPLACES_KEY}"
                    if (it.photosList?.get(0)?.p_ref.isNullOrEmpty())
                    {
                        pic = it.imgUrl!!
                    }
                    Picasso.get()
                        .load(pic)
                        .placeholder(R.drawable.ic_restaurant)
                        .into(ivIcon)
                }
            }
        }
    }

    override fun getItemCount(): Int = mDataList.size

    fun clearAndSubmitList(dataList: List<RestaurantList.RestaurantItem>){
        mDataList = emptyList()
        mDataList = dataList
        notifyDataSetChanged()
    }

    fun submitList(dataList: List<RestaurantList.RestaurantItem>){
        mDataList = dataList
        notifyDataSetChanged()
    }
}