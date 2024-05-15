package com.sportapp_grupo1.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.AlimentacionResultCreateFragmentBinding
import com.sportapp_grupo1.models.Alimentacion
import com.sportapp_grupo1.network.AlimentacionNetworkService
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.validator.DateValidator
import com.sportapp_grupo1.validator.EmptyValidator
import com.sportapp_grupo1.validator.base.BaseValidator
import org.json.JSONObject


class AlimentacionResultCreate : Fragment() {


    private var _binding:AlimentacionResultCreateFragmentBinding? = null
    private val binding get() = _binding!!
    private  lateinit var volleyBroker: AlimentacionNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlimentacionResultCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        volleyBroker = this.context?.let { AlimentacionNetworkService(it) }!!
        val sharedPref = activity?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val user = CacheManager.getInstance(this.requireContext()).getUsuario()


        binding.goal.text = sharedPref!!.getInt("alime_goal", 2000).toString().plus(resources.getString(R.string.sufix_alimentacion))

        binding.cancelar.setOnClickListener {
            navigateToHome()
        }

        binding.registrar.setOnClickListener {
            val comida1 = binding.comida1Text.text.toString()
            val comida2 = binding.comida2Text.text.toString()
            val comida3 = binding.comida3Text.text.toString()
            val agua = binding.aguaText.text.toString()
            val date = binding.dateText.text.toString()

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
            val dateValidator = BaseValidator.validate(EmptyValidator(date), DateValidator(date))
            binding.date.error =
                if (!dateValidator.isSuccess) getString(dateValidator.message) else null

            if (comida1Validator.isSuccess && comida2Validator.isSuccess && comida3Validator.isSuccess
                && aguaValidator.isSuccess && dateValidator.isSuccess) {
                val params = mapOf(
                    "fecha" to date,
                    "calorias_1" to comida1,
                    "calorias_2" to comida2,
                    "calorias_3" to comida3,
                    "ml_agua" to agua,
                    "id_usuario" to user.userId
                )

                volleyBroker.instance.add(
                    AlimentacionNetworkService.postRequest(
                    JSONObject(params),
                    {response ->
                        val calorias1 = response.optInt("calorias_1").toString()
                        val calorias2 = response.optInt("calorias_2").toString()
                        val calorias3 = response.optInt("calorias_3").toString()

                        /* Guardar Plan en Cache */
                        val result = Alimentacion (
                            alimentacionID = response.optString("id"),
                            calorias1 = calorias1,
                            calorias2 = calorias2,
                            calorias3 = calorias3,
                            date = response.optString("fecha"),
                            ml_agua = response.optInt("ml_agua").toString(),
                            total_calories = (calorias1.toInt() + calorias2.toInt() + calorias3.toInt()).toString()

                        )
                        CacheManager.getInstance(this.requireContext()).addAlimentacion(result)
                        /* Mostar Toast */
                        showMessage(resources.getString(R.string.exito))
                        // Navegar a Home
                        navigateToHome()
                    },
                    {
                        showMessage(resources.getString(R.string.failed_Error).plus(it.networkResponse.statusCode.toString()))
                    },
                    "nutricion/resultados-alimentacion",
                        user.token
                ))
            } else {
                showMessage(resources.getString(R.string.todos_los_campos))
            }
        }

    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_alimentacionResult_to_home2)
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

