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
import com.sportapp_grupo1.databinding.MainFragmentBinding
import com.sportapp_grupo1.models.User
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.LoginNetworkService
import com.sportapp_grupo1.validator.EmailValidator
import com.sportapp_grupo1.validator.EmptyValidator
import com.sportapp_grupo1.validator.PasswordValidator
import com.sportapp_grupo1.validator.base.BaseValidator
import org.json.JSONObject

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var volleyBroker: LoginNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        volleyBroker = this.context?.let { LoginNetworkService(it) }!!

        binding.recuperar.setOnClickListener {
            showMessage("No es parte del MVP.")
        }

        binding.registro.setOnClickListener {
            showMessage("Registro solo posible en la Plataforma Web.")
        }

        binding.loginButton.setOnClickListener {
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()

            val usernameValidations = BaseValidator.validate(
                EmptyValidator(username), EmailValidator(username)
            )
            binding.inputUsername.error =
                if (!usernameValidations.isSuccess) getString(usernameValidations.message) else null

            val passwordValidations = BaseValidator.validate(
                EmptyValidator(password), PasswordValidator(password)
            )
            binding.inputPassword.error =
                if (!passwordValidations.isSuccess) getString(passwordValidations.message) else null

            if (binding.inputUsername.error == null && binding.inputPassword.error == null ){
                val loginParams = mapOf<String, Any>(
                    "email" to username,
                    "password" to password
                )

                volleyBroker.instance.add(LoginNetworkService.postRequest(JSONObject(loginParams),
                    { response ->
                        /* Guardar User en Cache */
                        val user = User (
                            userId = response.optString("id"),
                            nombres = response.optString("nombres"),
                            rol = response.optString("tipo_usuario"),
                            plan = response.optString("tipo_plan"),
                            token = response.optString("token"),
                            correo = username
                        )
                        CacheManager.getInstance(this.requireContext()).saveUsuario(user)
                        if( user.rol == "Usuario"){
                            /* Mostar Toast */
                            showMessage("Inicio de Sesion Exitoso.")
                            // Navegar a Home
                            findNavController().navigate(R.id.action_mainFragment_to_home2)
                        }else{
                            /* Mostar Toast */
                            showMessage("Usuario no es cliente, proveedores deben usar la Aplicacion Web.")
                        }
                    },
                    {
                        showMessage("Inicio de Sesion Fallido. Error:".plus(it.networkResponse.statusCode.toString()))
                    }))
            } else {
                showMessage("Todos los campos deben ser diligenciados, por favor corrija e intente de nuevo.")
            }

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
    }



}