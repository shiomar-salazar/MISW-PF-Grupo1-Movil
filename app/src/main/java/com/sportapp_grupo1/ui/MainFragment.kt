package com.sportapp_grupo1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sportapp_grupo1.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(
            R.layout.main_fragment,
            container, false
        )

        val button1 = view.findViewById<View>(R.id.recuperar) as Button
        button1.setOnClickListener {
            Toast.makeText(activity, "Not implemented yet", Toast.LENGTH_SHORT).show()
        }

        val button2 = view.findViewById<View>(R.id.registro) as Button
        button2.setOnClickListener {
            Toast.makeText(activity, "Registro solo posible en la Plataforma Web", Toast.LENGTH_SHORT).show()
        }

        val button = view.findViewById<View>(R.id.login_button) as Button
        button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_home2)
        }

        return view
    }
}