package com.example.b2023gr2sw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class HFirebaseUIAuth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hfirebase_uiauth)

        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )
            val logearseIntent =  AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build()
            respuestaLoginAuthUi.launch(logearseIntent)
        }

        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.setOnClickListener {seDeslogeo()}

        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario != null){
            val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
            val btnLogin: Button = findViewById(R.id.btn_login_firebase)
            val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
            btnLogin.visibility = View.INVISIBLE
            btnLogout.visibility = View.VISIBLE
            tvBienvenido.text = usuario.displayName
        }
    }

    private fun seDeslogeo() {
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text = "Bienvenido"
        btnLogin.visibility = View.VISIBLE
        btnLogout.visibility = View.INVISIBLE
        FirebaseAuth.getInstance().signOut()
    }

    private val respuestaLoginAuthUi = registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
        res: FirebaseAuthUIAuthenticationResult->
        if (res.resultCode === RESULT_OK) {
            if (res.idpResponse != null){
                seLogeo(res.idpResponse!!)
            }
        }
    }

    private fun seLogeo(res: IdpResponse) {
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido =  findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text = FirebaseAuth.getInstance().currentUser?.displayName
        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        if (res.isNewUser == true){
            registrarUsuarioPorPrimeraVez(res)
        }
    }

    private fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse) {
        TODO("Not yet implemented")
    }
}