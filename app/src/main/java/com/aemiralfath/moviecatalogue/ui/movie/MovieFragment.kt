package com.aemiralfath.moviecatalogue.ui.movie

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
import com.aemiralfath.moviecatalogue.data.source.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.databinding.FragmentMovieBinding
import com.aemiralfath.moviecatalogue.utils.SortUtils
import com.aemiralfath.moviecatalogue.vo.Resource
import com.aemiralfath.moviecatalogue.vo.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModel()
    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieAdapter: MovieAdapter

    private var querySearch: String = ""
    private var state: Boolean = false
    private var sortType: String = SortUtils.NEWEST

    companion object {
        private const val STATE = "state"

        @JvmStatic
        fun newInstance(state: Boolean) =
            MovieFragment().apply {
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
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            with(binding) {

                movieAdapter = MovieAdapter()

                if (state) {
                    svMovie.visibility = View.GONE
                    movieViewModel.getFavoriteMovie().observe(viewLifecycleOwner, {
                        movieAdapter.submitList(it)
                    })
                } else {
                    svMovie.visibility = View.VISIBLE
                    movieViewModel.getMovie(sortType, querySearch)
                        .observe(viewLifecycleOwner, movieObserver)

                    val searchManager =
                        requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
                    svMovie.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                    svMovie.isActivated = true
                    svMovie.queryHint = resources.getString(R.string.search_movie)
                    svMovie.onActionViewExpanded()
                    svMovie.isIconified = false
                    svMovie.clearFocus()

                    svMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            query?.let {
                                movieViewModel.getMovie(sortType, it)
                                    .observe(viewLifecycleOwner, movieObserver)
                                querySearch = it
                            }
                            requireActivity().hideSoftKeyboard()
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            return if (newText.isNullOrBlank()) {
                                newText?.let {
                                    movieViewModel.getMovie(sortType, it)
                                        .observe(viewLifecycleOwner, movieObserver)
                                }
                                true
                            } else {
                                showLoading(true)
                                true
                            }
                        }
                    })

                    val idBtn = svMovie.context.resources.getIdentifier(
                        "android:id/search_close_btn",
                        null,
                        null
                    )
                    val closeButton = svMovie.findViewById<ImageView>(idBtn)

                    closeButton.setOnClickListener {
                        querySearch = ""
                        movieViewModel.getMovie(sortType, querySearch)
                            .observe(viewLifecycleOwner, movieObserver)
                        svMovie.setQuery("", false)
                        requireActivity().hideSoftKeyboard()
                    }
                }

                rvHomeMovie.layoutManager = LinearLayoutManager(context)
                rvHomeMovie.setHasFixedSize(true)
                rvHomeMovie.adapter = movieAdapter
            }
        }
    }

    private val movieObserver = Observer<Resource<PagedList<MovieEntity>>> {
        when (it.status) {
            Status.SUCCESS -> {
                showLoading(false)
                it.data?.let { data -> movieAdapter.submitList(data) }
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
        movieViewModel.getMovie(sort, querySearch).observe(this, movieObserver)
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
        binding.rvHomeMovie.visibility = if (state) View.GONE else View.VISIBLE
    }
}