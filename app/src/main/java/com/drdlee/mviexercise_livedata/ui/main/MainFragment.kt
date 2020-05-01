package com.drdlee.mviexercise_livedata.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.drdlee.mviexercise_livedata.R
import com.drdlee.mviexercise_livedata.databinding.FragmentMainBinding
import com.drdlee.mviexercise_livedata.ui.main.state.MainStateEvent
import java.lang.Exception

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var blogListAdapter: BlogListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fetch_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_user -> viewModel.setEventState(MainStateEvent.GetUserEvent("1"))
            R.id.action_get_blogs -> viewModel.setEventState(MainStateEvent.GetBlogPostEvent())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeObserver()
        blogListAdapter = BlogListAdapter()
        binding.blogList.adapter = blogListAdapter
    }

    /**
     * Initialization
     *
     * dataState observation -> set it to viewState
     * viewState observation -> submit to RecyclerView Adapter
     */
    private fun initializeObserver() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            dataState.data?.let {
                it.user?.let { user ->
                    viewModel.setUser(user)
                }
                it.blogPost?.let { blogList ->
                    viewModel.setBlogList(blogList)
                }
            }
        })
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.blogPost?.let { blogList ->
                blogListAdapter.submitList(blogList)
            }
        })
    }
}