package com.sportapp_grupo1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sportapp_grupo1.databinding.EventosListFragmentBinding
import com.sportapp_grupo1.models.Sugerencia
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.SugerenciasNetworkService
import com.sportapp_grupo1.ui.adapters.EventosAdapter
import org.json.JSONObject

class EventosList : Fragment() {

    private var _binding: EventosListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var viewAdapter: EventosAdapter? = null
    private  lateinit var volleyBroker: SugerenciasNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EventosListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewAdapter = EventosAdapter()
        return view
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = binding.eventosItemFragment
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewAdapter
        volleyBroker = this.context?.let { SugerenciasNetworkService(it) }!!


        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        volleyBroker.instance.add(
            SugerenciasNetworkService.getRequest(
                {response ->
                    val list = mutableListOf<Sugerencia>()
                    var item: JSONObject
                    (0 until response.length()).forEach { it ->
                        item = response.getJSONObject(it)
                        list.add(
                            it,
                            Sugerencia(
                                sugerencia_id = item.getString("id"),
                                costo = item.getString("costo"),
                                nombre = item.getString("nombre").take(18),
                                lugar = item.getString("lugar"),
                                horario_final = "10:00:00 AM"
                            )
                        )
                    }
                    viewAdapter!!.eventos = list
                    showMessage("Carga Exitosa.")
                },
                {
                    showMessage("Carga Fallida. Error:".plus(it.networkResponse.statusCode.toString()))
                },
                user.token
            ))
    }

    private fun showMessage(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
    }

}