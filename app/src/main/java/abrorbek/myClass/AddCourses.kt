package abrorbek.myClass

import abrorbek.models.Courses
import abrorbek.uz.registration.R
import abrorbek.uz.registration.databinding.DialogAddCoursesBinding
import abrorbek.uz.registration.databinding.FragmentCoursesBinding
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.navigation.NavController
import developer.abdusamid.codial_app.db.MyDBHelper

class  AddCourses(
    var activity: Activity,
    var binding: FragmentCoursesBinding,
    var findNavController: NavController,
) {
    private lateinit var bindingDialog: DialogAddCoursesBinding
    lateinit var dialog: AlertDialog
    lateinit var myDBHelper: MyDBHelper
    lateinit var showCourses: ShowCourses
    var booleanAntiBag = true

    fun buildDialog() {
        bindingDialog = DialogAddCoursesBinding.inflate(activity.layoutInflater)
        val alertDialog = AlertDialog.Builder(activity)
        bindingDialog.tvCancel.setOnClickListener {
            dialog.cancel()
        }

        bindingDialog.tvSave.setOnClickListener {
            val name = bindingDialog.edtCoursesName.text.toString().trim()
            val about = bindingDialog.edtCoursesAbout.text.toString().trim()
            if (name.isNotEmpty() && about.isNotEmpty()) {
                myDBHelper = MyDBHelper(activity)
                if (myDBHelper.addCourses(Courses(name, about), activity)) {
                    showCourses = ShowCourses(activity, binding, findNavController)
                    showCourses.showCourses()
                    dialog.cancel()
                }
            }
        }

        alertDialog.setOnCancelListener {
            booleanAntiBag = true
        }

        alertDialog.setView(bindingDialog.root)
        dialog = alertDialog.create()
        dialog.window!!.attributes.windowAnimations = R.style.MyAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (booleanAntiBag) {
            dialog.show()
            booleanAntiBag = false
        }
    }
}