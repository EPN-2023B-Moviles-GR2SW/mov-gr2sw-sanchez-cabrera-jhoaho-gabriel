package com.example.proyecto_02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.proyecto_02.views.HomeView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )
            // Construimos el intent de login
            val logearseIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            // RESPUESTA DEL INTENT DE LOGIN
            respuestaLoginAuthUi.launch(logearseIntent)
            // https://console.firebase.google.com/u/0/project/PROYECTO/authentication/settings
            // Authentication/Settings/UserActions/Email enumeration protection ( )
            // Unchecked!!
        }

        //saltarLogin()

        /*
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.setOnClickListener { seDeslogeo() }
        // Logica si destruye el aplicativo
        val usuario = FirebaseAuth.getInstance().currentUser
        if(usuario != null){
            val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
            val btnLogin: Button = findViewById<Button>(R.id.btn_login_firebase)
            val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
            btnLogout.visibility = View.VISIBLE
            btnLogin.visibility = View.INVISIBLE
            tvBienvenido.text = usuario.displayName
        }*/
    }

    override fun onResume() {
        super.onResume()
        //saltarLogin()
    }

    override fun onRestart() {
        super.onRestart()
        //saltarLogin()
    }

    //Callback del INTENT de LOGIN
    private val respuestaLoginAuthUi = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode === RESULT_OK) {
            if (res.idpResponse != null) {
                // Logica de negocio
                seLogeo(res.idpResponse!!)
            }
        }
    }

    fun seLogeo(
        res: IdpResponse
    ) {
        if (res.isNewUser == true) {
            registrarUsuarioPorPrimeraVez(res)
        }
        irActividad(HomeView::class.java)
    }

    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse) {
        /*
         usuario.email;
         usuario.phoneNumber;
         usuario.user.name;
         */
    }

    fun saltarLogin() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            irActividad(HomeView::class.java)
        }
    }

    /*
    fun seDeslogeo(){
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text = "Bienvenido"
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
        FirebaseAuth.getInstance().signOut()
    }*/

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}