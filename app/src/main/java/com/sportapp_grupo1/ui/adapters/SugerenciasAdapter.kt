package com.sportapp_grupo1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.SugerenciasItemBinding
import com.sportapp_grupo1.models.Sugerencia
import com.sportapp_grupo1.ui.SugerenciasListDirections

class SugerenciasAdapter: RecyclerView.Adapter<SugerenciasAdapter.SugerenciasViewHolder>(){

    var sugerencias:List<Sugerencia> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SugerenciasViewHolder {
        val withDataBinding: SugerenciasItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            SugerenciasViewHolder.LAYOUT,
            parent,
            false
        )
        return SugerenciasViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return sugerencias.size
    }

    override fun onBindViewHolder(holder: SugerenciasViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.sugerencia = sugerencias[position]
            holder.viewDataBinding.root.setOnClickListener{
                val action = SugerenciasListDirections.actionSugerenciasListToSugerenciasDetail(sugerencias[position].sugerencia_id)
                holder.viewDataBinding.root.findNavController().navigate(action)
            }
        }
    }

    class SugerenciasViewHolder (val viewDataBinding: SugerenciasItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.sugerencias_item
        }
    }


}