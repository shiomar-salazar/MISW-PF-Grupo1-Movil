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
import com.sportapp_grupo1.databinding.PlanAlimentacionCreateFragmentBinding
import com.sportapp_grupo1.models.PlanEntrenamiento
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.PlanEntrenamientoNetworkService
import com.sportapp_grupo1.validator.EmptyValidator
import com.sportapp_grupo1.validator.PlanAlimentacionValidator
import com.sportapp_grupo1.validator.base.BaseValidator
import org.json.JSONObject

class PlanAlimentacionCreate : Fragment() {

    private var _binding: PlanAlimentacionCreateFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var volleyBroker: PlanEntrenamientoNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlanAlimentacionCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        volleyBroker = this.context?.let { PlanEntrenamientoNetworkService(it) }!!

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

                val params = mapOf<String, Any>(
                    "lunes" to lunes ,
                    "martes" to martes,
                    "miercoles" to miercoles,
                    "jueves" to jueves,
                    "viernes  " to viernes,
                    "sabado" to sabado,
                    "domingo" to domingo,
                    "numero_semanas" to semanas,
                )

                volleyBroker.instance.add(PlanEntrenamientoNetworkService.postRequest(JSONObject(params),
                    {response ->
                        /* Guardar User en Cache */
                        val plan = PlanEntrenamiento (
                            planEntrenamientoID = response.optString("id"),
                            entrenamiento = response.optString("entrenamiento"),
                            lunes = response.getJSONObject("plan_entrenamiento").optString("lunes"),
                            martes = response.getJSONObject("plan_entrenamiento").optString("martes"),
                            miercoles = response.getJSONObject("plan_entrenamiento").optString("miercoles"),
                            jueves = response.getJSONObject("plan_entrenamiento").optString("jueves"),
                            viernes = response.getJSONObject("plan_entrenamiento").optString("viernes"),
                            sabado = response.getJSONObject("plan_entrenamiento").optString("sabado"),
                            domingo = response.getJSONObject("plan_entrenamiento").optString("domingo"),
                            numero_semanas = response.optInt("numero_semanas")
                        )
                        CacheManager.getInstance(this.requireContext()).addPlanEntrentamiento(plan)
                        /* Mostar Toast */
                        showMessage("Registro Exitoso.")
                        // Navegar a Home
                        navigateToHome()
                    },
                    {
                        showMessage("Registro Fallido.")
                    },
                    "entrenamientos/plan-entrenamiento",
                    CacheManager.getInstance(this.requireContext()).getUsuario().token
                    ))
            } else {
                showMessage("Todos los campos deben ser diligenciados, por favor corrija e intente de nuevo.")
            }
        }

    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_planAlimentacionCreate_to_home2)
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
    }
}