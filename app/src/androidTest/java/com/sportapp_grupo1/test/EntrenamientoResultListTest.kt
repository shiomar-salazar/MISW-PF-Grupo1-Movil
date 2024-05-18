package com.sportapp_grupo1.test

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sportapp_grupo1.R
import com.sportapp_grupo1.ui.MainActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EntrenamientoResultListTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    //Constante que define el tiempo de espera para que se carguen los datos retornados por el adapter
    val delayService2 = Integer.toUnsignedLong(7000)

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
        setTextViewByValue(R.id.input_username,"s.salazarc@uniandes.edu.co")
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

    private fun validateTextView(idView: Int, valueToSearch: String) {
        //Validamos si es mostrado algun TextView de tipo idView
        Espresso.onView(AllOf.allOf(ViewMatchers.withId(idView), ViewMatchers.isDisplayed()))
        //Validamos que no venga vacio algun TextView de tipo idView
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(idView),
                Matchers.not(ViewMatchers.withText(""))
            )
        )
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(idView),
                ViewMatchers.withText(valueToSearch)
            )
        )
    }

    /**
     * Esta Prueba tiene la intencion de verificar el caso de error 404
     */
    @Test
    fun EntrenamientoDetail() {
        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()
        validateTextView(R.id.result_list_actividad, "Atletismo")
        validateTextView(R.id.result_list_fecha, "2024-04-26")
        validateTextView(R.id.result_list_tiempo, "08:10:15")
        validateTextView(R.id.result_list_distancia, "25.0 km")
        validateTextView(R.id.result_list_resultado, "45")


    }

}