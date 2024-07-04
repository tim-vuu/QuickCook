package com.application.quickcook.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.quickcook.data.pojo.Meal
import com.application.quickcook.data.pojo.MealsResponse
import com.application.quickcook.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealActivityMVVM():ViewModel() {
    private var mutableMeal = MutableLiveData<List<Meal>>()

    fun getMealsByCategory(category:String){
        RetrofitInstance.foodApi.getMealsByCategory(category).enqueue(object : Callback<MealsResponse>{
            override fun onResponse(call: Call<MealsResponse>, response: Response<MealsResponse>) {
                mutableMeal.value = response.body()!!.meals
            }

            override fun onFailure(call: Call<MealsResponse>, t: Throwable) {
                Log.d(TAG,t.message.toString())
            }

        })
    }

    fun observeMeal():LiveData<List<Meal>>{
        return mutableMeal
    }
}