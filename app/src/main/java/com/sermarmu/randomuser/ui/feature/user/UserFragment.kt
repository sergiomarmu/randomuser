package com.sermarmu.randomuser.ui.feature.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sermarmu.core.base.BaseFragment
import com.sermarmu.domain.model.UserModel
import com.sermarmu.randomuser.databinding.UserAdapterModelBinding
import com.sermarmu.randomuser.databinding.UserFragmentBinding
import com.sermarmu.randomuser.extensions.loadImageFromUrlWithRadius
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment() {
    private val viewModel: UserViewModel
            by viewModel()

    private var _binding: UserFragmentBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = with(
        UserFragmentBinding
            .inflate(layoutInflater)
    ) {
        _binding = this
        this.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUsers.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AdapterList {
                navController.navigate(
                    UserFragmentDirections.actNavDestUserDetailFragment(it)
                )
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.userLiveData
                .collectLatest {
                    (binding.rvUsers.adapter as AdapterList)
                        .submitList(it)
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


private class AdapterList(
    private val onClickUser: (UserModel) -> Unit
) : ListAdapter<UserModel, AdapterList.ViewHolder>(UserComparator) {

    private class ViewHolder(
        private val binding: UserAdapterModelBinding
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
            acivImageCharactermodel.loadImageFromUrlWithRadius(
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
        UserAdapterModelBinding.inflate(
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
            holder.bind(it, onClickUser)
        }
    }

    private object UserComparator : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel) =
            oldItem.uuid == newItem.uuid

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel) =
            oldItem == newItem
    }
}