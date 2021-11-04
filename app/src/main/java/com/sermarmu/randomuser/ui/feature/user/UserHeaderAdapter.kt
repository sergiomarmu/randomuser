package com.sermarmu.randomuser.ui.feature.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sermarmu.randomuser.databinding.UserAdapterHeaderBinding

class UserHeaderAdapter :
    RecyclerView.Adapter<UserHeaderAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: UserAdapterHeaderBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind() = with(binding) {}
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        UserAdapterHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1
}