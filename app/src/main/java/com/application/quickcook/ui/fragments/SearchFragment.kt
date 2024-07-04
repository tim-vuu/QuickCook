package com.application.quickcook.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.application.quickcook.adapters.MealRecyclerAdapter
import com.application.quickcook.data.pojo.MealDetail
import com.application.quickcook.databinding.FragmentSearchBinding
import com.application.quickcook.mvvm.SearchMVVM
import com.application.quickcook.ui.activites.MealDetailesActivity
import com.application.quickcook.ui.fragments.HomeFragment
import com.application.quickcook.ui.fragments.HomeFragment.Companion.MEAL_ID
import com.application.quickcook.ui.fragments.HomeFragment.Companion.MEAL_STR
import com.application.quickcook.ui.fragments.HomeFragment.Companion.MEAL_THUMB

class SearchFragment : Fragment() {
    private lateinit var myAdapter: MealRecyclerAdapter
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchMvvm: SearchMVVM
    private var mealId = ""
    private var mealStr = ""
    private var mealThub = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAdapter = MealRecyclerAdapter()
        searchMvvm = ViewModelProviders.of(this)[SearchMVVM::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSearchClick()
        observeSearchLiveData()
        setOnMealCardClick()
    }

    private fun setOnMealCardClick() {
        binding.searchedMealCard.setOnClickListener {
            val intent = Intent(context, MealDetailesActivity::class.java)

            intent.putExtra(MEAL_ID, mealId)
            intent.putExtra(MEAL_STR, mealStr)
            intent.putExtra(MEAL_THUMB, mealThub)

            startActivity(intent)


        }
    }

    private fun onSearchClick() {
        binding.icSearch.setOnClickListener {
            searchMvvm.searchMealDetail(binding.edSearch.text.toString(),context)

        }
    }

    private fun observeSearchLiveData() {
        searchMvvm.observeSearchLiveData()
            .observe(viewLifecycleOwner, object : Observer<MealDetail> {
                override fun onChanged(t: MealDetail?) {
                    if (t == null) {
                        Toast.makeText(context, "No such a meal", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.apply {

                            mealId = t.idMeal
                            mealStr = t.strMeal
                            mealThub = t.strMealThumb

                            Glide.with(context!!.applicationContext)
                                .load(t.strMealThumb)
                                .into(imgSearchedMeal)

                            tvSearchedMeal.text = t.strMeal
                            searchedMealCard.visibility = View.VISIBLE
                        }
                    }
                }
            })
    }


}