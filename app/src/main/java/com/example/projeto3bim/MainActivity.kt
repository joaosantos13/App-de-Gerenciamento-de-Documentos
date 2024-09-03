package com.example.projeto3bim

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var nomeEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        FirebaseApp.initializeApp(this)
        nomeEditText = findViewById(R.id.nomeTextview)
        emailEditText = findViewById(R.id.emailTextview)
        passwordEditText = findViewById(R.id.senhaTextview)
        registerButton = findViewById(R.id.enviarButton)
        //LoginButton = findViewById(R.id.loginButton)

        registerButton.setOnClickListener {

            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            register(email, password)

        }

        loginButton.setOnClickListener {

            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            login(email, password)

        }

    }

    private fun register(email: String, password: String)
    {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword (email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                // Registro bem-sucedido
                val user = auth.currentUser
                Toast.makeText(this, "Registrado com sucesso: ${user?.email}", Toast.LENGTH_SHORT).show()
                } else {
                    // Falha no registro
                    Toast.makeText(
                        this,
                        "Erro ao registrar: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun login(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login bem-sucedido
                    val user = auth.currentUser
                    Toast.makeText(
                        this, "Logado com sucesso: ${user?.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Falha no login
                    Toast.makeText(
                        this, "Erro ao logar: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}