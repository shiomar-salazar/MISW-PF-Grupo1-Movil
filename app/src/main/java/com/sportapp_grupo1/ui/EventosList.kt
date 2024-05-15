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
import com.sportapp_grupo1.R
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
    private var req1 = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        binding.container.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        volleyBroker.instance.add(
            SugerenciasNetworkService.getRequest_registrados(
                {response ->
                    req1 = true
                    val list = mutableListOf<Sugerencia>()
                    var item: JSONObject
                    (0 until response.length()).forEach { it ->
                        item = response.getJSONObject(it)
                        list.add(
                            it,
                            Sugerencia(
                                sugerencia_id = item.getString("id"),
                                costo = item.getString("costo"),
                                nombre = item.getString("nombre").take(23),
                                lugar = item.getString("lugar"),
                                horario_final = item.getString("hora"),
                                fecha = item.getString("fecha")
                            )
                        )
                    }
                    viewAdapter!!.eventos = list
                    showMessage("Carga Exitosa.")
                    checkProgressBar()
                },
                {
                    req1 = true
                    if(it.networkResponse.statusCode == 404){
                        showMessage("Usuario no tiene datos cargados aun.")
                        val list = mutableListOf<Sugerencia>()
                        list.add(0,
                            Sugerencia(
                                sugerencia_id = "",
                                nombre = resources.getString(R.string.Vacio),
                                costo = "",
                                descripcion = "",
                                horario_final = resources.getString(R.string.Vacio),
                                lugar = resources.getString(R.string.Vacio),
                                fecha = resources.getString(R.string.Vacio)
                            )
                        )
                        viewAdapter!!.eventos = list

                    }
                    else
                    {
                        showMessage("Carga Fallida. Error:".plus(it.networkResponse.statusCode.toString()))
                    }
                    checkProgressBar()
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

    fun checkProgressBar(){
        if(req1){
            binding.progressBar.visibility = View.GONE
            binding.container.visibility = View.VISIBLE
        }
    }

}