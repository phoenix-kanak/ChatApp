package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var userArray:ArrayList<Users>
    private lateinit var mAuth: FirebaseAuth
    private lateinit var userRecyclerView:RecyclerView
    private lateinit var userDb:DatabaseReference
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        mAuth= FirebaseAuth.getInstance()
        userDb=FirebaseDatabase.getInstance().getReference()

        userArray=ArrayList()
        userAdapter=UserAdapter(this , userArray)


        userRecyclerView=binding.userRv
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.adapter=userAdapter

        userDb.child("users").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userArray.clear()
                for(postSnapShot in snapshot.children){
                    val currentUser=postSnapShot.getValue(Users::class.java)
                    if(mAuth.currentUser?.uid!! != currentUser?.uid!!){
                        userArray.add(currentUser)
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout){
            mAuth.signOut()
            val intent=Intent(this,LoginActivity::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}