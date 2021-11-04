package com.sermarmu.randomuser.ui.feature.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.sermarmu.randomuser.R
import com.sermarmu.randomuser.common.AppBaseFragment
import com.sermarmu.randomuser.databinding.UserFragmentBinding
import com.sermarmu.randomuser.extensions.debounce
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
        initObserveViewModel()
    }

    private fun initView() {
        binding.apply {
            lpiUsers.show()

            fnbLoadMoreUsers
                .setOnClickListener {
                    lpiUsers.show()
                    viewModel.onLoadMoreUsersRequest()
                }

            tieUsersSearch.debounce(
                coroutineScope = this@UserFragment
            ) {
                if (it.isBlank()) fnbLoadMoreUsers.show()
                else fnbLoadMoreUsers.hide()
                viewModel.onQueryTypedRequest(it)
            }

            rvUsers.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ConcatAdapter(
                    UserHeaderAdapter(),
                    UserAdapter {
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

    private fun initObserveViewModel() {
        viewModel.userStateLiveData.observe(
            this.viewLifecycleOwner, {
                onUserState(it)
            }
        )
    }

    private fun onUserState(
        state: UserViewModel.UserState
    ) {
        binding.lpiUsers.hide()
        when (state) {
            is VM_UserState.Success -> (when {
                state.users.isEmpty() -> binding.vaUsers.displayedChild = 0
                else -> {
                    binding.vaUsers.displayedChild = 1
                    ((binding.rvUsers.adapter as ConcatAdapter)
                        .adapters[1] as UserAdapter)
                        .submitList(state.users)
                }
            }).also {
                (when (state) {
                    is UserViewModel.UserState.Success.LoadNewUsers ->
                        R.string.add_new_user_success
                    is UserViewModel.UserState.Success.UserDeleted ->
                        R.string.delete_user_success
                    else -> null
                })?.let { stringId ->
                    messageLauncher
                        .showPositive(
                            view = requireView(),
                            stringId = stringId
                        )
                }
            }
            is VM_UserState.Failure -> messageLauncher
                .showNegative(
                    view = requireView(),
                    throwable = state.error
                )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}