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
import com.sportapp_grupo1.databinding.PlanAlimentacionDetailFragmentBinding
import com.sportapp_grupo1.models.PlanAlimentacion
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.PlanAlimentacionNetworkService

class PlanAlimentacionDetail : Fragment() {


    private var _binding: PlanAlimentacionDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private  lateinit var volleyBroker: PlanAlimentacionNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlanAlimentacionDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        volleyBroker = this.context?.let { PlanAlimentacionNetworkService(it) }!!

        binding.crear.setOnClickListener {
            navigateToCreatePlan()
        }

        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        volleyBroker.instance.add(PlanAlimentacionNetworkService.getRequest(
            {response ->
                /* Guardar Plan en Cache */
                val plan = PlanAlimentacion (
                    planAlimentacionID = response.optString("id"),
                    lunes = response.getJSONObject("plan_alimentacion").optString("lunes"),
                    martes = response.getJSONObject("plan_alimentacion").optString("martes"),
                    miercoles = response.getJSONObject("plan_alimentacion").optString("miercoles"),
                    jueves = response.getJSONObject("plan_alimentacion").optString("jueves"),
                    viernes = response.getJSONObject("plan_alimentacion").optString("viernes"),
                    sabado = response.getJSONObject("plan_alimentacion").optString("sabado"),
                    domingo = response.getJSONObject("plan_alimentacion").optString("domingo"),
                    numero_semanas = response.optInt("numero_semanas")
                )
                binding.lunesDetail.text = plan.lunes.plus(resources.getString(R.string.sufix_alimentacion))
                binding.martesDetail.text = plan.martes.plus(resources.getString(R.string.sufix_alimentacion))
                binding.miercolesDetail.text = plan.miercoles.plus(resources.getString(R.string.sufix_alimentacion))
                binding.juevesDetail.text = plan.jueves.plus(resources.getString(R.string.sufix_alimentacion))
                binding.viernesDetail.text = plan.viernes.plus(resources.getString(R.string.sufix_alimentacion))
                binding.sabadoDetail.text = plan.sabado.plus(resources.getString(R.string.sufix_alimentacion))
                binding.domingoDetail.text = plan.domingo.plus(resources.getString(R.string.sufix_alimentacion))
                binding.semanasDetail.text = plan.numero_semanas.toString()
                showMessage("Carga Exitosa.")
            },
            {
                if(it.networkResponse.statusCode == 404){
                    showMessage("Usuario no tiene datos cargados aun.")
                    binding.lunesDetail.text = getString(R.string.sin_datos)
                    binding.martesDetail.text = getString(R.string.sin_datos)
                    binding.miercolesDetail.text = getString(R.string.sin_datos)
                    binding.juevesDetail.text = getString(R.string.sin_datos)
                    binding.viernesDetail.text = getString(R.string.sin_datos)
                    binding.sabadoDetail.text = getString(R.string.sin_datos)
                    binding.domingoDetail.text = getString(R.string.sin_datos)
                    binding.semanasDetail.text = getString(R.string.sin_datos)
                }
                else
                {
                    showMessage("Carga Fallida. Error:".plus(it.networkResponse.statusCode.toString()))
                }
            },
            "nutricion/plan-nutricional/"+user.userId,
            user.token
        ))

    }

    private fun navigateToCreatePlan() {
        findNavController().navigate(R.id.action_planAlimentacionDetail_to_planAlimentacionCreate)
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