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
import com.sportapp_grupo1.databinding.PlanEntrenamientoDetailFragmentBinding
import com.sportapp_grupo1.models.PlanEntrenamiento
import com.sportapp_grupo1.viewmodels.PlanEntrenamientoViewModel


class PlanEntrenamientoDetail : Fragment() {

    private var _binding: PlanEntrenamientoDetailFragmentBinding? = null
    private lateinit var viewModel: PlanEntrenamientoViewModel
    private val binding get() = _binding!!
    private var planEntrenamientoUser: PlanEntrenamiento? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlanEntrenamientoDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actividadDetail.text = planEntrenamientoUser?.entrenamiento
        binding.lunesDetail.text = planEntrenamientoUser?.lunes
        binding.martesDetail.text = planEntrenamientoUser?.martes
        binding.miercolesDetail.text = planEntrenamientoUser?.miercoles
        binding.juevesDetail.text = planEntrenamientoUser?.jueves
        binding.viernesDetail.text = planEntrenamientoUser?.viernes
        binding.sabadoDetail.text = planEntrenamientoUser?.sabado
        binding.domingoDetail.text = planEntrenamientoUser?.domingo
        binding.semanasDetail.text = planEntrenamientoUser?.numero_semanas.toString()

        binding.crear.setOnClickListener {
            navigateToCreatePlan()
        }

    }

    private fun navigateToCreatePlan() {
        findNavController().navigate(R.id.action_planEntrenamientoDetail_to_planentrenamiento_crear_fragment)
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
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
        viewModel = ViewModelProvider(
            this,
            PlanEntrenamientoViewModel.Factory(activity.application)
        )[PlanEntrenamientoViewModel::class.java]
        viewModel.planEntrenamiento.observe(viewLifecycleOwner) {
            it.apply {
                planEntrenamientoUser = this
            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

}