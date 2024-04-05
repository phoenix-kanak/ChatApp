package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var binding:ActivityChatBinding
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var messageList:ArrayList<Message>
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var dbRef:DatabaseReference

    var receiver_room:String?=null
    var sender_room:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val name=intent.getStringExtra("name")
        val receiverUid=intent.getStringExtra("uid")
        dbRef=FirebaseDatabase.getInstance().getReference()
        val senderUid= FirebaseAuth.getInstance().currentUser?.uid

        sender_room=receiverUid+senderUid
        receiver_room=senderUid+receiverUid

        supportActionBar?.title=name

        messageBox=binding.messagebox
        messageRecyclerView=binding.chatRv
        messageList= ArrayList()
        messageAdapter=MessageAdapter(this,messageList)

        messageRecyclerView.layoutManager=LinearLayoutManager(this)
        messageRecyclerView.adapter=messageAdapter


        dbRef.child("chats").child(sender_room!!).child("messages").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val message=postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.sensBtn.setOnClickListener{
            val message=messageBox.text.toString()
            val messageObject=Message(message,senderUid)

            dbRef.child("chats").child(sender_room!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                dbRef.child("chats").child(receiver_room!!).child("messages").push().setValue(messageObject)
            }
            messageBox.setText("")
        }
        binding.attachmentBtn.setOnClickListener{

        }



    }
}