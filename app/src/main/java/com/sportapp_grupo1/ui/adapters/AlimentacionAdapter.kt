package com.sportapp_grupo1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.AlimentacionItemBinding
import com.sportapp_grupo1.models.Alimentacion

class AlimentacionAdapter: RecyclerView.Adapter<AlimentacionAdapter.AlimentacionViewHolder>() {

    var results: List<Alimentacion> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlimentacionViewHolder {
        val withDataBinding: AlimentacionItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlimentacionViewHolder.LAYOUT,
            parent,
            false
        )
        return AlimentacionViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: AlimentacionViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.results = results[position]
        }
    }


    class AlimentacionViewHolder (val viewDataBinding: AlimentacionItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.alimentacion_item
        }


    }
}