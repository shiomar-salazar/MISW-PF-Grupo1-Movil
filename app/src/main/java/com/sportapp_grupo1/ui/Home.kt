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
import com.sportapp_grupo1.databinding.HomeFragmentBinding
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.PlanAlimentacionNetworkService
import com.sportapp_grupo1.network.PlanEntrenamientoNetworkService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class Home : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private  lateinit var volleyBroker: PlanEntrenamientoNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CommitPrefEdits", "UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        volleyBroker = this.context?.let { PlanEntrenamientoNetworkService(it) }!!

        val calendar: Calendar = Calendar.getInstance()
        val date: Date = calendar.time
        val day = SimpleDateFormat("EEEE", Locale("es","MX")).format(date.time)
        var distance = 0
        var calories_goal = 0

        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        volleyBroker.instance.add(
            PlanEntrenamientoNetworkService.getRequest(
                { response ->
                    distance =
                        response.getJSONObject("plan_entrenamiento").optString(day).toInt()
                },
                {
                    distance = 0
                },
                "entrenamientos/plan-entrenamiento/usuario/" + user.userId,
                user.token
            )
        )

        volleyBroker.instance.add(
            PlanAlimentacionNetworkService.getRequest(
            {response ->
                calories_goal = response.getJSONObject("plan_alimentacion").optInt(day)
            },
            {
                calories_goal = 0
            },
            "nutricion/plan-nutricional/"+user.userId,
            user.token
        ))

        val sharedPref = activity?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()

        binding.alimentacion.setOnClickListener {
            editor?.apply{
                putInt("alime_goal",calories_goal)
                apply()
            }
            findNavController().navigate((R.id.action_home2_to_alimentacionResultList))
        }
        binding.entrenamiento.setOnClickListener {
            editor?.apply{
                putInt("entre_goal",distance)
                apply()
            }
            findNavController().navigate((R.id.action_home2_to_entrenamiento_Menu))
        }
        binding.tusEventos.setOnClickListener {
            showMessage("Not implemented yet.")
        }
        binding.sugerencias.setOnClickListener {
            showMessage("Not implemented yet.")
        }
        binding.planAlimentacion.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_planAlimentacionDetail)
        }

        binding.planEntrenamiento.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_planEntrenamientoDetail)
        }
    }

    private fun showMessage(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
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

