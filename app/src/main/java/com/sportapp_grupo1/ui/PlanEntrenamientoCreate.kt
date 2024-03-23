package com.sportapp_grupo1.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.PlanentrenamientoCrearFragmentBinding
import com.sportapp_grupo1.models.PlanEntrenamiento
import com.sportapp_grupo1.viewmodels.PlanEntrenamientoCreateViewModel

class PlanEntrenamientoCreate : Fragment() {

    private var _binding: PlanentrenamientoCrearFragmentBinding? = null
    private lateinit var viewModel: PlanEntrenamientoCreateViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlanentrenamientoCrearFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelar.setOnClickListener {
            findNavController().navigate(R.id.action_planentrenamiento_crear_fragment_to_home2)
        }

        binding.crear.setOnClickListener {
            val lunes = binding.lunes.editText?.text.toString()
            val martes = binding.martes.editText?.text.toString()
            val miercoles = binding.miercoles.editText?.text.toString()
            val jueves = binding.jueves.editText?.text.toString()
            val viernes = binding.viernes.editText?.text.toString()
            val sabado = binding.sabado.editText?.text.toString()
            val domingo = binding.domingo.editText?.text.toString()
            val semanas = binding.semanas.editText?.text.toString()

            val argsArray: ArrayList<String> = arrayListOf(lunes, martes, miercoles, jueves, viernes, sabado, domingo, semanas)
            if (this.formIsValid(argsArray)) {
                val newPlan = PlanEntrenamiento (
                    lunes = lunes,
                    martes = martes,
                    miercoles = miercoles,
                    jueves = jueves,
                    viernes = viernes,
                    sabado = sabado,
                    domingo = domingo,
                    numero_semanas = semanas
                )
                if (viewModel.addNewPlanEntrenamiento(newPlan)) {
                    showMessage("El nuevo Entrenamiento se registró correctamente.")
                    navigateToHome()
                } else {
                    showMessage("Ocurrió un error en el registro del entrenamiento.")
                }
            } else {
                showMessage("Todos los campos deben ser diligenciados, por favor corrija e intente de nuevo.")
            }
        }

    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_planentrenamiento_crear_fragment_to_home2)
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    private fun formIsValid(array: ArrayList<String>): Boolean {
        for (elem in array) {
            if (TextUtils.isEmpty(elem)) {
                return false
            }
        }
        return true
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
        viewModel = ViewModelProvider(
            this,
            PlanEntrenamientoCreateViewModel.Factory(activity.application)
        )[PlanEntrenamientoCreateViewModel::class.java]
        viewModel.planEntrenamiento.observe(viewLifecycleOwner) {
            it.apply {

            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }



}