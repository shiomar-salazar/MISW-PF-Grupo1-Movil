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
import com.sportapp_grupo1.databinding.AlimentacionResultFragmentBinding
import com.sportapp_grupo1.models.Alimentacion
import com.sportapp_grupo1.validator.EmptyValidator
import com.sportapp_grupo1.validator.base.BaseValidator
import com.sportapp_grupo1.viewmodels.AlimentacionResultViewModel


class AlimentacionResult : Fragment() {


    private var _binding:AlimentacionResultFragmentBinding? = null
    private lateinit var viewModel: AlimentacionResultViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlimentacionResultFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelar.setOnClickListener {
            navigateToHome()
        }

        binding.registrar.setOnClickListener {
            val comida1 = binding.comida1Text.text.toString()
            val comida2 = binding.comida2Text.text.toString()
            val comida3 = binding.comida3Text.text.toString()
            val agua = binding.aguaText.text.toString()


            val comida1Validator = BaseValidator.validate(EmptyValidator(comida1))
            binding.comida1.error =
                if (!comida1Validator.isSuccess) getString(comida1Validator.message) else null
            val comida2Validator = BaseValidator.validate(EmptyValidator(comida2))
            binding.comida2.error =
                if (!comida2Validator.isSuccess) getString(comida2Validator.message) else null
            val comida3Validator = BaseValidator.validate(EmptyValidator(comida3))
            binding.comida3.error =
                if (!comida3Validator.isSuccess) getString(comida3Validator.message) else null
            val aguaValidator = BaseValidator.validate(EmptyValidator(agua))
            binding.agua.error =
                if (!aguaValidator.isSuccess) getString(aguaValidator.message) else null

            if (comida1Validator.isSuccess && comida2Validator.isSuccess && comida3Validator.isSuccess
                && aguaValidator.isSuccess) {
                val newPlan = Alimentacion (
                    calorias1 = comida1,
                    calorias2 = comida2,
                    calorias3 = comida3,
                    ml_agua = agua
                )
                if (viewModel.addNewAlimentacionResult(newPlan)) {
                    showMessage("La alimentacion del Dia se registró correctamente.")
                    navigateToHome()
                } else {
                    showMessage("Ocurrió un error en el registro de la Alimentacion.")
                }
            } else {
                showMessage("Todos los campos deben ser diligenciados, por favor corrija e intente de nuevo.")
            }
        }

    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_alimentacionResult_to_home2)
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
            AlimentacionResultViewModel.Factory(activity.application)
        )[AlimentacionResultViewModel::class.java]
        viewModel.alimentacion.observe(viewLifecycleOwner) {
            it.apply {

            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }
}

