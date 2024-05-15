package com.sportapp_grupo1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.AlimentacionResultListFragmentBinding
import com.sportapp_grupo1.models.Alimentacion
import com.sportapp_grupo1.network.AlimentacionNetworkService
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.ui.adapters.AlimentacionAdapter
import org.json.JSONObject

class AlimentacionResultList : Fragment() {

    private var _binding: AlimentacionResultListFragmentBinding? = null
    private  val  binding get() = _binding !!
    private lateinit var recyclerView: RecyclerView
    private var viewAdapter: AlimentacionAdapter? = null
    private  lateinit var volleyBroker: AlimentacionNetworkService
    private var req1 = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlimentacionResultListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewAdapter = AlimentacionAdapter()
        return view
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = binding.aliResultFragment
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewAdapter
        volleyBroker = this.context?.let { AlimentacionNetworkService(it) }!!

        binding.container.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        binding.fabAddResult.setOnClickListener {
            navigateToCreateResult()
        }

        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        volleyBroker.instance.add(AlimentacionNetworkService.getRequest(
            {response ->
                req1 = true
                val list = mutableListOf<Alimentacion>()
                var item: JSONObject
                (0 until response.length()).forEach { it ->
                    item = response.getJSONObject(it)
                    val calorias1 = item.getInt("calorias_1").toString()
                    val calorias2 = item.getInt("calorias_2").toString()
                    val calorias3 = item.getInt("calorias_3").toString()
                    list.add(it,
                        Alimentacion(
                            alimentacionID = item.getString("id"),
                            calorias1 = calorias1,
                            calorias2 = calorias2,
                            calorias3 = calorias3,
                            ml_agua = item.getInt("ml_agua").toString().plus(" ml"),
                            date = item.getString("fecha"),
                            total_calories = (calorias1.toInt() + calorias2.toInt() + calorias3.toInt()).toString().plus(resources.getString(R.string.sufix_alimentacion))
                        )
                    )
                }
                viewAdapter!!.results = list
                showMessage("Carga Exitosa.")
                checkProgressBar()
            },
            {
                req1 = true
                if(it.networkResponse.statusCode == 404){
                    showMessage("Usuario no tiene datos cargados aun.")
                    val list = mutableListOf<Alimentacion>()
                    list.add(0,
                        Alimentacion(
                            alimentacionID = "",
                            calorias1 = "",
                            calorias2 = "",
                            calorias3 = "",
                            ml_agua = resources.getString(R.string.Vacio),
                            date = resources.getString(R.string.Vacio),
                            total_calories = resources.getString(R.string.Vacio)
                        )
                    )
                    viewAdapter!!.results = list
                    checkProgressBar()
                }
                else
                {
                    showMessage("Carga Fallida. Error:".plus(it.networkResponse.statusCode.toString()))
                }
            },
            "nutricion/resultados-alimentacion/"+user.userId,
            user.token
        ))
    }

    private fun showMessage(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCreateResult() {
        findNavController().navigate(R.id.action_alimentacionResultList_to_alimentacionResultCreate)
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