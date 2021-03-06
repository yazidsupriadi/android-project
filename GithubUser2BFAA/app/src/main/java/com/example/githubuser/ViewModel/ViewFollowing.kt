package com.example.githubuser.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.retrofit.ApiClient
import com.example.githubuser.retrofit.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewFollowing : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<UserModel>>()

    fun setListFollowing(username: String) {
        ApiClient.apiReq
            .getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<UserModel>> {
                override fun onResponse(
                        call: Call<ArrayList<UserModel>>,
                        response: Response<ArrayList<UserModel>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserModel>>, error: Throwable) {
                    Log.d("Failure", error.message.toString())
                }
            })
    }

    fun getListFollowing(): LiveData<ArrayList<UserModel>> = listFollowing
}