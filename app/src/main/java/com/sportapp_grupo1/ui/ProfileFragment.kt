package com.sportapp_grupo1.ui

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


class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        binding.nombreText.text = user.nombres

        binding.cerrarSesion.setOnClickListener{
            findNavController().navigate((R.id.action_profileFragment_to_mainFragment))
        }
        binding.cambiarContraseA.setOnClickListener {
            showMessage("Fuera del Alcance del MVP.")
        }
        binding.mejorarPlan.setOnClickListener {
            showMessage("Fuera del Alcance del MVP.")
        }
        binding.editarPerfil.setOnClickListener {
            showMessage("Fuera del Alcance del MVP.")
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