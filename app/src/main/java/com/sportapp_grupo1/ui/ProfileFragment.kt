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
    private var req1 = false

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

        binding.container.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        volleyBroker.instance.add(LoginNetworkService.getRequest(
            { response ->
                req1 = true
                binding.nombreText.text = response.optString("nombres").plus(" ").plus(response.optString("apellidos"))
                binding.edadText.text = response.optInt("edad").toString()
                binding.pesoText.text = response.optDouble("peso").toString()
                binding.alturaText.text = response.optDouble("altura").toString()
                binding.correoText.text = response.optString("usuario")
                binding.sexoText.text = response.optString("genero")
                binding.ciudadText.text = response.optString("ciudad_residencia")
                binding.paisText.text = response.optString("pais_residencia")
                binding.planText.text = response.optString("tipo_plan")
                checkProgressBar()
            },
            {
                req1 = true
                showMessage(resources.getString(R.string.failed_Error).plus(it.networkResponse.statusCode.toString()))
                checkProgressBar()
            },user.token
        ))


        binding.cerrarSesion.setOnClickListener{
            findNavController().navigate((R.id.action_profileFragment_to_mainFragment))
        }
        binding.cambiarContraseA.setOnClickListener {
            //showMessage(resources.getString(R.string.not_part_mvp))
        }
        binding.mejorarPlan.setOnClickListener {
            //showMessage(resources.getString(R.string.not_part_mvp))
        }
        binding.editarPerfil.setOnClickListener {
            //showMessage(resources.getString(R.string.not_part_mvp))
        }

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