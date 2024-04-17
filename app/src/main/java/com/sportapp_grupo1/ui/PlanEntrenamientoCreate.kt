package com.sportapp_grupo1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.PlanEntrenamientoCreateFragmentBinding
import com.sportapp_grupo1.models.PlanEntrenamiento
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.PlanEntrenamientoNetworkService
import com.sportapp_grupo1.validator.EmptyValidator
import com.sportapp_grupo1.validator.base.BaseValidator
import org.json.JSONObject

class PlanEntrenamientoCreate : Fragment() {

    private var _binding: PlanEntrenamientoCreateFragmentBinding? = null
    private val binding get() = _binding!!
    private  lateinit var volleyBroker: PlanEntrenamientoNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlanEntrenamientoCreateFragmentBinding.inflate(inflater, container, false)
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
            val actividad = binding.actividadSpinner.selectedItem.toString().trim()

            val lunesValidator = BaseValidator.validate(EmptyValidator(lunes))
            binding.lunes.error =
                if (!lunesValidator.isSuccess) getString(lunesValidator.message) else null
            val martesValidator = BaseValidator.validate(EmptyValidator(lunes))
            binding.martes.error =
                if (!martesValidator.isSuccess) getString(martesValidator.message) else null
            val miercolesValidator = BaseValidator.validate(EmptyValidator(lunes))
            binding.miercoles.error =
                if (!miercolesValidator.isSuccess) getString(miercolesValidator.message) else null
            val juevesValidator = BaseValidator.validate(EmptyValidator(lunes))
            binding.jueves.error =
                if (!juevesValidator.isSuccess) getString(juevesValidator.message) else null
            val viernesValidator = BaseValidator.validate(EmptyValidator(lunes))
            binding.viernes.error =
                if (!viernesValidator.isSuccess) getString(viernesValidator.message) else null
            val sabadoValidator = BaseValidator.validate(EmptyValidator(lunes))
            binding.sabado.error =
                if (!sabadoValidator.isSuccess) getString(sabadoValidator.message) else null
            val domingoValidator = BaseValidator.validate(EmptyValidator(lunes))
            binding.domingo.error =
                if (!domingoValidator.isSuccess) getString(domingoValidator.message) else null
            val semanasValidator = BaseValidator.validate(EmptyValidator(lunes))
            binding.semanas.error =
                if (!semanasValidator.isSuccess) getString(semanasValidator.message) else null

            val argsArray: ArrayList<String> = arrayListOf(lunes, martes, miercoles, jueves, viernes, sabado, domingo, semanas)
            if (this.formIsValid(argsArray)) {
                val params = mapOf(
                    "entrenamiento" to actividad,
                    "numero_semanas" to semanas.toInt(),
                    "id_usuario" to CacheManager.getInstance(this.requireContext()).getUsuario().userId,
                    "plan_entrenamiento" to mapOf<String, Any>(
                        "lunes" to lunes,
                        "martes" to martes,
                        "miercoles" to miercoles,
                        "jueves" to jueves,
                        "viernes" to viernes,
                        "sabado" to sabado,
                        "domingo" to  domingo
                    )
                )
                volleyBroker.instance.add(PlanEntrenamientoNetworkService.postRequest(
                    JSONObject(params),
                    {response ->
                        /* Guardar Plan en Cache */
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
                        showMessage("Registro Fallido. Error:".plus(it.networkResponse.statusCode.toString()))
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
        findNavController().navigate(R.id.action_planentrenamiento_crear_fragment_to_home2)
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
    }



}