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
import com.sportapp_grupo1.databinding.EntrenamientoMenuFragmentBinding
import com.sportapp_grupo1.viewmodels.EntrenamientoMenuViewModel


class EntrenamientoMenu : Fragment() {

    private var _binding: EntrenamientoMenuFragmentBinding? = null
    private lateinit var  viewModel: EntrenamientoMenuViewModel
    private  val  binding get() = _binding !!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EntrenamientoMenuFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* TODO: Cambiar para obtener la distancia del entrenamiento del dia */
        binding.goal.text = "5 km"

        binding.monitoreoBtn.setOnClickListener {
            showMessage("Not implemented yet.")
        }
        binding.entreResultBtn.setOnClickListener {
            findNavController().navigate((R.id.action_entrenamiento_Menu_to_entrenamientoResult))
        }
        binding.resultListBtn.setOnClickListener {
            findNavController().navigate((R.id.action_entrenamiento_Menu_to_entrenamientoResultList))
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
            EntrenamientoMenuViewModel.Factory(activity.application)
        )[EntrenamientoMenuViewModel::class.java]
        viewModel.full_access.observe(viewLifecycleOwner) {
            it.apply {

            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

}