package com.sportapp_grupo1.test

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sportapp_grupo1.R
import com.sportapp_grupo1.ui.MainActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EntrenamientoResultListEmptyTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    //Constante que define el tiempo de espera para que se carguen los datos retornados por el adapter
    val delayService2 = Integer.toUnsignedLong(8500)

    fun clickIntoButtonById(idView: Int) {
        //Damos click en el boton idView
        Espresso.onView(ViewMatchers.withId(idView)).perform(ViewActions.click())
    }
    fun clickIntoButtonByIdwithScroll(idView: Int) {
        //Damos click en el boton idView
        Espresso.onView(ViewMatchers.withId(idView)).perform(ViewActions.scrollTo(), ViewActions.click())
    }

    fun clickIntoButtonByText(idView: Int, valueToSearch: String) {
        //Damos click en el boton idView
        getTextViewByValue(idView, valueToSearch)?.perform(ViewActions.click())
    }

    fun getTextViewByValue(idView: Int, valueToSearch: String): ViewInteraction? {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        return Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(idView),
                ViewMatchers.withText(valueToSearch)
            )
        )
    }

    fun setTextLayoutViewByValue(idView: Int, valueToType:String) {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.isDescendantOfA(ViewMatchers.withId(idView)),
                ViewMatchers.withClassName(CoreMatchers.endsWith("EditText"))
            )
        ).perform(
            ViewActions.typeText(valueToType)
        )
    }

    fun setTextViewByValue(idView: Int, valueToType:String) {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(idView)
            )
        ).perform(
            ViewActions.click(),
            ViewActions.clearText(),
            ViewActions.typeText(valueToType),
            ViewActions.closeSoftKeyboard()
        )
    }


    fun navigateToTestScreen(){
        setTextViewByValue(R.id.input_username,"s.salazarc_vacio@uniandes.edu.co")
        setTextViewByValue(R.id.input_password,"123456789156Aa-")
        clickIntoButtonById(R.id.login_button)
        SystemClock.sleep(delayService2)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.entrenamiento),
                ViewMatchers.isDisplayed()
            )
        )
        /* Llegar a Entrenamiento Menu */
        clickIntoButtonById(R.id.entrenamiento)
        SystemClock.sleep(delayService2)
        /* Llegar a Entrenamiento Results */
        clickIntoButtonById(R.id.result_list_btn)
        SystemClock.sleep(delayService2)
    }

    /**
     * Esta Prueba tiene la intencion de verificar el caso de error 404
     */
    @Test
    fun EntrenamientoDetailEmpty(){
        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()

        Espresso.onView(ViewMatchers.withId(R.id.result_list_actividad)).check(
            ViewAssertions.matches(
                ViewMatchers.withText("No Data")
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.result_list_fecha)).check(
            ViewAssertions.matches(
                ViewMatchers.withText("No Data")
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.result_list_tiempo)).check(
            ViewAssertions.matches(
                ViewMatchers.withText("No Data")
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.result_list_distancia)).check(
            ViewAssertions.matches(
                ViewMatchers.withText("No Data")
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.result_list_resultado)).check(
            ViewAssertions.matches(
                ViewMatchers.withText("No Data")
            )
        )
    }

}