package com.app.colorlovers.adapters

import DataModel
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.colorlovers.R
import com.squareup.picasso.Picasso


class RecyclerViewAdapter(private val mContext: Context, private val mData: List<DataModel>) :
        RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View
        val mInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.color_grid_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv_title.text = "Title: " + mData.get(position).title
        holder.tv_hex.text = "Hex: " + mData.get(position).hex
        Picasso.get().load(mData.get(position).imageUrl).into(holder.iv_colorImage)
        holder.iv_likeDislike.setOnClickListener {
            var resLike: Drawable = mContext.resources.getDrawable(R.drawable.like);
            var resDisLike: Drawable = mContext.resources.getDrawable(R.drawable.dislike);
            if (holder.iv_likeDislike.drawable.constantState == resLike.constantState)
                holder.iv_likeDislike.setImageDrawable(resDisLike)
            else
                holder.iv_likeDislike.setImageDrawable(resLike)
        }

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var iv_colorImage: ImageView
        internal var iv_likeDislike: ImageView
        internal var tv_title: TextView
        internal var tv_hex: TextView

        init {
            iv_colorImage = itemView.findViewById(R.id.iv_colorImage)
            iv_likeDislike = itemView.findViewById(R.id.likeDislikeImg)
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_hex = itemView.findViewById(R.id.tv_hex)
        }
    }
}