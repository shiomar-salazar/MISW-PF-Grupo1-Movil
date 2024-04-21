package com.sportapp_grupo1.test

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
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
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.core.AllOf
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class EntrenamientoResultCreateTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    //Constante que define el tiempo de espera para que se carguen los datos retornados por el adapter
    val delayService = Integer.toUnsignedLong(5000)
    val delayService2 = Integer.toUnsignedLong(1000)

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
        clickIntoButtonById(R.id.entre_result_btn)
        SystemClock.sleep(delayService2)
    }

    /**
     * Esta Prueba tiene la intencion de Comprobar la correcta funcionalidad del Boton de cancelar
     */
    @Test
    fun cancelarBtnEntrenamientoResult(){

        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()
        /* Hacemos click en boton de cancelar */
        clickIntoButtonByIdwithScroll(R.id.cancelar)
        /* Validamos estar en pantalla de Home */
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.entrenamiento),
                ViewMatchers.isDisplayed()
            )
        )
    }
    /**
     * Esta Prueba tiene la intencion de Comprobar que el registro del Entrenamiento es Exitoso
     */
    @Test
    fun positiveTestSuccesfullEntrenamientoResult(){
        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()
        /* Ingresamos Datos a Campos */
        setTextViewByValue(R.id.date_text,"2024-04-15")
        setTextViewByValue(R.id.tiempo_text,"12:34:56")
        setTextViewByValue(R.id.result_text,"30")
        clickIntoButtonByIdwithScroll(R.id.registrar)
        SystemClock.sleep(delayService2)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.entrenamiento),
                ViewMatchers.isDisplayed()
            )
        )
    }

    /**
     * Esta Prueba tiene la intencion de Comprobar que el registro del Entrenamiento es Exitoso
     */
    @Test
    fun positiveTestSuccesfullEntrenamientoResult_Ciclismo(){
        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()
        /* Ingresamos Datos a Campos */

        onView(ViewMatchers.withId(R.id.actividad_spinner)).perform(ViewActions.click());
        onData(AllOf.allOf(`is`(instanceOf(String::class.java)))).atPosition(1).perform(ViewActions.click())

        setTextViewByValue(R.id.date_text,"2024-04-15")
        setTextViewByValue(R.id.tiempo_text,"12:34:56")
        setTextViewByValue(R.id.result_text,"30")
        clickIntoButtonByIdwithScroll(R.id.registrar)
        SystemClock.sleep(delayService2)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.entrenamiento),
                ViewMatchers.isDisplayed()
            )
        )
    }

    /**
     * Esta Prueba tiene la intencion de los casos de validacion negativa
     */
    @Test
    fun negativeTestFailedEntrenamientoResult() {
        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()
        /* Hacemos click en boton de registrar */
        clickIntoButtonByIdwithScroll(R.id.registrar)

        /* Validamos respuestas negativas */
        Espresso.onView(ViewMatchers.withId(R.id.tiempo)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            Assert.assertEquals(actualError, "El campo no debe estar vacio")
        }
        Espresso.onView(ViewMatchers.withId(R.id.result)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            Assert.assertEquals(actualError, "El campo no debe estar vacio")
        }
        Espresso.onView(ViewMatchers.withId(R.id.date)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            Assert.assertEquals(actualError, "El campo no debe estar vacio")
        }

        /* Validamos mensaje de error del campo tiempo */
        setTextViewByValue(R.id.tiempo_text,"12:34.56")
        clickIntoButtonByIdwithScroll(R.id.registrar)
        Espresso.onView(ViewMatchers.withId(R.id.tiempo)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            Assert.assertEquals(actualError, "El tiempo tiene que estar en formato XX:YY:ZZ")
        }
        /* Validamos seguir en la pantalla de Resultado de Entrenamiento */
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.registrar),
                ViewMatchers.isDisplayed()
            )
        )
    }

    /*TODO: Remove this after Monitoring HU is implemented */
    @Test
    fun monitoring_Btn_Test() {
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
        clickIntoButtonById(R.id.monitoreoBtn)
        SystemClock.sleep(delayService2)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.monitoreoBtn),
                ViewMatchers.isDisplayed()
            )
        )
    }

}