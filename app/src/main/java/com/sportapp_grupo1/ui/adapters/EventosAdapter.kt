package com.sportapp_grupo1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.EventosItemBinding
import com.sportapp_grupo1.models.Sugerencia
import com.sportapp_grupo1.ui.EventosListDirections

class EventosAdapter : RecyclerView.Adapter<EventosAdapter.EventosViewHolder>(){
    var eventos:List<Sugerencia> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventosViewHolder {
        val withDataBinding: EventosItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            EventosViewHolder.LAYOUT,
            parent,
            false
        )
        return EventosViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return eventos.size
    }

    override fun onBindViewHolder(holder: EventosViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.evento = eventos[position]
            holder.viewDataBinding.root.setOnClickListener{
                val action = EventosListDirections.actionEventosListToEventosDetail(eventos[position].sugerencia_id)
                holder.viewDataBinding.root.findNavController().navigate(action)
            }
        }
    }

    class EventosViewHolder (val viewDataBinding: EventosItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.eventos_item
        }
    }
}