package com.sportapp_grupo1.test

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sportapp_grupo1.R
import com.sportapp_grupo1.test.utils.clickIntoButtonById
import com.sportapp_grupo1.test.utils.delayService
import com.sportapp_grupo1.test.utils.setTextViewByValue
import com.sportapp_grupo1.ui.MainActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    /**
     * Esta Prueba tiene la intencion de los casos de validacion negativa
     */
    @Test
    fun negativeTestFailedLogin(){
        val editTextUser: ViewInteraction = onView(withId(R.id.input_username))
        val editTextPass: ViewInteraction = onView(withId(R.id.input_password))

        clickIntoButtonById(R.id.login_button)
        editTextUser.check(matches(hasErrorText("El campo no debe estar vacio")))
        editTextPass.check(matches(hasErrorText("El campo no debe estar vacio")))

        setTextViewByValue(R.id.input_username,"shiomar")
        setTextViewByValue(R.id.input_password,"1234")
        clickIntoButtonById(R.id.login_button)
        editTextUser.check(matches(hasErrorText("El usuario debe ser un correo valido")))
        editTextPass.check(matches(hasErrorText("La contraseña debe tener al menos 8 caracteres")))

        setTextViewByValue(R.id.input_username,"s.salazarc@uniandes.edu. co")
        setTextViewByValue(R.id.input_password,"QQZanuV8G8zJ66dPnDiYLicQ4B8Hr5NNXGpVCywJSV6UUHP7Wx4GXzj9ULAMc6g35")
        clickIntoButtonById(R.id.login_button)
        editTextUser.check(matches(hasErrorText("El usuario debe ser un correo valido")))
        editTextPass.check(matches(hasErrorText("La contraseña debe tener maximo 64 caracteres")))

        setTextViewByValue(R.id.input_username,"s.salazarcuniandes.edu")
        setTextViewByValue(R.id.input_password,"123456 89")
        clickIntoButtonById(R.id.login_button)
        editTextUser.check(matches(hasErrorText("El usuario debe ser un correo valido")))
        editTextPass.check(matches(hasErrorText("La contraseña no debe tener espacios en blanco")))

        setTextViewByValue(R.id.input_username,"s.salazarc@uniandesedu")
        setTextViewByValue(R.id.input_password,"12345689A")
        clickIntoButtonById(R.id.login_button)
        editTextUser.check(matches(hasErrorText("El usuario debe ser un correo valido")))
        editTextPass.check(matches(hasErrorText("La contraseña necesita al menos una minuscula")))

        setTextViewByValue(R.id.input_password,"12345689Aa")
        clickIntoButtonById(R.id.login_button)
        editTextPass.check(matches(hasErrorText("La contraseña necesita al menos un simbolo especial")))

        SystemClock.sleep(delayService)
        //Verificamos que sigamos en la pantalla de Inicio de Sesion
        onView(allOf(withId(R.id.login_button), isDisplayed()))
    }

    /**
     * Esta Prueba tiene la intencion de Comprobar que el Login es exitoso
     */
    @Test
    fun positiveTestSuccesfullLogin(){
        setTextViewByValue(R.id.input_username,"s.salazarc@uniandes.edu.co")
        setTextViewByValue(R.id.input_password,"123456789156Aa-")
        clickIntoButtonById(R.id.login_button)
        SystemClock.sleep(delayService)
        //Verificamos que ya no estemos en la pantalla de Inicio de Sesion
        onView(allOf(withId(R.id.login_button), CoreMatchers.not(isDisplayed())))
    }


}