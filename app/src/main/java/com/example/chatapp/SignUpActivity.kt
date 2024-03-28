package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        supportActionBar?.hide()

        mAuth= FirebaseAuth.getInstance()

        binding.signupBtn.setOnClickListener{
            val email=binding.email.text.toString()
            val password=binding.password.text.toString()
            val name=binding.name.text.toString()
            if(binding.password.text.toString().length<6){
                Toast.makeText(this,"Password should have atleast six characters",Toast.LENGTH_SHORT).show()
            }else {
                mAuth.currentUser?.uid?.let { uid ->
                    signup(name, email, password, uid)
                } ?: run {
                    Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
                }
            }
            Log.e("Tag" , email)
            Log.e("Tag" , password)
            Log.e("Tag" , mAuth.currentUser?.uid !! )
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

    private fun signup(name: String, email: String, password: String , uid:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name , email , uid)
                    val intent= Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this , "SignUp Failed!!" , Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name:String , email:String , uid:String) {
        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("users").child(uid).setValue(Users(name , email , uid))
    }
}