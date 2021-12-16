package com.example.doggoapp.network

import androidx.lifecycle.*
import com.example.doggoapp.data.ImageDao
import com.example.doggoapp.data.ImageEntity
import kotlinx.coroutines.launch

class DogViewModel(private val imageDao: ImageDao) : ViewModel() {

    private val _dogPhoto = MutableLiveData<DogPhoto>()
    val dogPhoto: LiveData<DogPhoto> = _dogPhoto

    init {
        getNewPhoto()
    }

    fun getNewPhoto(){

        viewModelScope.launch {
            _dogPhoto.value = DogPhotoApi.retrofitService.getRandomPhoto()
        }

    }


    fun insertNewImage(imageEntity: ImageEntity){
        viewModelScope.launch {
            imageDao.insertImage(imageEntity)
        }
    }

    fun deleteLastImage(){
        viewModelScope.launch {
            imageDao.deleteImage()
        }
    }

    fun getAllImagesList(): LiveData<List<ImageEntity>>{
        return imageDao.getAllImages().asLiveData()
    }
}

class DogViewModelFactory(val dogImageDao: ImageDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DogViewModel(dogImageDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}


