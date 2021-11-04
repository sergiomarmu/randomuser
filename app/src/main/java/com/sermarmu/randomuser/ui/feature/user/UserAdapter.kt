package com.sermarmu.randomuser.ui.feature.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sermarmu.domain.model.UserModel
import com.sermarmu.randomuser.databinding.UserAdapterBinding
import com.sermarmu.randomuser.extensions.loadImageFromUrlWithRadius


private object UserComparator : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(
        oldItem: UserModel,
        newItem: UserModel
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: UserModel,
        newItem: UserModel
    ) = oldItem == newItem
}

class UserAdapter(
    private val onUserClick: (UserModel) -> Unit
) : ListAdapter<UserModel, UserAdapter.ViewHolder>(UserComparator) {

    class ViewHolder(
        private val binding: UserAdapterBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(
            model: UserModel,
            onClickUser: (UserModel) -> Unit
        ) = with(binding) {
            mtvNameUser.text = model.name.first
            mtvSurnameUser.text = model.name.last
            mtvEmailUser.text = model.email
            mtvPhoneUser.text = model.phone
            acivImageUser.loadImageFromUrlWithRadius(
                model.picture.large
            )
            root.setOnClickListener {
                onClickUser(model)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        UserAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        getItem(position)!!.also {
            holder.bind(it, onUserClick)
        }
    }
}