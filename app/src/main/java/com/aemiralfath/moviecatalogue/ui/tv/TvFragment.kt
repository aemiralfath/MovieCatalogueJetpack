package com.aemiralfath.moviecatalogue.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aemiralfath.moviecatalogue.databinding.FragmentTvBinding
import com.aemiralfath.moviecatalogue.utils.EspressoIdlingResource

class TvFragment : Fragment() {

    private lateinit var tvViewModel: TvViewModel
    private lateinit var binding: FragmentTvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvViewModel = ViewModelProvider(this).get(TvViewModel::class.java).apply {
            EspressoIdlingResource.increment()
            setTv()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvBinding.inflate(layoutInflater, container, false)
        showLoading(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            with(binding.rvHomeTv) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)

                tvViewModel.getTv().observe(viewLifecycleOwner, {
                    val tvAdapter = TvAdapter()
                    tvAdapter.setTv(it)
                    adapter = tvAdapter
                    showLoading(false)
                })
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}