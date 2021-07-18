package com.example.socialapp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialapp2.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostAdapter(options: FirestoreRecyclerOptions<Post> , val listener : IPostAdapter) : FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(
    options
) {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postText : TextView = itemView.findViewById(R.id.postTitle)
        val userText : TextView = itemView.findViewById(R.id.userName)
        val createAt : TextView = itemView.findViewById(R.id.createdAt)
        val likeCount : TextView = itemView.findViewById(R.id.likeCount)
        val userImage : ImageView = itemView.findViewById(R.id.userImage)
        val likeButton : ImageView = itemView.findViewById(R.id.likeButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val holder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false))
        holder.likeButton.setOnClickListener{
            listener.onLikeClicked(snapshots.getSnapshot(holder.adapterPosition).id)
        }
        return holder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.postText.text=model.text
        holder.userText.text=model.createdBy.displayName
        holder.createAt.text = Utils.getTimeAgo(model.createdAt)

        Glide.with(holder.userImage.context)
            .load(model.createdBy.imageUrl)
            .circleCrop()
            .into(holder.userImage)

        holder.likeCount.text = model.likedBy.size.toString()

        val auth = Firebase.auth
        val currentUser = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUser)

        if(isLiked){
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_liked))
        } else{
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context,R.drawable.ic_unliked))
        }

    }
}

interface IPostAdapter{
    fun onLikeClicked(postId : String)
}