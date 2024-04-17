package com.sportapp_grupo1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.EntrenamientoResultFragmentBinding
import com.sportapp_grupo1.models.Entrenamiento
import com.sportapp_grupo1.validator.EmptyValidator
import com.sportapp_grupo1.validator.TimeValidator
import com.sportapp_grupo1.validator.base.BaseValidator
import com.sportapp_grupo1.viewmodels.EntrenamientoResultViewModel


class EntrenamientoResultCreate : Fragment() {

    private var _binding: EntrenamientoResultFragmentBinding? = null
    private lateinit var viewModel: EntrenamientoResultViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EntrenamientoResultFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* TODO: Cambiar para obtener la distancia del entrenamiento del dia */
        binding.goal.text = "5 km"

        /* Valores por defecto */
        binding.result.hint = "Vo2Max"
        binding.result.suffixText = "ml/kg/min"

        binding.cancelar.setOnClickListener {
            navigateToHome()
        }

        binding.actividadSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (binding.actividadSpinner.selectedItem.equals("Carreras")){
                    binding.result.hint = "Vo2Max"
                    binding.result.suffixText = "ml/kg/min"
                }
                else {
                    binding.result.hint = "FTP"
                    binding.result.suffixText = "%"
                }

            }

        }

        binding.registrar.setOnClickListener {
            val actividad = binding.actividadSpinner.selectedItem.toString().trim()
            val tiempo = binding.tiempoText.text.toString()
            val result = binding.resultText.text.toString()
            val retro = binding.retroSpinner.selectedItem.toString().trim()


            val tiempoValidator = BaseValidator.validate(EmptyValidator(tiempo), TimeValidator(tiempo))
            binding.tiempo.error =
                if (!tiempoValidator.isSuccess) getString(tiempoValidator.message) else null
            val resultValidator = BaseValidator.validate(EmptyValidator(result))
            binding.result.error =
                if (!resultValidator.isSuccess) getString(resultValidator.message) else null


            if (tiempoValidator.isSuccess && resultValidator.isSuccess) {
                val newPlan = Entrenamiento (
                    tiempo = tiempo,
                    resultado = result,
                    actividad = actividad,
                    feedback = retro,
                    distancia = "5km",
                    date=""
                )
                if (viewModel.addNewEntrenamientoResult(newPlan)) {
                    showMessage("El Entrenamiento del Dia se registró correctamente.")
                    navigateToHome()
                } else {
                    showMessage("Ocurrió un error en el registro del Entrenamiento.")
                }
            } else {
                showMessage("Todos los campos deben ser diligenciados, por favor corrija e intente de nuevo.")
            }
        }

    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_entrenamientoResult_to_home2)
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
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
            EntrenamientoResultViewModel.Factory(activity.application)
        )[EntrenamientoResultViewModel::class.java]
        viewModel.entrenamiento.observe(viewLifecycleOwner) {
            it.apply {

            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

}