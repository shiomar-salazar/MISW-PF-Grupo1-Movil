package com.sportapp_grupo1.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var req1 = false
    private var req2 = false

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
        val day = SimpleDateFormat("EEEE", Locale("es","MX")).format(date.time).replace("é","e").replace("á", "a")
        var distance = 0
        var calories_goal = 0

        binding.container.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        val user = CacheManager.getInstance(this.requireContext()).getUsuario()
        binding.header.text = resources.getString(R.string.hola).plus(user.nombres.substringBefore(" "))

        if (user.plan != "Premium"){
            binding.notPremiumText.visibility = View.VISIBLE
            binding.tusEventos.isClickable = false
            binding.tusEventos.visibility = View.INVISIBLE
        }else{
            binding.tusEventos.setOnClickListener {
                findNavController().navigate((R.id.action_home2_to_eventosList))
            }
        }

        volleyBroker.instance.add(
            PlanEntrenamientoNetworkService.getRequest(
                { response ->
                    req1 = true
                    distance = response.getJSONObject("plan_entrenamiento").optString(day).toInt()
                    val duration = response.optInt("numero_semanas").toString().plus(resources.getString(R.string.semanas))
                    binding.distanceDayText.text = distance.toString().plus(resources.getString(R.string.sufix_entrenamiento))
                    binding.planEntreDurationText.text = resources.getString(R.string.quedan).plus(duration)
                    checkProgressBar()
                },
                {
                    req1 = true
                    distance = 0
                    binding.distanceDayText.text = resources.getString(R.string.Vacio)
                    binding.planEntreDurationText.text = resources.getString(R.string.Vacio)
                    checkProgressBar()
                },
                "entrenamientos/plan-entrenamiento/usuario/" + user.userId,
                user.token
            )
        )

        volleyBroker.instance.add(
            PlanAlimentacionNetworkService.getRequest(
            {response ->
                req2 = true
                calories_goal = response.getJSONObject("plan_alimentacion").optInt(day)
                val duration = response.optInt("numero_semanas").toString().plus(resources.getString(R.string.semanas))
                binding.caloriesDayText.text = calories_goal.toString().plus(resources.getString(R.string.sufix_alimentacion))
                binding.planAliDurationText.text = resources.getString(R.string.quedan).plus(duration)
                checkProgressBar()
            },
            {
                req2 = true
                calories_goal = 0
                binding.caloriesDayText.text = resources.getString(R.string.Vacio)
                binding.planAliDurationText.text = resources.getString(R.string.Vacio)
                checkProgressBar()
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
        binding.sugerencias.setOnClickListener {
            findNavController().navigate((R.id.action_home2_to_sugerenciasList))
        }
        binding.planAlimentacion.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_planAlimentacionDetail)
        }

        binding.planEntrenamiento.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_planEntrenamientoDetail)
        }

        binding.fabProfile.setOnClickListener{
            findNavController().navigate(R.id.action_home2_to_profileFragment)
        }
    }

    fun checkProgressBar(){
        if(req1 && req2){
            binding.progressBar.visibility = View.GONE
            binding.container.visibility = View.VISIBLE
        }
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

