package com.example.github.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.github.presentation.model.LoadingListItemViewModel

class Adapter(private val viewHolderFactory: ViewHolderFactory) :
    RecyclerView.Adapter<ViewHolder<ViewModel>>() {

    private lateinit var recyclerView: RecyclerView

    private val visibleThreshold = 15
    private val loading = LoadingListItemViewModel
    private val items = arrayListOf<ViewModel>()

    private var lastAttachedPosition = 0

    var isLoading = false
        set(value) {
            if (field == value) return
            field = value
            if (value) add(listOf(loading))
            else remove(loading)
        }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this@Adapter.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ViewModel> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return viewHolderFactory.getHolder(viewType, view) as ViewHolder<ViewModel>
    }

    override fun onBindViewHolder(holder: ViewHolder<ViewModel>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = viewHolderFactory.getType(items[position])

    fun replace(list: List<ViewModel>) {
        items.apply {
            clear()
            addAll(list)
        }
        recyclerView.post { notifyDataSetChanged() }
    }

    fun add(list: List<ViewModel>) {
        val positionStart = items.size
        items.addAll(list)
        recyclerView.post { notifyItemRangeChanged(positionStart, list.size) }
    }

    fun remove(viewModel: ViewModel) {
        val position = items.indexOf(viewModel)
        if (position >= 0) {
            items.remove(viewModel)
            recyclerView.post { notifyItemRemoved(position) }
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder<ViewModel>) {
        val position = holder.adapterPosition
        if (position > lastAttachedPosition && position in items.size - visibleThreshold until items.size)
            viewHolderFactory.onScrolledBeyondVisibleThreshold()
        lastAttachedPosition = position
    }

    override fun onViewRecycled(holder: ViewHolder<ViewModel>) {
        holder.recycle()
    }
}
