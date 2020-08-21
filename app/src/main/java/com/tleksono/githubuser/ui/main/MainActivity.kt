package com.tleksono.githubuser.ui.main

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tleksono.githubuser.R
import com.tleksono.githubuser.domain.model.State
import com.tleksono.githubuser.domain.model.User
import com.tleksono.githubuser.ui.main.adapter.MainActivityItemAdapter
import com.tleksono.githubuser.util.OnLoadMoreListener
import com.tleksono.githubuser.util.RecyclerViewLoadMore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    companion object {
        const val USER_INSTANCE_STATE = "state"
        const val RECYCLE_INCSTANCE_STATE = "rec"
        const val PAGE_INCSTANCE_STATE = "page"
    }

    private lateinit var recyclerViewLoadMore: RecyclerViewLoadMore
    private lateinit var mainActivityItemAdapter: MainActivityItemAdapter
    private lateinit var users: MutableList<User?>
    private var nextUrl: String = ""
    private var recycleState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            users = savedInstanceState.getParcelableArrayList(USER_INSTANCE_STATE) ?: arrayListOf()
            recycleState = savedInstanceState.getParcelable(RECYCLE_INCSTANCE_STATE)
            nextUrl = savedInstanceState.getString(PAGE_INCSTANCE_STATE, "")
        } else {
            users = mutableListOf()
        }

        initialize()
        observeViewModel()

        btnSearch.setOnClickListener {
            recyclerViewLoadMore.resetPage()
            mainActivityItemAdapter.clear()
            viewModel.searchUser(etSearch.text.toString())
        }
    }

    private fun initialize() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            mainActivityItemAdapter = MainActivityItemAdapter(users)
            adapter = mainActivityItemAdapter
            recyclerViewLoadMore = RecyclerViewLoadMore(layoutManager as LinearLayoutManager)
            recyclerViewLoadMore.setNextPage(nextUrl)
            recyclerViewLoadMore.setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMore(url: String) {
                    viewModel.loadNextData(url)
                }
            })

            addOnScrollListener(recyclerViewLoadMore)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(USER_INSTANCE_STATE, ArrayList(users))
        outState.putParcelable(
            RECYCLE_INCSTANCE_STATE,
            recyclerView.layoutManager?.onSaveInstanceState()
        )
        outState.putString(PAGE_INCSTANCE_STATE, recyclerViewLoadMore.getNextUrl())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        users = savedInstanceState.getParcelableArrayList(USER_INSTANCE_STATE) ?: arrayListOf()
        recycleState = savedInstanceState.getParcelable(RECYCLE_INCSTANCE_STATE)
        nextUrl = savedInstanceState.getString(PAGE_INCSTANCE_STATE, "")
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(this, Observer { state ->
            when (state.status) {
                State.Status.SUCCESS -> {
                    mainActivityItemAdapter.removeLoadingView()
                    state.data?.let {
                        recyclerViewLoadMore.setNextPage(it.nextPage)
                        mainActivityItemAdapter.addContent(it.items.toMutableList())
                        if (it.items.isEmpty()) showSnackBar("Data not found")
                    }
                }
                State.Status.ERROR -> {
                    mainActivityItemAdapter.removeLoadingView()
                    state.message?.let { showSnackBar(it) }
                }
                State.Status.LOADING -> {
                    mainActivityItemAdapter.addLoadingView()
                }
            }
        })

        viewModel.hashMoreLiveData.observe(this, Observer {
            if (it) recyclerViewLoadMore.invalidateLoading()
        })
    }

    private fun showSnackBar(text: String) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show()
    }
}
