// OfferListFragment.kt
package com.example.aviatickets.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aviatickets.R
import com.example.aviatickets.adapter.OfferListAdapter
import com.example.aviatickets.databinding.FragmentOfferListBinding
import com.example.aviatickets.model.entity.Offer
import com.example.aviatickets.model.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferListFragment : Fragment() {

    private var _binding: FragmentOfferListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter: OfferListAdapter by lazy {
        OfferListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferListBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()

        // Fetch offer list using Retrofit
        ApiClient.apiService.getOfferList().enqueue(object : Callback<List<Offer>> {
            override fun onResponse(call: Call<List<Offer>>, response: Response<List<Offer>>) {
                if (response.isSuccessful) {
                    val offerList = response.body()
                    offerList?.let {
                        // Submit the list to the adapter
                        adapter.submitList(it)
                    }
                } else {
                    // Handle error
                    // Show an error message
                }
            }

            override fun onFailure(call: Call<List<Offer>>, t: Throwable) {
                // Handle failure
                // Show an error message
            }
        })
    }

    private fun setupUI() {
        with(binding) {
            offerList.adapter = adapter
            sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.sort_by_price -> {
                        adapter.sortByPrice()
                    }
                    R.id.sort_by_duration -> {
                        adapter.sortByDuration()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


