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
import com.sportapp_grupo1.databinding.EntrenamientoResultListFragmentBinding
import com.sportapp_grupo1.models.Entrenamiento
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.EntrenamientoNetworkService
import com.sportapp_grupo1.ui.adapters.EntrenamientoAdapter
import org.json.JSONObject


class EntrenamientoResultList : Fragment() {

    private var _binding: EntrenamientoResultListFragmentBinding? = null
    private  val  binding get() = _binding !!
    private lateinit var recyclerView: RecyclerView
    private var viewAdapter: EntrenamientoAdapter? = null
    private  lateinit var volleyBroker: EntrenamientoNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EntrenamientoResultListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewAdapter = EntrenamientoAdapter()
        return view
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = binding.entreResultFragment
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewAdapter
        volleyBroker = this.context?.let { EntrenamientoNetworkService(it) }!!


        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        volleyBroker.instance.add(
            EntrenamientoNetworkService.getRequest(
            {response ->
                val list = mutableListOf<Entrenamiento>()
                var item: JSONObject
                var res:String = ""
                if( response.length() == 0){
                    showMessage("Usuario no tiene datos cargados aun.")
                    list.add(0,
                        Entrenamiento(
                            entrenamientoId = "",
                            actividad = R.string.Vacio.toString(),
                            distancia = R.string.Vacio.toString(),
                            tiempo = R.string.Vacio.toString(),
                            date = R.string.Vacio.toString(),
                            resultado = R.string.Vacio.toString(),
                            feedback = ""
                        )
                    )
                    viewAdapter!!.results = list

                }else {
                    (0 until response.length()).forEach { it ->
                        item = response.getJSONObject(it)
                        val actividad = item.getString("actividad")
                        res = if (actividad == "Ciclismo") {
                            item.getInt("ftp").toString()
                        } else {
                            item.getInt("vo2max").toString()
                        }
                        list.add(
                            it,
                            Entrenamiento(
                                entrenamientoId = item.getString("id"),
                                actividad = item.getString("actividad"),
                                distancia = item.getString("distancia").plus(R.string.sufix_entrenamiento.toString()),
                                tiempo = item.getString("tiempo"),
                                date = item.getString("fecha"),
                                resultado = res,
                                feedback = item.getString("retroalimentacion")
                            )
                        )
                    }
                    viewAdapter!!.results = list
                    showMessage("Carga Exitosa.")
                }
            },
            {
                showMessage("Carga Fallida. Error:".plus(it.networkResponse.statusCode.toString()))
            },
            "resultado-entrenamiento/usuario/"+user.userId,
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