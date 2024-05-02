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
import com.sportapp_grupo1.databinding.SugerenciasListFragmentBinding
import com.sportapp_grupo1.models.Sugerencia
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.SugerenciasNetworkService
import com.sportapp_grupo1.ui.adapters.SugerenciaAdapter
import org.json.JSONObject

class SugerenciasList : Fragment() {

    private var _binding: SugerenciasListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var viewAdapter: SugerenciaAdapter? = null
    private  lateinit var volleyBroker: SugerenciasNetworkService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SugerenciasListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewAdapter = SugerenciaAdapter()
        return view
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = binding.sugerenciaItemFragment
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
                                fecha = "",
                                descripcion = "",
                                estado = "",
                                frecuencia = "",
                                horario = emptyList()
                            )
                        )
                    }
                    viewAdapter!!.sugerencias = list
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