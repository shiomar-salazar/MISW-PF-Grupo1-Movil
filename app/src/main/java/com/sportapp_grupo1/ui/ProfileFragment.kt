package com.sportapp_grupo1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.ProfileFragmentBinding
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.LoginNetworkService


class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private  lateinit var volleyBroker: LoginNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        volleyBroker = this.context?.let { LoginNetworkService(it) }!!

        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        volleyBroker.instance.add(LoginNetworkService.getRequest(
            { response ->
                binding.nombreText.text = response.optString("nombres").plus(" ").plus(response.optString("apellidos"))
                binding.edadText.text = response.optInt("edad").toString()
                binding.pesoText.text = response.optDouble("peso").toString()
                binding.alturaText.text = response.optDouble("altura").toString()
                binding.correoText.text = response.optString("usuario")
                binding.sexoText.text = response.optString("genero")
                binding.ciudadText.text = response.optString("ciudad_residencia")
                binding.paisText.text = response.optString("pais_residencia")
                binding.planText.text = response.optString("tipo_plan")
            },
            {
                showMessage("Carga Fallida. Error:".plus(it.networkResponse.statusCode.toString()))
            },user.token
        ))


        binding.cerrarSesion.setOnClickListener{
            findNavController().navigate((R.id.action_profileFragment_to_mainFragment))
        }
        binding.cambiarContraseA.setOnClickListener {
            showMessage("Fuera del alcance del MVP.")
        }
        binding.mejorarPlan.setOnClickListener {
            showMessage("Fuera del alcance del MVP.")
        }
        binding.editarPerfil.setOnClickListener {
            showMessage("Fuera del alcance del MVP.")
        }

    }

    private fun showMessage(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
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