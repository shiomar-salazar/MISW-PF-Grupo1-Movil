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
import com.sportapp_grupo1.databinding.MainFragmentBinding
import com.sportapp_grupo1.validator.EmailValidator
import com.sportapp_grupo1.validator.EmptyValidator
import com.sportapp_grupo1.validator.PasswordValidator
import com.sportapp_grupo1.validator.base.BaseValidator
import com.sportapp_grupo1.viewmodels.MainViewModel

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private lateinit var viewModel: MainViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        binding.recuperar.setOnClickListener {
            showMessage("Not implemented yet.")
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

            //Test-Only
            findNavController().navigate(R.id.action_mainFragment_to_home2)

            if (binding.inputUsername.error == null && binding.inputPassword.error == null ){
                showMessage("Inicio de Sesion Exitoso.")
                viewModel.login(username,password)
                // Navegar a Home
                findNavController().navigate(R.id.action_mainFragment_to_home2)
            } else {
                showMessage("Inicio de Sesion Fallido.")
            }

        }


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
            MainViewModel.Factory(activity.application)
        )[MainViewModel::class.java]
        viewModel.token.observe(viewLifecycleOwner) {
            it.apply {

            }
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }



}