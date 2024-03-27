package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        mAuth= FirebaseAuth.getInstance()

        binding.signupBtn.setOnClickListener{
            val intent= Intent(this , SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginBtn.setOnClickListener{
            login(binding.email.text.toString(), binding.password.text.toString())
        }
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = mAuth.currentUser
//        if (currentUser != null) {
//            val intent=Intent(this , MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this , "Login Successful" , Toast.LENGTH_SHORT).show()
                    val intent=Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this , "User Not Found!" , Toast.LENGTH_SHORT).show()
                }
            }
    }


}