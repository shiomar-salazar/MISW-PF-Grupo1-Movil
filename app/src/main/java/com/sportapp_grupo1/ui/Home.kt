package com.sportapp_grupo1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.HomeFragmentBinding
import com.sportapp_grupo1.viewmodels.HomeViewModel


class Home : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private lateinit var viewModel: HomeViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.alimentacion.setOnClickListener {
            findNavController().navigate((R.id.action_home2_to_alimentacionResultList))
        }
        binding.entrenamiento.setOnClickListener {
            findNavController().navigate((R.id.action_home2_to_entrenamiento_Menu))
        }
        binding.tusEventos.setOnClickListener {
            showMessage("Not implemented yet.")
        }
        binding.sugerencias.setOnClickListener {
            showMessage("Not implemented yet.")
        }
        binding.planAlimentacion.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_planAlimentacionDetail)
        }

        binding.planEntrenamiento.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_planEntrenamientoDetail)
        }
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
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
        viewModel = ViewModelProvider(
            this,
            HomeViewModel.Factory(activity.application)
        )[HomeViewModel::class.java]
        viewModel.full_access.observe(viewLifecycleOwner) {
            it.apply {

            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }


}

