package abrorbek.uz.registration

import abrorbek.Object.Object
import abrorbek.models.Mentors
import abrorbek.uz.registration.databinding.FragmentAddMentorsBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import developer.abdusamid.codial_app.db.MyDBHelper

class AddMentorsFragment : Fragment() {
    lateinit var myDBHelper: MyDBHelper
    lateinit var binding: FragmentAddMentorsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMentorsBinding.inflate(layoutInflater)
        myDBHelper = MyDBHelper(requireActivity())
        binding.imageSave.setOnClickListener {
            val surname = binding.edtMentorsSurname.text.toString().trim()
            val name = binding.edtMentorsName.text.toString().trim()
            val patronymic = binding.edtMentorsPatronymic.text.toString().trim()
            if (surname.isNotEmpty() && name.isNotEmpty() && patronymic.isNotEmpty()) {
                myDBHelper.addMentors(
                    Mentors(
                        surname, name, patronymic, myDBHelper.getCoursesByID(
                            Object.courses.id!!
                        )
                    ), requireActivity()
                )
            }
            binding.edtMentorsSurname.text!!.clear()
            binding.edtMentorsName.text!!.clear()
            binding.edtMentorsPatronymic.text!!.clear()
        }
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}