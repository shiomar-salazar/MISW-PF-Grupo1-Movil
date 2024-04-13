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
import com.sportapp_grupo1.databinding.PlanAlimentacionDetailFragmentBinding
import com.sportapp_grupo1.viewmodels.PlanAlimentacionViewModel

class PlanAlimentacionDetail : Fragment() {


    private var _binding: PlanAlimentacionDetailFragmentBinding? = null
    private lateinit var viewModel: PlanAlimentacionViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlanAlimentacionDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.crear.setOnClickListener {
            navigateToCreatePlan()
        }

    }

    private fun navigateToCreatePlan() {
        findNavController().navigate(R.id.action_planAlimentacionDetail_to_planAlimentacionCreate)
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
            PlanAlimentacionViewModel.Factory(activity.application)
        )[PlanAlimentacionViewModel::class.java]
        viewModel.planAplimentacion.observe(viewLifecycleOwner) {
            it.apply {
                binding.lunesDetail.text = this.lunes.plus(" kcal")
                binding.martesDetail.text = this.martes.plus(" kcal")
                binding.miercolesDetail.text = this.miercoles.plus(" kcal")
                binding.juevesDetail.text = this.jueves.plus(" kcal")
                binding.viernesDetail.text = this.viernes.plus(" kcal")
                binding.sabadoDetail.text = this.sabado.plus(" kcal")
                binding.domingoDetail.text = this.domingo.plus(" kcal")
                binding.semanasDetail.text = this.numero_semanas.toString()
            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }


}