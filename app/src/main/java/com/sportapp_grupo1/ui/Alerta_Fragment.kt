package com.sportapp_grupo1.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sportapp_grupo1.R
import com.sportapp_grupo1.databinding.AlertaFragmentBinding
import com.sportapp_grupo1.network.CacheManager
import com.sportapp_grupo1.network.EntrenamientoNetworkService
import org.json.JSONObject


class Alerta_Fragment : Fragment() {
    private var _binding: AlertaFragmentBinding? = null
    private val binding get() = _binding !!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    private  lateinit var volleyBroker: EntrenamientoNetworkService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlertaFragmentBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        volleyBroker = this.context?.let { EntrenamientoNetworkService(it) }!!

        getLocation()
        binding.regresarBtn.setOnClickListener {
            findNavController().navigate((R.id.action_alerta_Fragment_to_home2))
        }

        binding.reintentarBtn.setOnClickListener {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    binding.latitudText.text = location.latitude.toString()
                    binding.longitudText.text = location.longitude.toString()

                    val user = CacheManager.getInstance(this.requireContext()).getUsuario()
                    val params = mapOf(
                        "latitud" to location.latitude.toString(),
                        "longitud" to location.longitude.toString(),
                        "descripcion" to "Alerta de Emergencia durante Entrenamiento"
                    )

                    volleyBroker.instance.add(
                        EntrenamientoNetworkService.postRequestAlerta(
                            JSONObject(params),
                            {
                                /* Mostar Toast */
                                showMessage(resources.getString(R.string.exito))
                            },
                            {
                                showMessage(resources.getString(R.string.failed_Error).plus(it.networkResponse.statusCode.toString()))
                            },
                            user.token
                        ))
                }
            }
        fusedLocationClient.lastLocation.addOnFailureListener {
            Log.d("Error en Ubicacion", it.toString())
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
        val mLocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getLocation(){
        if (checkPermissions() && isLocationEnabled()) {
            getLastKnownLocation()
        } else if (!isLocationEnabled()) {
            showMessage(resources.getString(R.string.location_services))
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
            binding.latitudText.text = R.string.reintentar_text.toString()
            binding.longitudText.text = R.string.reintentar_text.toString()
        } else {
            requestPermissions()
            binding.latitudText.text = R.string.reintentar_text.toString()
            binding.longitudText.text = R.string.reintentar_text.toString()
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