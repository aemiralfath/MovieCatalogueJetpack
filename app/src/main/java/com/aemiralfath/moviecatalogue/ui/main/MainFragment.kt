package com.aemiralfath.moviecatalogue.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aemiralfath.moviecatalogue.databinding.FragmentMainBinding
import com.aemiralfath.moviecatalogue.ui.main.adapter.MovieAdapter
import com.aemiralfath.moviecatalogue.ui.main.adapter.TvAdapter

class MainFragment : Fragment() {

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    private var tabIndex: Int = 1
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabIndex = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java).apply {
            if (tabIndex == 1) setMovie(requireContext()) else setTv(requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        showLoading(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            with(binding.rvHome) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)

                if (tabIndex == 1) {
                    mainViewModel.getMovie().observe(viewLifecycleOwner, {
                        val movieAdapter = MovieAdapter()
                        movieAdapter.setMovie(it)
                        adapter = movieAdapter
                        showLoading(false)
                    })
                } else {
                    mainViewModel.getTv().observe(viewLifecycleOwner, {
                        val tvAdapter = TvAdapter()
                        tvAdapter.setTv(it)
                        adapter = tvAdapter
                        showLoading(false)
                    })
                }
            }

        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}