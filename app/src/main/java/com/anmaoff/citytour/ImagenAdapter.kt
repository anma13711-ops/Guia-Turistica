package com.anmaoff.citytour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImagenAdapter(private val imagenes: List<Int>) :
    RecyclerView.Adapter<ImagenAdapter.ImagenViewHolder>() {

    class ImagenViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        val imagen: ImageView = vista.findViewById(R.id.imgSlide)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagenViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slide, parent, false)
        return ImagenViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ImagenViewHolder, position: Int) {
        holder.imagen.setImageResource(imagenes[position])
    }

    override fun getItemCount() = imagenes.size
}