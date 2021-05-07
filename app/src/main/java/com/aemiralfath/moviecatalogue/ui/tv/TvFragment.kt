package com.aemiralfath.moviecatalogue.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aemiralfath.moviecatalogue.databinding.FragmentTvBinding
import com.aemiralfath.moviecatalogue.vo.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvFragment : Fragment() {

    private val tvViewModel: TvViewModel by viewModel()
    private lateinit var binding: FragmentTvBinding
    private var state: Boolean = false

    companion object {
        private const val STATE = "state"

        @JvmStatic
        fun newInstance(state: Boolean) =
            TvFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(STATE, state)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            state = it.getBoolean(STATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            with(binding.rvHomeTv) {

                val tvAdapter = TvAdapter()

                if (state) {
                    tvViewModel.getFavoriteTv().observe(viewLifecycleOwner, {
                        tvAdapter.submitList(it)
                    })
                } else {
                    tvViewModel.getTv().observe(viewLifecycleOwner, {
                        when (it.status) {
                            Status.SUCCESS -> {
                                showLoading(false)
                                it.data?.let { data -> tvAdapter.submitList(data) }
                            }
                            Status.LOADING -> {
                                showLoading(true)
                            }
                            Status.ERROR -> {
                                showLoading(false)
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    })
                }



                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvAdapter
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}