package com.aemiralfath.moviecatalogue.ui.tv

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.data.source.local.entity.TvEntity
import com.aemiralfath.moviecatalogue.databinding.FragmentTvBinding
import com.aemiralfath.moviecatalogue.utils.SortUtils
import com.aemiralfath.moviecatalogue.vo.Resource
import com.aemiralfath.moviecatalogue.vo.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvFragment : Fragment() {

    private val tvViewModel: TvViewModel by viewModel()
    private lateinit var binding: FragmentTvBinding
    private lateinit var tvAdapter: TvAdapter
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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            with(binding.rvHomeTv) {

                tvAdapter = TvAdapter()

                if (state) {
                    tvViewModel.getFavoriteTv().observe(viewLifecycleOwner, {
                        tvAdapter.submitList(it)
                    })
                } else {
                    tvViewModel.getTv(SortUtils.NEWEST).observe(viewLifecycleOwner, tvObserver)
                }

                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvAdapter
            }
        }
    }

    private val tvObserver = Observer<Resource<PagedList<TvEntity>>> {
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_newest -> sort = SortUtils.NEWEST
            R.id.action_oldest -> sort = SortUtils.OLDEST
            R.id.action_character -> sort = SortUtils.CHARACTER
        }
        tvViewModel.getTv(sort).observe(this, tvObserver)
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}