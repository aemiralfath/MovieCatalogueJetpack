package com.aemiralfath.moviecatalogue.ui.tv

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
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

    private var querySearch: String = ""
    private var state: Boolean = false
    private var sortType: String = SortUtils.NEWEST

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
            with(binding) {

                tvAdapter = TvAdapter()

                if (state) {
                    svTv.visibility = View.GONE
                    tvViewModel.getFavoriteTv().observe(viewLifecycleOwner, {
                        tvAdapter.submitList(it)
                    })
                } else {
                    svTv.visibility = View.VISIBLE
                    tvViewModel.getTv(SortUtils.NEWEST, querySearch)
                        .observe(viewLifecycleOwner, tvObserver)

                    val searchManager =
                        requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
                    svTv.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                    svTv.isActivated = true
                    svTv.queryHint = resources.getString(R.string.search_tv)
                    svTv.onActionViewExpanded()
                    svTv.isIconified = false
                    svTv.clearFocus()

                    svTv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            query?.let {
                                tvViewModel.getTv(sortType, it)
                                    .observe(viewLifecycleOwner, tvObserver)
                                querySearch = it
                            }
                            requireActivity().hideSoftKeyboard()
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            newText?.let {
                                tvViewModel.getTv(sortType, newText)
                                    .observe(viewLifecycleOwner, tvObserver)
                            }
                            return true
                        }
                    })

                    val idBtn = svTv.context.resources.getIdentifier(
                        "android:id/search_close_btn",
                        null,
                        null
                    )
                    val closeButton = svTv.findViewById<ImageView>(idBtn)

                    closeButton.setOnClickListener {
                        querySearch = ""
                        tvViewModel.getTv(sortType, querySearch)
                            .observe(viewLifecycleOwner, tvObserver)
                        svTv.setQuery("", false)
                        requireActivity().hideSoftKeyboard()
                    }
                }

                rvHomeTv.layoutManager = LinearLayoutManager(context)
                rvHomeTv.setHasFixedSize(true)
                rvHomeTv.adapter = tvAdapter
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
        tvViewModel.getTv(sort, querySearch).observe(this, tvObserver)
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    private fun Activity.hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager =
                ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
        binding.rvHomeTv.visibility = if (state) View.GONE else View.VISIBLE
    }
}