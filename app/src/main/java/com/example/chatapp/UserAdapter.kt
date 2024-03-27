package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.LayoutInflaterFactory
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context: Context,val list:List<Users>):RecyclerView.Adapter<UserAdapter.viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view:View=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val currentUser=list[position]
        holder.name.text=currentUser.name
    }

    class viewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name=itemView.findViewById<TextView>(R.id.user_name)
    }
}