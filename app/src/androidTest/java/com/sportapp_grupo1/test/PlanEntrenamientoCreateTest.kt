package com.sportapp_grupo1.test

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.android.material.textfield.TextInputLayout
import com.sportapp_grupo1.R
import com.sportapp_grupo1.ui.MainActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.core.AllOf
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class PlanEntrenamientoCreateTest {

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
                ViewMatchers.withId(R.id.plan_entrenamiento),
                ViewMatchers.isDisplayed()
            )
        )
        clickIntoButtonByIdwithScroll(R.id.plan_entrenamiento)
        SystemClock.sleep(delayService2)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.crear),
                ViewMatchers.isDisplayed()
            )
        )
        clickIntoButtonByIdwithScroll(R.id.crear)
    }

    /**
     * Esta Prueba tiene la intencion de los casos de validacion negativa
     */
    @Test
    fun negativeTestFailedPlanEntrenamientoCreate(){
        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()

        clickIntoButtonByIdwithScroll(R.id.crear)
        Espresso.onView(ViewMatchers.withId(R.id.lunes)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            assertEquals(actualError, "The field cannot be empty")
        }
        Espresso.onView(ViewMatchers.withId(R.id.martes)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            assertEquals(actualError, "The field cannot be empty")
        }
        Espresso.onView(ViewMatchers.withId(R.id.miercoles)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            assertEquals(actualError, "The field cannot be empty")
        }
        Espresso.onView(ViewMatchers.withId(R.id.jueves)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            assertEquals(actualError, "The field cannot be empty")
        }
        Espresso.onView(ViewMatchers.withId(R.id.viernes)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            assertEquals(actualError, "The field cannot be empty")
        }
        Espresso.onView(ViewMatchers.withId(R.id.sabado)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            assertEquals(actualError, "The field cannot be empty")
        }
        Espresso.onView(ViewMatchers.withId(R.id.domingo)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            assertEquals(actualError, "The field cannot be empty")
        }
        Espresso.onView(ViewMatchers.withId(R.id.semanas)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            assertEquals(actualError, "The field cannot be empty")
        }
        SystemClock.sleep(delayService2)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.crear),
                ViewMatchers.isDisplayed()
            )
        )
    }

    /**
     * Esta Prueba tiene la intencion de Comprobar que la creacion del Plan de Entrenamiento
     */
    @Test
    fun positiveTestSuccesfullPlanEntrenamientoCreate(){
        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()
        setTextViewByValue(R.id.lunes_text,"8")
        setTextViewByValue(R.id.martes_text,"12")
        setTextViewByValue(R.id.miercoles_text,"0")
        setTextViewByValue(R.id.jueves_text,"5")
        setTextViewByValue(R.id.viernes_text,"0")
        setTextViewByValue(R.id.sabado_text,"10")
        setTextViewByValue(R.id.domingo_text,"8")
        setTextViewByValue(R.id.semana_text,"25")
        clickIntoButtonByIdwithScroll(R.id.crear)
        SystemClock.sleep(delayService2)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.plan_entrenamiento),
                ViewMatchers.isDisplayed()
            )
        )
    }

    /**
     * Esta Prueba tiene la intencion de Comprobar la correcta funcionalidad del Boton de cancelar
     */
    @Test
    fun cancelarBtnPlanEntrenamientoCreate(){

        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()
        /* Hacemos click en boton de cancelar */
        clickIntoButtonByIdwithScroll(R.id.cancelar)
        /* Validamos estar en pantalla de Home */
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.plan_entrenamiento),
                ViewMatchers.isDisplayed()
            )
        )
    }

}