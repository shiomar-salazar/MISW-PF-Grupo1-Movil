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
import com.sportapp_grupo1.databinding.PlanAlimentacionCreateFragmentBinding
import com.sportapp_grupo1.models.PlanAlimentacion
import com.sportapp_grupo1.validator.EmptyValidator
import com.sportapp_grupo1.validator.PlanAlimentacionValidator
import com.sportapp_grupo1.validator.base.BaseValidator
import com.sportapp_grupo1.viewmodels.PlanAlimentacionViewModel

class PlanAlimentacionCreate : Fragment() {

    private var _binding: PlanAlimentacionCreateFragmentBinding? = null
    private lateinit var viewModel: PlanAlimentacionViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlanAlimentacionCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelar.setOnClickListener {
            navigateToHome()
        }

        binding.crear.setOnClickListener {
            val lunes = binding.lunesText.text.toString()
            val martes = binding.martesText.text.toString()
            val miercoles = binding.miercolesText.text.toString()
            val jueves = binding.juevesText.text.toString()
            val viernes = binding.viernesText.text.toString()
            val sabado = binding.sabadoText.text.toString()
            val domingo = binding.domingoText.text.toString()
            val semanas = binding.semanaText.text.toString()

            val lunesValidator = BaseValidator.validate(EmptyValidator(lunes), PlanAlimentacionValidator(lunes))
            binding.lunes.error =
                if (!lunesValidator.isSuccess) getString(lunesValidator.message) else null
            val martesValidator = BaseValidator.validate(EmptyValidator(martes), PlanAlimentacionValidator(martes))
            binding.martes.error =
                if (!martesValidator.isSuccess) getString(martesValidator.message) else null
            val miercolesValidator = BaseValidator.validate(EmptyValidator(miercoles), PlanAlimentacionValidator(miercoles))
            binding.miercoles.error =
                if (!miercolesValidator.isSuccess) getString(miercolesValidator.message) else null
            val juevesValidator = BaseValidator.validate(EmptyValidator(jueves), PlanAlimentacionValidator(jueves))
            binding.jueves.error =
                if (!juevesValidator.isSuccess) getString(juevesValidator.message) else null
            val viernesValidator = BaseValidator.validate(EmptyValidator(viernes), PlanAlimentacionValidator(viernes))
            binding.viernes.error =
                if (!viernesValidator.isSuccess) getString(viernesValidator.message) else null
            val sabadoValidator = BaseValidator.validate(EmptyValidator(sabado), PlanAlimentacionValidator(sabado))
            binding.sabado.error =
                if (!sabadoValidator.isSuccess) getString(sabadoValidator.message) else null
            val domingoValidator = BaseValidator.validate(EmptyValidator(domingo), PlanAlimentacionValidator(domingo))
            binding.domingo.error =
                if (!domingoValidator.isSuccess) getString(domingoValidator.message) else null
            val semanasValidator = BaseValidator.validate(EmptyValidator(semanas))
            binding.semanas.error =
                if (!semanasValidator.isSuccess) getString(semanasValidator.message) else null

            if (semanasValidator.isSuccess && domingoValidator.isSuccess && sabadoValidator.isSuccess
                    && viernesValidator.isSuccess && juevesValidator.isSuccess && miercolesValidator.isSuccess
                    && martesValidator.isSuccess && lunesValidator.isSuccess) {
                val newPlan = PlanAlimentacion (
                    lunes = lunes,
                    martes = martes,
                    miercoles = miercoles,
                    jueves = jueves,
                    viernes = viernes,
                    sabado = sabado,
                    domingo = domingo,
                    numero_semanas = semanas
                )
                if (viewModel.addNewPlanAlimentacion(newPlan)) {
                    showMessage("El nuevo Plan Alimentacion se registró correctamente.")
                    navigateToHome()
                } else {
                    showMessage("Ocurrió un error en el registro de nuevo Plan de  Alimentacion.")
                }
            } else {
                showMessage("Todos los campos deben ser diligenciados, por favor corrija e intente de nuevo.")
            }
        }

    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_planAlimentacionCreate_to_home2)
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
            PlanAlimentacionViewModel.Factory(activity.application)
        )[PlanAlimentacionViewModel::class.java]
        viewModel.planAplimentacion.observe(viewLifecycleOwner) {
            it.apply {

            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

}