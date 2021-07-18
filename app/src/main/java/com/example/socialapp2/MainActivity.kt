package com.example.socialapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialapp2.dao.PostDao
import com.example.socialapp2.databinding.ActivityMainBinding
import com.example.socialapp2.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity(), IPostAdapter {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: PostAdapter
    private lateinit var postDao : PostDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.fab.setOnClickListener {
            val intent = Intent(this,CreatePostActivity::class.java)
            startActivity(intent)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        postDao = PostDao()
        val postCollection = postDao.postCollections
        val query = postCollection.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        adapter = PostAdapter(recyclerViewOptions,this)

        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
        Log.i("MainActivity","Adapter Started Listening.")
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
        Log.i("MainActivity","Adapter Stopped Listening.")
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}