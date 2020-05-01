package io.shivamvk.imgur_androidexample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.shivamvk.imgur_androidexample.R
import io.shivamvk.imgur_androidexample.models.ImageModel

class RvMainImagesAdapter(private val images: List<ImageModel>) :
    RecyclerView.Adapter<RvMainImagesAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        public var tvRvMainImagesItemView: TextView = view.findViewById(R.id.tv_rv_main_images_item_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RvMainImagesAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_main_images_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvRvMainImagesItemView.text = images[position].title
    }

    override fun getItemCount() = images.size
}