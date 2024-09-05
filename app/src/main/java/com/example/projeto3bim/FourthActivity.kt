package com.example.projeto3bim

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FourthActivity : AppCompatActivity() {

    private lateinit var alterarDados: Button
    private lateinit var novaSenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        alterarDados = findViewById(R.id.alterarDados)
        novaSenha = findViewById(R.id.novaSenha)

        alterarDados.setOnClickListener{
            val novaSenha = novaSenha.text.toString().trim()

            alterarDados(novaSenha)
        }
    }

    private fun alterarDados(password: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        // Verificar se o usuário está logado
        val user: FirebaseUser? = auth.currentUser

        // Alterar a senha, assumindo que o usuário está logado
            user?.let {
                it.updatePassword(password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Senha alterada com sucesso
                        Toast.makeText(this, "Senha alterada con sucesso: ${user?.email}", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        // Falha ao alterar a senha
                        Toast.makeText(this, "Erro ao alterar: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }