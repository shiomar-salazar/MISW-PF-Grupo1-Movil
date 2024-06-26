package com.sportapp_grupo1.test

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sportapp_grupo1.R
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

    //Constante que define el tiempo de espera para que se carguen los datos retornados por el adapter
    val delayService2 = Integer.toUnsignedLong(7000)

    fun clickIntoButtonById(idView: Int) {
        //Damos click en el boton idView
        onView(withId(idView)).perform(ViewActions.click())
    }

    fun clickIntoButtonByText(idView: Int, valueToSearch: String) {
        //Damos click en el boton idView
        getTextViewByValue(idView, valueToSearch)?.perform(ViewActions.click())
    }

    fun getTextViewByValue(idView: Int, valueToSearch: String): ViewInteraction? {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        return onView(
            allOf(
                withId(idView),
                ViewMatchers.withText(valueToSearch)
            )
        )
    }

    fun setTextLayoutViewByValue(idView: Int, valueToType:String) {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        onView(
            allOf(
                ViewMatchers.isDescendantOfA(withId(idView)),
                ViewMatchers.withClassName(CoreMatchers.endsWith("EditText"))
            )
        ).perform(
            ViewActions.typeText(valueToType)
        )
    }

    fun setTextViewByValue(idView: Int, valueToType:String) {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        onView(
            allOf(
                withId(idView)
            )
        ).perform(
            ViewActions.click(),
            ViewActions.clearText(),
            ViewActions.typeText(valueToType),
            ViewActions.closeSoftKeyboard()
        )
    }

    /**
     * Esta Prueba tiene la intencion de los casos de validacion negativa
     */
    @Test
    fun negativeTestFailedLogin(){
        val editTextUser: ViewInteraction = onView(withId(R.id.input_username))
        val editTextPass: ViewInteraction = onView(withId(R.id.input_password))

        clickIntoButtonById(R.id.login_button)
        editTextUser.check(matches(hasErrorText("The field cannot be empty")))
        editTextPass.check(matches(hasErrorText("The field cannot be empty")))

        setTextViewByValue(R.id.input_username,"shiomar")
        setTextViewByValue(R.id.input_password,"1234")
        clickIntoButtonById(R.id.login_button)
        editTextUser.check(matches(hasErrorText("Username shall be a valid email")))
        editTextPass.check(matches(hasErrorText("Password shall be at least 8 character long")))

        setTextViewByValue(R.id.input_username,"s.salazarc@uniandes.edu. co")
        setTextViewByValue(R.id.input_password,"QQZanuV8G8zJ66dPnDiYLicQ4B8Hr5NNXGpVCywJSV6UUHP7Wx4GXzj9ULAMc6g35")
        clickIntoButtonById(R.id.login_button)
        editTextUser.check(matches(hasErrorText("Username shall be a valid email")))
        editTextPass.check(matches(hasErrorText("Password shall have at most 64 characters")))

        setTextViewByValue(R.id.input_username,"s.salazarcuniandes.edu")
        setTextViewByValue(R.id.input_password,"123456 89")
        clickIntoButtonById(R.id.login_button)
        editTextUser.check(matches(hasErrorText("Username shall be a valid email")))
        editTextPass.check(matches(hasErrorText("Password field shall not have whitespaces")))

        setTextViewByValue(R.id.input_username,"s.salazarc@uniandesedu")
        setTextViewByValue(R.id.input_password,"12345689A")
        clickIntoButtonById(R.id.login_button)
        editTextUser.check(matches(hasErrorText("Username shall be a valid email")))
        editTextPass.check(matches(hasErrorText("Password shall include at least one Lowercase Letter")))

        setTextViewByValue(R.id.input_password,"12345689Aa")
        clickIntoButtonById(R.id.login_button)
        editTextPass.check(matches(hasErrorText("Password shall include at least one Special Character")))

        SystemClock.sleep(delayService2)
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
        SystemClock.sleep(delayService2)
        //Verificamos que ya no estemos en la pantalla de Inicio de Sesion
        onView(allOf(withId(R.id.login_button), CoreMatchers.not(isDisplayed())))
    }

    /**
     * Esta Prueba tiene la intencion de Comprobar los demas botones de la pantalla de Login
     */
    @Test
    fun Login_OtherBtns(){
        clickIntoButtonById(R.id.recuperar)
        SystemClock.sleep(delayService2)
        clickIntoButtonById(R.id.registro)
        //Verificamos que sigamos en la pantalla de Inicio de Sesion
        onView(allOf(withId(R.id.login_button), isDisplayed()))

    }

    @Test
    fun TestFailedLogin_wrongPassword(){
        setTextViewByValue(R.id.input_username,"s.salazarc@uniandes.edu.co")
        setTextViewByValue(R.id.input_password,"123456789156Aa-123")
        clickIntoButtonById(R.id.login_button)
        SystemClock.sleep(delayService2)
        //Verificamos que ya no estemos en la pantalla de Inicio de Sesion
        onView(allOf(withId(R.id.login_button), isDisplayed()))
    }

    @Test
    fun TestFailedLogin_wrongUser(){
        setTextViewByValue(R.id.input_username,"sh.salazarc@uniandes.edu.co")
        setTextViewByValue(R.id.input_password,"123456789156Aa-")
        clickIntoButtonById(R.id.login_button)
        SystemClock.sleep(delayService2)
        //Verificamos que aun estemos en la pantalla de Inicio de Sesion
        onView(allOf(withId(R.id.login_button), isDisplayed()))
    }

    @Test
    fun TestFailedLogin_wrongUserType(){
        setTextViewByValue(R.id.input_username,"prestador2024@uniandes.edu.co")
        setTextViewByValue(R.id.input_password,"Prestador2*24")
        clickIntoButtonById(R.id.login_button)
        SystemClock.sleep(delayService2)
        //Verificamos que aun estemos en la pantalla de Inicio de Sesion
        onView(allOf(withId(R.id.login_button), isDisplayed()))
    }



}