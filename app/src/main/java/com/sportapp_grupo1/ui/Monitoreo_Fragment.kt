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
import com.sportapp_grupo1.databinding.MonitoreoFragmentBinding


class Monitoreo_Fragment : Fragment() {

    private var _binding: MonitoreoFragmentBinding? = null
    private val binding get() = _binding !!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MonitoreoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CommitPrefEdits", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()

        binding.goal.text = sharedPref!!.getInt("entre_goal",0).toString().plus(R.string.sufix_entrenamiento.toString())

        binding.monitoreoBtn.setOnClickListener {
            binding.monitoreoBtn.visibility = View.INVISIBLE
            binding.monitoreoBtn.isClickable = false
            binding.alertaBtn.visibility = View.VISIBLE
            binding.alertaBtn.isClickable = true
            binding.terminarBtn.visibility = View.VISIBLE
            binding.terminarBtn.isClickable = true
            binding.textTimer.start()

        }

        binding.alertaBtn.setOnClickListener {
            binding.textTimer.stop()
            findNavController().navigate((R.id.action_monitoreo_Fragment_to_alerta_Fragment))
        }

        binding.terminarBtn.setOnClickListener {
            binding.textTimer.stop()
            val time: String = if(binding.textTimer.text.toString().length == 5){
                "00:".plus(binding.textTimer.text.toString())
            } else {
                "0".plus(binding.textTimer.text.toString())
            }
            showMessage(time)
            editor?.apply{
                putString("time_result", time)
                apply()
            }
            findNavController().navigate(R.id.action_monitoreo_Fragment_to_entrenamientoResult)
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