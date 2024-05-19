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
import com.sportapp_grupo1.databinding.SugerenciasListFragmentBinding
import com.sportapp_grupo1.models.Sugerencia
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.SugerenciasNetworkService
import com.sportapp_grupo1.ui.adapters.SugerenciasAdapter
import org.json.JSONObject

class SugerenciasList : Fragment() {

    private var _binding: SugerenciasListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var viewAdapter: SugerenciasAdapter? = null
    private  lateinit var volleyBroker: SugerenciasNetworkService
    private var req1 = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SugerenciasListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewAdapter = SugerenciasAdapter()
        return view
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = binding.sugerenciaItemFragment
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewAdapter
        volleyBroker = this.context?.let { SugerenciasNetworkService(it) }!!

        binding.container.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE


        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        volleyBroker.instance.add(
            SugerenciasNetworkService.getRequest(
                {response ->
                    req1 = true
                    if( response.length() == 0) {
                        binding.noSugerenciaText.visibility = View.VISIBLE
                        showMessage(resources.getString(R.string.no_eventos))
                        checkProgressBar()
                    }else {
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
                                )
                            )
                        }
                        viewAdapter!!.sugerencias = list
                        showMessage(resources.getString(R.string.exito))
                        checkProgressBar()
                    }
                },
                {
                    req1 = true
                    showMessage(resources.getString(R.string.failed_Error).plus(it.networkResponse.statusCode.toString()))
                    checkProgressBar()
                },
                user.token
            ))
    }

    private fun showMessage(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
    }
    fun checkProgressBar(){
        if(req1){
            binding.progressBar.visibility = View.GONE
            binding.container.visibility = View.VISIBLE
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
    }

}