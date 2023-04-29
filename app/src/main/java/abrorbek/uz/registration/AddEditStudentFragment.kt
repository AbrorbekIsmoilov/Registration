package abrorbek.uz.registration

import abrorbek.Object.Object
import abrorbek.models.Students
import abrorbek.uz.registration.databinding.FragmentAddEditStudentBinding
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import developer.abdusamid.codial_app.db.MyDBHelper
import java.time.LocalDate

class AddEditStudentFragment : Fragment() {
    lateinit var myDBHelper: MyDBHelper
    lateinit var binding: FragmentAddEditStudentBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadData()
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imageSave.setOnClickListener {
            if (abrorbek.Object.Object.editStudent) {
                editStudents()
            } else {
                addStudents()
            }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addStudents() {
        val name = binding.edtStudentName.text.toString().trim()
        val surname = binding.edtStudentSurname.text.toString().trim()
        val number = binding.edtStudentNumber.text.toString().trim()
        val date = LocalDate.now().toString()
        val students = Students(name, surname, number, date, abrorbek.Object.Object.groups)

        if (name.isNotEmpty() && surname.isNotEmpty() && number.isNotEmpty() && date.isNotEmpty()) {
            myDBHelper.addStudents(students, requireActivity())
            binding.edtStudentName.text!!.clear()
            binding.edtStudentSurname.text!!.clear()
            binding.edtStudentNumber.text!!.clear()
        }
    }

    private fun editStudents() {
        val name = binding.edtStudentName.text.toString().trim()
        val surname = binding.edtStudentSurname.text.toString().trim()
        val number = binding.edtStudentNumber.text.toString().trim()
        val students =
            Students(abrorbek.Object.Object.students.id, name, surname, number, abrorbek.Object.Object.students.day, Object.groups)
        myDBHelper.updateStudents(students, requireActivity())
        findNavController().popBackStack()
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {
        binding = FragmentAddEditStudentBinding.inflate(layoutInflater)
        myDBHelper = MyDBHelper(requireActivity())
        if (abrorbek.Object.Object.editStudent) {
            binding.tvAllCourses.text = "Talaba o'zgartirish"
            binding.imageSave.setImageResource(R.drawable.ic_baseline_edit_24)
            binding.edtStudentName.setText(abrorbek.Object.Object.students.name)
            binding.edtStudentSurname.setText(abrorbek.Object.Object.students.surname)
            binding.edtStudentNumber.setText(abrorbek.Object.Object.students.number)
        }
    }


}