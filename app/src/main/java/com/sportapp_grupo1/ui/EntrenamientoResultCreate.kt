package com.sportapp_grupo1.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.EntrenamientoResultFragmentBinding
import com.sportapp_grupo1.models.Entrenamiento
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.EntrenamientoNetworkService
import com.sportapp_grupo1.validator.DateValidator
import com.sportapp_grupo1.validator.EmptyValidator
import com.sportapp_grupo1.validator.TimeValidator
import com.sportapp_grupo1.validator.base.BaseValidator
import org.json.JSONObject


class EntrenamientoResultCreate : Fragment() {

    private var _binding: EntrenamientoResultFragmentBinding? = null
    private val binding get() = _binding!!
    private  lateinit var volleyBroker: EntrenamientoNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EntrenamientoResultFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        volleyBroker = this.context?.let { EntrenamientoNetworkService(it) }!!
        val sharedPref = activity?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        var result_ftp = 0.0
        var result_vo2 = 0.0

        val distance = sharedPref!!.getInt("entre_goal",0)
        binding.goal.text = distance.toString().plus(" km")
        binding.tiempoText.setText(sharedPref.getString("time_result",""))
        sharedPref.edit().remove("time_result").apply();

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
                if (binding.actividadSpinner.selectedItem.equals("Atletismo")){
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
            val date = binding.dateText.text.toString()

            val tiempoValidator = BaseValidator.validate(EmptyValidator(tiempo), TimeValidator(tiempo))
            binding.tiempo.error =
                if (!tiempoValidator.isSuccess) getString(tiempoValidator.message) else null
            val resultValidator = BaseValidator.validate(EmptyValidator(result))
            binding.result.error =
                if (!resultValidator.isSuccess) getString(resultValidator.message) else null
            val dateValidator = BaseValidator.validate(EmptyValidator(date), DateValidator(date))
            binding.date.error =
                if (!dateValidator.isSuccess) getString(dateValidator.message) else null

            if (tiempoValidator.isSuccess && resultValidator.isSuccess && dateValidator.isSuccess) {
                if(actividad == "Ciclismo"){
                    result_ftp = result.toDouble()
                    result_vo2 = 0.0
                }
                else
                {
                    result_vo2 = result.toDouble()
                    result_ftp = 0.0
                }

                val params = mapOf(
                    "fecha" to date,
                    "id_usuario" to user.userId,
                    "ftp" to result_ftp.toFloat(),
                    "vo2max" to result_vo2.toFloat(),
                    "retroalimentacion" to retro,
                    "actividad" to actividad,
                    "distancia" to distance,
                    "tiempo" to tiempo
                )

                volleyBroker.instance.add(
                    EntrenamientoNetworkService.postRequest(
                        JSONObject(params),
                        {response ->
                            /* Guardar Plan en Cache */
                            val result = Entrenamiento (
                                entrenamientoId = response.optString("id"),
                                date = response.optString("fecha"),
                                actividad = response.optString("entrenamiento") ,
                                distancia = response.optString("distancia"),
                                tiempo = response.optString("tiempo"),
                                resultado = response.optString("resultado"),
                                feedback = response.optString("retroalimentacion")
                            )
                            CacheManager.getInstance(this.requireContext()).addEntrenamiento(result)
                            /* Mostar Toast */
                            showMessage("Registro Exitoso.")
                            // Navegar a Home
                            navigateToHome()
                        },
                        {
                            showMessage("Registro Fallido.Error:".plus(it.networkResponse.statusCode.toString()))
                        },
                        "entrenamientos/resultados-plan-entrenamiento",
                        user.token
                    ))
            } else {
                showMessage("Todos los campos deben ser diligenciados, por favor corrija e intente de nuevo.")
            }
        }

    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_entrenamientoResult_to_home2)
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