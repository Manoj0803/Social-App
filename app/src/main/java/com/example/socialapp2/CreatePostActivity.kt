package com.example.socialapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.socialapp2.dao.PostDao
import com.example.socialapp2.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreatePostBinding
    private lateinit var postDao : PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_post)
        postDao= PostDao()
        binding.postButton.setOnClickListener {
            val input = binding.postInputEditText.text.toString()

            if(input.isNotEmpty()){
                postDao.addPost(input)
                finish()
            }

        }

    }
}