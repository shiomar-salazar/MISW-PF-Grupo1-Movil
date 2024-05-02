package com.sportapp_grupo1.test

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.sportapp_grupo1.R
import com.sportapp_grupo1.ui.MainActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test

class EntrenamientoMonitoreoTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @JvmField
    @Rule
    val location: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION )

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
        SystemClock.sleep(delayService)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.entrenamiento),
                ViewMatchers.isDisplayed()
            )
        )
        /* Llegar a Entrenamiento Menu */
        clickIntoButtonById(R.id.entrenamiento)
        SystemClock.sleep(delayService)
        /* Llegar a Entrenamiento Results */
        clickIntoButtonById(R.id.monitoreoBtn)
        SystemClock.sleep(delayService)
    }

    @Test
    fun Monitoreo_Test_Positive() {
        navigateToTestScreen()

        /* Iniciamos el Monitoreo */
        clickIntoButtonById(R.id.monitoreoBtn)
        /* Validamos botones */
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.alertaBtn),
                ViewMatchers.isClickable()
            )
        )
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.terminarBtn),
                ViewMatchers.isClickable()
            )
        )
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.monitoreoBtn),
                ViewMatchers.isNotClickable()
            )
        )
        /* Esperamos que aumente el contador */
        SystemClock.sleep(delayService)
        SystemClock.sleep(delayService)
        clickIntoButtonById(R.id.terminarBtn)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.registrar),
                ViewMatchers.isClickable()
            )
        )
    }

    @Test
    fun Monitoreo_Test_Alerta() {
        navigateToTestScreen()

        /* Iniciamos el Monitoreo */
        clickIntoButtonById(R.id.monitoreoBtn)
        /* Validamos botones */
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.alertaBtn),
                ViewMatchers.isClickable()
            )
        )
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.terminarBtn),
                ViewMatchers.isClickable()
            )
        )
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.monitoreoBtn),
                ViewMatchers.isNotClickable()
            )
        )
        /* Esperamos que aumente el contador */
        SystemClock.sleep(delayService)
        SystemClock.sleep(delayService)
        clickIntoButtonById(R.id.alertaBtn)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.regresarBtn),
                ViewMatchers.isClickable()
            )
        )
        SystemClock.sleep(delayService)
        SystemClock.sleep(delayService)
        SystemClock.sleep(delayService)
        clickIntoButtonById(R.id.reintentarBtn)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.regresarBtn),
                ViewMatchers.isClickable()
            )
        )
        SystemClock.sleep(delayService)
        SystemClock.sleep(delayService)
        SystemClock.sleep(delayService)
        clickIntoButtonById(R.id.regresarBtn)
        SystemClock.sleep(delayService)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.entrenamiento),
                ViewMatchers.isDisplayed()
            )
        )
    }
}