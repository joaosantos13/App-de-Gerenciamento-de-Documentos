package com.example.projeto3bim

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage


class ThirdActivity : AppCompatActivity() {

    private lateinit var selecionarArquivo: Button
    private lateinit var enviarArquivo: Button
    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null
    private lateinit var storage: FirebaseStorage

    companion object
    {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        storage = Firebase.storage

        selecionarArquivo = findViewById(R.id.selecionarArquivo)
        enviarArquivo = findViewById(R.id.enviarArquivo)
        imageView = findViewById(R.id.imageView)

        selecionarArquivo.setOnClickListener{
            openFileChooser()
        }

        enviarArquivo.setOnClickListener{
            imageUri?.let{ uri ->
                uploadImageToFirebase(uri)
            } ?: run {
                Toast.makeText(this, "no image selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${fileUri.lastPathSegment}")
        val uploadTask = imageRef.putFile(fileUri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                Log.d("Firebase", "Image upload successful. Download URL: $uri")
                Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Image upload failed", exception)
            Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openFileChooser() {
        val intent = Intent().apply{
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data !=null){
            imageUri = data.data
            imageView.setImageURI(imageUri)
        }
    }

}