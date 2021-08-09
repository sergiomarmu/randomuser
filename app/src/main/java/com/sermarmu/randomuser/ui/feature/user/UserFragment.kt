package com.sermarmu.randomuser.ui.feature.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.sermarmu.domain.model.UserModel
import com.sermarmu.randomuser.common.AppBaseFragment
import com.sermarmu.randomuser.databinding.UserAdapterBinding
import com.sermarmu.randomuser.databinding.UserAdapterHeaderBinding
import com.sermarmu.randomuser.databinding.UserFragmentBinding
import com.sermarmu.randomuser.extensions.debounce
import com.sermarmu.randomuser.extensions.gone
import com.sermarmu.randomuser.extensions.loadImageFromUrlWithRadius
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.sermarmu.randomuser.ui.feature.user.UserViewModel.UserState as VM_UserState

class UserFragment : AppBaseFragment() {

    private val viewModel: UserViewModel
            by sharedViewModel<UserViewModelImpl>()

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

        initView()

        lifecycleScope.launchWhenStarted {
            viewModel.uiStateSharedFlow
                .collectLatest {
                    onUserState(it)
                }
        }
    }

    private fun initView() {
        binding.apply {
            lpiUsers.show()

            fnbLoadMoreUsers
                .setOnClickListener {
                    lpiUsers.show()
                    viewModel.onLoadMoreUsersAction()
                }

            tieUsersSearch.debounce(
                coroutineScope = this@UserFragment
            ) {
                if (it.isBlank())
                    fnbLoadMoreUsers.show()
                else
                    fnbLoadMoreUsers.hide()
                viewModel.onQueryTypedAction(it)
            }

            rvUsers.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ConcatAdapter(
                    HeaderAdapter(),
                    Adapter {
                        navController.navigate(
                            UserFragmentDirections.actNavDestUserDetailFragment(it)
                        )
                    }
                )

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(
                        recyclerView: RecyclerView,
                        newState: Int
                    ) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == SCROLL_STATE_IDLE)
                            fnbLoadMoreUsers.extend()
                        else
                            fnbLoadMoreUsers.shrink()
                    }
                })
            }
        }
    }

    private fun onUserState(
        state: UserViewModel.UserState
    ) {
        binding.lpiUsers.gone()
        when (state) {
            is VM_UserState.Idle -> {
                // Nothing to do
            }
            is VM_UserState.Success -> when {
                state.users.isEmpty() -> binding.vaUsers.displayedChild = 0
                else -> {
                    binding.vaUsers.displayedChild = 1
                    ((binding.rvUsers.adapter as ConcatAdapter)
                        .adapters[1] as Adapter)
                        .submitList(state.users)
                }
            }
            is VM_UserState.Failure -> messageLauncher
                .showNegative(
                    view = requireView(),
                    throwable = state.e
                )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

private class HeaderAdapter :
    RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {

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

private class Adapter(
    private val onClickUser: (UserModel) -> Unit
) : ListAdapter<UserModel, Adapter.ViewHolder>(UserComparator) {

    private class ViewHolder(
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
            holder.bind(it, onClickUser)
        }
    }

    private object UserComparator : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel) =
            oldItem == newItem
    }
}