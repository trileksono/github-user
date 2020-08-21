package com.tleksono.githubuser.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLoadMore(layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 5
    private lateinit var mOnLoadMoreListener: OnLoadMoreListener
    private var isLoading: Boolean = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var mLayoutManager: LinearLayoutManager = layoutManager
    private var nextUrl = ""

    fun invalidateLoading() {
        isLoading = false
    }

    fun resetPage() {
        nextUrl = ""
        invalidateLoading()
    }

    fun setNextPage(url: String) {
        nextUrl = url
    }

    fun getNextUrl(): String = nextUrl

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy <= 0) return
        totalItemCount = mLayoutManager.itemCount
        lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
        if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
            mOnLoadMoreListener.onLoadMore(nextUrl)
            isLoading = true
        }
    }
}
