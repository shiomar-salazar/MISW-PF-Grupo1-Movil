package com.sportapp_grupo1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.EntrenamientoItemBinding
import com.sportapp_grupo1.models.Entrenamiento

class EntrenamientoAdapter: RecyclerView.Adapter<EntrenamientoAdapter.EntrenamientoViewHolder>(){

    var results: List<Entrenamiento> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntrenamientoViewHolder {
        val withDataBinding: EntrenamientoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            EntrenamientoViewHolder.LAYOUT,
            parent,
            false
        )
        return EntrenamientoViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: EntrenamientoViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.results = results[position]
            if(results[position].actividad == "Ciclismo")
            {
                it.resultadoLabel.text = R.string.ftp.toString()
            }else{
                it.resultadoLabel.text = R.string.VO2MAX.toString()
            }
        }
    }

    class EntrenamientoViewHolder (val viewDataBinding: EntrenamientoItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.entrenamiento_item
        }
    }

}