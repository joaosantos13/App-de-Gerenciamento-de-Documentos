package com.example.projeto3bim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SecondActivity : AppCompatActivity()
{
    private lateinit var loginButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        FirebaseApp.initializeApp(this)

        loginButton = findViewById(R.id.loginButton)
        emailEditText = findViewById(R.id.emailTextview)
        passwordEditText = findViewById(R.id.senhaTextview)

        loginButton.setOnClickListener {

            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            login(email, password)

        }

    }

    private fun login(email: String, password: String) {
        val intent = Intent(this, ThirdActivity::class.java)
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
                    finish()
                    startActivity(intent)
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
