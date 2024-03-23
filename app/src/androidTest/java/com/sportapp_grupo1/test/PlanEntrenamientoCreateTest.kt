package com.sportapp_grupo1.test

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sportapp_grupo1.R
import com.sportapp_grupo1.test.utils.clickIntoButtonById
import com.sportapp_grupo1.test.utils.delayService
import com.sportapp_grupo1.test.utils.setTextViewByValue
import com.sportapp_grupo1.ui.MainActivity
import org.hamcrest.core.AllOf
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


    fun navigateToTestScreen(){
        setTextViewByValue(R.id.input_username,"s.salazarc@uniandes.edu.co")
        setTextViewByValue(R.id.input_password,"123456789156Aa-")
        clickIntoButtonById(R.id.login_button)
        SystemClock.sleep(delayService)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.plan_entrenamiento),
                ViewMatchers.isDisplayed()
            )
        )
        clickIntoButtonById(R.id.plan_entrenamiento)
    }

    /**
     * Esta Prueba tiene la intencion de los casos de validacion negativa
     */
    @Test
    fun negativeTestFailedPlanEntrenamientoCreate(){
        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()

        val lunesTextEdit: ViewInteraction =
            Espresso.onView(ViewMatchers.withId(R.id.lunes))
        val martesTextEdit: ViewInteraction =
            Espresso.onView(ViewMatchers.withId(R.id.martes))
        val miercolesTextEdit: ViewInteraction =
            Espresso.onView(ViewMatchers.withId(R.id.miercoles))
        val juevesTextEdit: ViewInteraction =
            Espresso.onView(ViewMatchers.withId(R.id.jueves))
        val viernesTextEdit: ViewInteraction =
            Espresso.onView(ViewMatchers.withId(R.id.viernes))
        val sabadoTextEdit: ViewInteraction =
            Espresso.onView(ViewMatchers.withId(R.id.sabado))
        val domingoTextEdit: ViewInteraction =
            Espresso.onView(ViewMatchers.withId(R.id.domingo))
        val semanasTextEdit: ViewInteraction =
            Espresso.onView(ViewMatchers.withId(R.id.semanas))
        clickIntoButtonById(R.id.crear)
        lunesTextEdit.check(ViewAssertions.matches(ViewMatchers.hasErrorText("El campo no debe estar vacio")))
        martesTextEdit.check(ViewAssertions.matches(ViewMatchers.hasErrorText("El campo no debe estar vacio")))
        miercolesTextEdit.check(ViewAssertions.matches(ViewMatchers.hasErrorText("El campo no debe estar vacio")))
        juevesTextEdit.check(ViewAssertions.matches(ViewMatchers.hasErrorText("El campo no debe estar vacio")))
        viernesTextEdit.check(ViewAssertions.matches(ViewMatchers.hasErrorText("El campo no debe estar vacio")))
        sabadoTextEdit.check(ViewAssertions.matches(ViewMatchers.hasErrorText("El campo no debe estar vacio")))
        domingoTextEdit.check(ViewAssertions.matches(ViewMatchers.hasErrorText("El campo no debe estar vacio")))
        semanasTextEdit.check(ViewAssertions.matches(ViewMatchers.hasErrorText("El campo no debe estar vacio")))
        SystemClock.sleep(delayService)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.crear),
                ViewMatchers.isDisplayed()
            )
        )
    }

    /**
     * Esta Prueba tiene la intencion de Comprobar que el Login es exitoso
     */
    @Test
    fun positiveTestSuccesfullPlanEntrenamientoCreate(){
        /* Primero navegamos a la pantalla correcta */
        navigateToTestScreen()
        setTextViewByValue(R.id.lunes,"8")
        setTextViewByValue(R.id.martes,"12")
        setTextViewByValue(R.id.miercoles,"0")
        setTextViewByValue(R.id.jueves,"5")
        setTextViewByValue(R.id.viernes,"0")
        setTextViewByValue(R.id.sabado,"10")
        setTextViewByValue(R.id.domingo,"8")
        setTextViewByValue(R.id.semanas,"25")
        clickIntoButtonById(R.id.crear)
        SystemClock.sleep(delayService)
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.plan_entrenamiento),
                ViewMatchers.isDisplayed()
            )
        )
    }

}