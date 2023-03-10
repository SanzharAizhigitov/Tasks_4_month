package com.geektech.tasks.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.geektech.tasks.R
import com.geektech.tasks.data.Pref
import com.geektech.tasks.databinding.FragmentProfileBinding
import com.geektech.tasks.ext.loadImage
import com.geektech.tasks.ext.showToast
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    lateinit var photo: String
    lateinit var _binding: FragmentProfileBinding
    lateinit var pref: Pref
    val NAME_KEY = "name_key"
    val PHOTO_KEY = "photo_key"
    val AGE_KEY = "age_key"
    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK
            && result.data != null
        ) {
            val photoUri: Uri? = result.data?.data
            photo = photoUri.toString()
            binding.avatarIv.loadImage(photo)
            pref.putString(PHOTO_KEY, photo)
        }
    }
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pref = Pref(requireContext())
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.avatarIv.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launcher.launch(intent)
        }
        binding.btnLogOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.authFragment)
        }
        if (pref.getString(PHOTO_KEY) !== "") {
            binding.avatarIv.loadImage(pref.getString(PHOTO_KEY))
        } else {
            context?.showToast("???? ???? ???????????????? ????????")
        }
        binding.etName.setText(pref.getString(NAME_KEY))
        binding.etAge.setText(pref.getString(AGE_KEY))
        binding.btnSaveName.setOnClickListener {
            pref.putString(NAME_KEY, binding.outlinedTextFieldName.editText?.text.toString())
            pref.putString(AGE_KEY, binding.outlinedTextFieldAge.editText?.text.toString())
        }

    }

}