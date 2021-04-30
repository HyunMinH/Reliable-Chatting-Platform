package com.example.chattingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chattingapp.R
import com.example.chattingapp.dto.ChatRoom
import com.example.chattingapp.dto.Message
import com.example.chattingapp.service.MessageApiService
import com.example.chattingapp.service.util.rest.RestApiService
import com.example.chattingapp.service.util.stomp.StompApiService
import io.reactivex.functions.BiConsumer
import java.util.logging.Logger
import kotlin.properties.Delegates

// Main Chatlist type Adapter
class RoomlistAdapter(val context: Context, val roomList: ArrayList<ChatRoom>, val onClickListener: BiConsumer<View, ChatRoom>) : RecyclerView.Adapter<RoomlistAdapter.Holder>() {
    private val logger = Logger.getLogger(RoomlistAdapter::class.java.name)

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var roomId = -1
        val roomnameText = itemView?.findViewById<TextView>(R.id.roomname)
        val curmessageText = itemView?.findViewById<TextView>(R.id.curmessage)
        val recenttimeText = itemView?.findViewById<TextView>(R.id.recenttime)

        fun bind(chatroom: ChatRoom, context: Context) {
            roomId = chatroom.roomId
            roomnameText?.text = chatroom.roomName
            curmessageText?.text = chatroom.curMessage
            recenttimeText?.text = chatroom.recentTime
        }

        fun bind(curMessage:String, time:String){
            curmessageText?.text = curMessage
            recenttimeText?.text = time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.widget_roomlist_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(roomList[position], context)
        holder?.itemView.setOnClickListener{
            onClickListener.accept(it, roomList[position])
        }

        // 바꿔야함 땜빵용
        // change room info when new message received
        val messageApiService =MessageApiService(StompApiService(), RestApiService.instance)
        messageApiService.subscribeRoom(roomList[position].roomId){
            logger.info("get content : ${it.content}")
            logger.info("get time : ${it.time}")

            holder?.bind(it.content, it.time)
            notifyItemChanged(position)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder, position)
            return
        }

        if(payloads.isNotEmpty()){
            for(message in payloads){
                if(message is Message){
                    holder?.bind(message.content, message.time)
                }
            }
        }
    }

    fun addItem(chatrooms: List<ChatRoom>){
        roomList.addAll(chatrooms)
        notifyItemInserted(roomList.size-1);
    }

    override fun getItemCount(): Int {
        return roomList.size
    }
}