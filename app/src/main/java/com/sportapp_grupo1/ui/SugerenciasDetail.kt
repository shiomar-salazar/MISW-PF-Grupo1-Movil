package com.sportapp_grupo1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.SugerenciasDetailFragmentBinding
import com.sportapp_grupo1.models.Sugerencia
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.SugerenciasNetworkService


class SugerenciasDetail : Fragment() {
    private var _binding: SugerenciasDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private  lateinit var volleyBroker: SugerenciasNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SugerenciasDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: SugerenciasDetailArgs by navArgs()
        volleyBroker = this.context?.let { SugerenciasNetworkService(it) }!!

        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        volleyBroker.instance.add(
            SugerenciasNetworkService.getRequest_single(
            {response ->
                var list_horarios = mutableListOf<String>()
                val elements = response.getJSONArray("horario")
                (0 until elements.length()).forEach {
                    list_horarios.add(it, elements[it].toString())
                }
                val sugerencia = Sugerencia (
                    sugerencia_id = response.optString("id"),
                    nombre = response.optString("nombre"),
                    lugar = response.optString("lugar"),
                    fecha = response.optString("fecha"),
                    costo = response.optString("costo"),
                    descripcion = response.optString("descripcion"),
                    estado = response.optString("estado"),
                    frecuencia = response.optString("frecuencia")
                )

                binding.costoText.text = sugerencia.costo
                binding.nombreText.text = sugerencia.nombre
                binding.fechaText.text = sugerencia.fecha.take(10)
                binding.descripcionText.text = sugerencia.descripcion
                binding.lugarText.text = sugerencia.lugar
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    list_horarios
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.horaSpinner.adapter = adapter
                /* Mostar Toast */
                showMessage("Carga Exitosa.")
            },
            {

                showMessage("Carga Fallida. Error:".plus(it.networkResponse.statusCode.toString()))
            },
            args.sugerneciaId,
            user.token
        ))

        binding.registrarBtn.setOnClickListener {
            showMessage("Not implemented Yet")
            NavigateHome()
        }
    }


    private fun NavigateHome() {
        findNavController().navigate(R.id.action_sugerenciasDetail_to_home2)
    }

    private fun showMessage(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
    }





}