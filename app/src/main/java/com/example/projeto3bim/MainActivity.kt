package com.example.projeto3bim

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        FirebaseApp.initializeApp(this)
        emailEditText = findViewById(R.id.emailTextview)
        passwordEditText = findViewById(R.id.senhaTextview)
        registerButton = findViewById(R.id.enviarButton)

        registerButton.setOnClickListener {

            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            register(email, password)

        }

        val buttonLink = findViewById<TextView>(R.id.button_link)
        buttonLink.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }


    }

    private fun register(email: String, password: String)
    {
        val intent = Intent(this, ThirdActivity::class.java)
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword (email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registro bem-sucedido
                    val user = auth.currentUser
                    Toast.makeText(this, "Registrado com sucesso: ${user?.email}", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
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
}