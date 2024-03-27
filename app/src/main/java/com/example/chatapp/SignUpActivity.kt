package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatapp.databinding.ActivityLoginBinding
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        mAuth= FirebaseAuth.getInstance()

        binding.signupBtn.setOnClickListener{
            val email=binding.email.text.toString()
            val password=binding.password.text.toString()
            if(binding.password.text.toString().length<6){
                Toast.makeText(this,"Password should have atleast six characters",Toast.LENGTH_SHORT).show()
            }else{
                signup(email , password)
            }
            Log.e("Tag" , email)
            Log.e("Tag" , password)
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

    private fun signup(email:String , password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent= Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this , "SignUp Failed!!" , Toast.LENGTH_SHORT).show()
                }
            }
    }
}