package com.example.doggoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.net.toUri
import coil.load
import com.example.doggoapp.application.ImageApplication
import com.example.doggoapp.data.ImageEntity
import com.example.doggoapp.databinding.ActivityMainBinding
import com.example.doggoapp.network.DogViewModel
import com.example.doggoapp.network.DogViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

        private val viewModel: DogViewModel by viewModels{
            DogViewModelFactory((application as ImageApplication).database.imageDao())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getRandomDogPhoto()
    }

    fun getRandomDogPhoto() {
        val randomPhotoButton = binding.button
        viewModel.dogPhoto.observe(this, {
            val imgUri = it.imageUrl!!
            binding.imageView.load(imgUri)
            randomPhotoButton.setOnClickListener {
                val currentImage = viewModel.dogPhoto.value!!.imageUrl
                val previousImage = currentImage?.let {
                        dogImage -> ImageEntity(imageUrl = dogImage) }
                viewModel.getNewPhoto()
                if(previousImage != null){
                    viewModel.insertNewImage(previousImage)
                }
                viewModel.deleteLastImage()
            }
        })
    }
}