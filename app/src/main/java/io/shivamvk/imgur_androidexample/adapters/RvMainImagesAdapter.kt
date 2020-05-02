package io.shivamvk.imgur_androidexample.adapters

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.shivamvk.imgur_androidexample.R
import io.shivamvk.imgur_androidexample.models.ImageModel

class RvMainImagesAdapter(private val context: Context, private val fragmentManager: FragmentManager, private val images: List<ImageModel>, private val width: Int, private  val height: Int) :
    RecyclerView.Adapter<RvMainImagesAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        public var tvRvMainImagesItemViewTitle: TextView = view.findViewById(R.id.tv_rv_main_images_item_view_title)
        var ivRvMainImagesItemViewImage: ImageView = view.findViewById(R.id.iv_rv_main_images_item_view_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RvMainImagesAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_main_images_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvRvMainImagesItemViewTitle.text = "\"" + images[position].title + "\""
        var displayMetrics = DisplayMetrics()

        Picasso.get()
            .load(images[position].link)
            .placeholder(R.drawable.iv_loading)
            .centerCrop()
            .resize(width, height/2)
            .into(holder.ivRvMainImagesItemViewImage)

        holder.ivRvMainImagesItemViewImage.setOnClickListener{
            var bottomSheet =
                BottomSheetFullScreenAdapter(
                    images[position].link
                )
            bottomSheet.show(fragmentManager, bottomSheet.tag)
        }
    }

    override fun getItemCount() = images.size
}