package com.example.doggoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.example.doggoapp.application.ImageApplication
import com.example.doggoapp.databinding.ActivityLastImageBinding
import com.example.doggoapp.network.DogViewModel
import com.example.doggoapp.network.DogViewModelFactory

class LastImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLastImageBinding
    private val viewModel: DogViewModel by viewModels{
        DogViewModelFactory((application as ImageApplication).database.imageDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLastImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getAllImagesList().observe(this,{ dogImage ->
            val prevImage = binding.previousDogImageView
            prevImage.load(dogImage[0].imageUrl)
            binding.currentButton.setOnClickListener { finish() }
        })
    }
}