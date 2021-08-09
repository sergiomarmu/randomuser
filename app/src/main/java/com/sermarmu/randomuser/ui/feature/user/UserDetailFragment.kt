package com.sermarmu.randomuser.ui.feature.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sermarmu.domain.model.dateRegisteredFormat
import com.sermarmu.domain.model.genderFormat
import com.sermarmu.randomuser.R
import com.sermarmu.randomuser.databinding.UserDetailFragmentBinding
import com.sermarmu.randomuser.extensions.loadImageFromUrlWithRadius

class UserDetailFragment : BottomSheetDialogFragment() {

    private val args: UserDetailFragmentArgs by navArgs()

    private var _binding: UserDetailFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_AppTheme_BottomSheetDialog)
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
            tieFirstNameUserDetail.setText(args.userModel.name.first)
            tieLastNameUserDetail.setText(args.userModel.name.last)
            tieGenderUserDetail.setText(args.userModel.genderFormat)
            tieEmailUserDetail.setText(args.userModel.email)
            tieStreetUserDetail.setText(args.userModel.location.street.name)
            tieStateUserDetail.setText(args.userModel.location.state)
            tieCityUserDetail.setText(args.userModel.location.city)
            tieRegisterDateUserDetail.setText(args.userModel.dateRegisteredFormat)
            mbDeleteUserDetail.setOnClickListener {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}