package com.sportapp_grupo1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.SugerenciasItemBinding
import com.sportapp_grupo1.models.Sugerencia

class SugerenciaAdapter: RecyclerView.Adapter<SugerenciaAdapter.SugerenciaViewHolder>(){

    var sugerencias:List<Sugerencia> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SugerenciaViewHolder {
        val withDataBinding: SugerenciasItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            SugerenciaViewHolder.LAYOUT,
            parent,
            false
        )
        return SugerenciaViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return sugerencias.size
    }

    override fun onBindViewHolder(holder: SugerenciaViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.sugerencia = sugerencias[position]
        }
    }

    class SugerenciaViewHolder (val viewDataBinding: SugerenciasItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.sugerencias_item
        }
    }


}