package com.app.galleryapp.gallery

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import com.app.galleryapp.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.galleryapp.database.model.Image
import com.app.galleryapp.image_inspection.InspectionActivity
import kotlinx.android.synthetic.main.image_small.view.*


/**
 * Adapter allow to show images stored in room database.
 */
class GalleryAdapter(val context: Context) :
    RecyclerView.Adapter<GalleryAdapter.MyViewHolder>() {
    private var imagesList = emptyList<Image>()

    private val THUMBNAIL_SIZE = (Resources.getSystem().displayMetrics.widthPixels - 30) / 3


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_small, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem = imagesList[position]

        holder.itemView.imageIconView.setImageBitmap(
            ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeFile(currItem.path),
                THUMBNAIL_SIZE,
                THUMBNAIL_SIZE,
            )
        )
        holder.itemView.imageIconView.setOnClickListener {
            val intent = Intent(context, InspectionActivity::class.java)
            intent.putExtra("index", currItem.id)
            context.startActivity(intent)//may be needed view
        }

    }

    /**
     * Quantity of rows in image table.
     */
    override fun getItemCount(): Int {
        return imagesList.size
    }

    /**
     * Append data to adapter.
     */
    fun setData(images: List<Image>) {
        this.imagesList = images
        notifyDataSetChanged()
    }

}