package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context,val list:List<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_SENT=1
    val ITEM_RECEIVE=2

    class sentViewholder(itemview: View):RecyclerView.ViewHolder(itemview){
        val sent_msg=itemview.findViewById<TextView>(R.id.sent_text)
    }
    class receiveViewholder(itemview: View):RecyclerView.ViewHolder(itemview){
        val receive_msg=itemview.findViewById<TextView>(R.id.receive_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            val view:View=LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return sentViewholder(view)
        }else{
            val view:View=LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return receiveViewholder(view)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMsg=list[position]

        if(FirebaseAuth.getInstance().currentUser?.uid!!.equals(currentMsg.sender)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMsg=list[position]
        if(holder.javaClass==sentViewholder::class.java){
            val viewholder=holder as sentViewholder
            holder.sent_msg.text=currentMsg.message
        }else{
            val viewholder=holder as receiveViewholder
            holder.receive_msg.text=currentMsg.message
        }
    }
}