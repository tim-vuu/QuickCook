package com.application.quickcook.mvvm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.quickcook.data.pojo.MealDetail
import com.application.quickcook.data.pojo.RandomMealResponse
import com.application.quickcook.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMVVM : ViewModel() {
    private var searchedMealLiveData = MutableLiveData<MealDetail>()


    fun searchMealDetail(name: String,context: Context?) {
        RetrofitInstance.foodApi.getMealByName(name).enqueue(object : Callback<RandomMealResponse> {
            override fun onResponse(call: Call<RandomMealResponse>, response: Response<RandomMealResponse>) {
                if (response.body()?.meals == null)
                    Toast.makeText(context?.applicationContext, "No such a meal", Toast.LENGTH_SHORT).show()
                else
                    searchedMealLiveData.value = response.body()!!.meals[0]
            }

            override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })
    }

    fun observeSearchLiveData(): LiveData<MealDetail> {
        return searchedMealLiveData
    }
}