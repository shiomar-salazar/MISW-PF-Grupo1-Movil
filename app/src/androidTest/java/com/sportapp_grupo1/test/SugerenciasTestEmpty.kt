package com.sportapp_grupo1.test

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sportapp_grupo1.R
import com.sportapp_grupo1.ui.MainActivity
import com.sportapp_grupo1.utils.CustomAssertions
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SugerenciasTestEmpty {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    //Constante que define el tiempo de espera para que se carguen los datos retornados por el adapter
    val delayService = Integer.toUnsignedLong(5000)
    val delayService2 = Integer.toUnsignedLong(1000)

    fun clickIntoButtonById(idView: Int) {
        //Damos click en el boton idView
        onView(withId(idView)).perform(ViewActions.click())
    }
    fun clickIntoButtonByIdwithScroll(idView: Int) {
        //Damos click en el boton idView
        onView(withId(idView)).perform(ViewActions.scrollTo(), ViewActions.click())
    }

    fun clickIntoButtonByText(idView: Int, valueToSearch: String) {
        //Damos click en el boton idView
        getTextViewByValue(idView, valueToSearch)?.perform(ViewActions.click())
    }

    fun getTextViewByValue(idView: Int, valueToSearch: String): ViewInteraction? {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        return onView(
            AllOf.allOf(
                withId(idView),
                ViewMatchers.withText(valueToSearch)
            )
        )
    }

    fun setTextLayoutViewByValue(idView: Int, valueToType:String) {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        onView(
            AllOf.allOf(
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
            AllOf.allOf(
                withId(idView)
            )
        ).perform(
            ViewActions.click(),
            ViewActions.clearText(),
            ViewActions.typeText(valueToType),
            ViewActions.closeSoftKeyboard()
        )
    }

    private fun validateTextView(idView: Int, valueToSearch: String) {
        //Validamos si es mostrado algun TextView de tipo idView
        onView(AllOf.allOf(withId(idView), ViewMatchers.isDisplayed()))
        //Validamos que no venga vacio algun TextView de tipo idView
        onView(AllOf.allOf(withId(idView), Matchers.not(ViewMatchers.withText(""))))
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        onView(AllOf.allOf(withId(idView), ViewMatchers.withText(valueToSearch)))
    }


    fun navigateToTestScreen(){
        setTextViewByValue(R.id.input_username,"s.salazarc_vacio@uniandes.edu.co")
        setTextViewByValue(R.id.input_password,"123456789156Aa-")
        clickIntoButtonById(R.id.login_button)
        SystemClock.sleep(delayService)
        onView(
            AllOf.allOf(
                withId(R.id.sugerencias),
                ViewMatchers.isDisplayed()
            )
        )
        clickIntoButtonById(R.id.sugerencias)
        SystemClock.sleep(delayService)
        onView(
            AllOf.allOf(
                withId(R.id.title),
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun CheckSugerenciaListMinimumEmpty() {
        navigateToTestScreen()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos que el listado tenga un minimo de albumes
        onView(withId(R.id.sugerencia_item_fragment)).check(
            CustomAssertions.greaterItem(1)
        )
    }

    /**
     * Esta prueba tiene la intencion de comprobar la visualizacion de listado de Sugerencias
     */
    @Test
    fun positiveTestSugerenciasLisEmpty() {
        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()

        //Validamos si el sugerencia_item_fragment es mostrado
        onView(withId(R.id.sugerencia_item_fragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Validamos el textView Title exista
        validateTextView(R.id.title, "Listado de Sugerencias")
    }

    /**
     * Esta prueba tiene la intencion de comprobar la visualizacion de listado de Sugerencias
     */
    @Test
    fun positiveTestSugerenciaDetailEmpty() {
        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()

        /* Damos click en textView con la primera sugerencia */
        onView(withId(R.id.sugerencia_item_fragment)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        SystemClock.sleep(delayService)
        onView(AllOf.allOf(withId(R.id.registrar_btn),Matchers.not(ViewMatchers.isDisplayed() ) ) )
    }

}
