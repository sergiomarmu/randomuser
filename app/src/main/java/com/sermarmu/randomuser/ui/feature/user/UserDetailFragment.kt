package com.sermarmu.randomuser.ui.feature.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sermarmu.domain.model.dateRegisteredFormat
import com.sermarmu.domain.model.genderFormat
import com.sermarmu.domain.model.streetFormat
import com.sermarmu.randomuser.R
import com.sermarmu.randomuser.databinding.UserDetailFragmentBinding
import com.sermarmu.randomuser.extensions.loadImageFromUrlWithRadius
import com.sermarmu.randomuser.extensions.readOnlyMode
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserDetailFragment : BottomSheetDialogFragment() {

    private val viewModel: UserViewModel
            by sharedViewModel<UserViewModelImpl>()

    private val args: UserDetailFragmentArgs by navArgs()

    private var _binding: UserDetailFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NORMAL,
            R.style.ThemeOverlay_AppTheme_BottomSheetDialog
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = with(
        UserDetailFragmentBinding
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
        binding.apply {
            acivImageUserDetail.loadImageFromUrlWithRadius(
                args.userModel.picture.large
            )
            tieFirstNameUserDetail.readOnlyMode(args.userModel.name.first)
            tieLastNameUserDetail.readOnlyMode(args.userModel.name.last)
            tieEmailUserDetail.readOnlyMode(args.userModel.email)
            tieRegisterDateUserDetail.readOnlyMode(args.userModel.dateRegisteredFormat)
            tieGenderUserDetail.readOnlyMode(args.userModel.genderFormat)
            tieStreetUserDetail.readOnlyMode(args.userModel.streetFormat)
            tieStateUserDetail.readOnlyMode(args.userModel.location.state)
            tieCityUserDetail.readOnlyMode(args.userModel.location.city)

            mbDeleteUserDetail.setOnClickListener {
                viewModel.onUserRemoveRequest(
                    args.userModel
                )
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}