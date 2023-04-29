package abrorbek.myClass

import abrorbek.Object.Object
import abrorbek.adapters.GroupsAdapter
import abrorbek.models.Groups
import abrorbek.models.Mentors
import abrorbek.uz.registration.R
import abrorbek.uz.registration.databinding.DialogEditGroupsBinding
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import developer.abdusamid.codial_app.db.MyDBHelper


class UpdateGroups(var activity: Activity, var groups: Groups) {
    lateinit var binding: DialogEditGroupsBinding
    private lateinit var dialog: AlertDialog
    lateinit var myDBHelper: MyDBHelper
    lateinit var arrayListMentors: ArrayList<Mentors>
    private lateinit var arrayListMentorsString: ArrayList<String>
    lateinit var arrayListTime: ArrayList<String>
    lateinit var arrayListDay: ArrayList<String>
    lateinit var arrayAdapterTimes: ArrayAdapter<String>
    lateinit var arrayAdapterDays: ArrayAdapter<String>
    lateinit var arrayAdapterMentors: ArrayAdapter<String>

    @SuppressLint("SetTextI18n")
    fun buildDialog(
        groupsAdapter: GroupsAdapter,
        position: Int,
    ) {
        loadData(groups)
        val alertDialog = AlertDialog.Builder(activity)
        binding.edtGroupsName.setText(groups.name)
        binding.spinnerMentors.setText("${groups.mentors!!.name} ${groups.mentors!!.surname}")
        binding.spinnerTimes.setText(groups.times)
        binding.spinnerDays.setText(groups.days)
        binding.spinnerTimes.setAdapter(arrayAdapterTimes)
        binding.spinnerDays.setAdapter(arrayAdapterDays)
        binding.spinnerMentors.setAdapter(arrayAdapterMentors)


        binding.spinnerMentors.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                abrorbek.Object.Object.mentors = arrayListMentors[position]
            }
        }

        binding.tvCancel.setOnClickListener {
            dialog.cancel()
        }

        binding.tvSave.setOnClickListener {
            if (binding.edtGroupsName.text.toString().trim()
                    .isNotEmpty() && binding.spinnerMentors.text.toString().trim()
                    .isNotEmpty() && binding.spinnerTimes.text.toString().trim().isNotEmpty()
                && binding.spinnerDays.text.toString().trim().isNotEmpty()
            ) {
                val groupID = groups.id
                val groupName = binding.edtGroupsName.text.toString().trim()
                val groupMentor = abrorbek.Object.Object.mentors
                val groupTime = binding.spinnerTimes.text.toString().trim()
                val groupDay = binding.spinnerDays.text.toString().trim()
                val groupCourses = abrorbek.Object.Object.courses
                val open = groups.open
                val groupsAdd = Groups(
                    groupID, groupName, groupMentor, groupTime, groupDay, groupCourses, open
                )

                myDBHelper.updateGroups(groupsAdd, activity)

                dialog.cancel()
            }
        }

        alertDialog.setView(binding.root)
        dialog = alertDialog.create()
        dialog.window!!.attributes.windowAnimations = R.style.MyAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun loadData(groups: Groups) {
        binding = DialogEditGroupsBinding.inflate(activity.layoutInflater)
        myDBHelper = MyDBHelper(activity)
        arrayListMentors = ArrayList()
        arrayListMentorsString = ArrayList()
        arrayListTime = ArrayList()
        arrayListDay = ArrayList()
        abrorbek.Object.Object.mentors = groups.mentors!!

        arrayListTime.add("08:00 - 10:00")
        arrayListTime.add("10:00 - 12:00")
        arrayListTime.add("12:00 - 14:00")
        arrayListTime.add("14:00 - 16:00")
        arrayListTime.add("16:00 - 18:00")
        arrayListTime.add("18:00 - 20:00")
        arrayListDay.add("Duyshanba/Chorshanba/Juma")
        arrayListDay.add("Seshanba/Payshanba/Shanba")

        arrayListMentors = myDBHelper.getAllMentorsByID(Object.courses.id!!)
        for (i in 0 until arrayListMentors.size) {
            arrayListMentorsString.add("${arrayListMentors[i].name!!} ${arrayListMentors[i].surname!!}")
        }

        arrayAdapterTimes = ArrayAdapter(activity, R.layout.item_spinner, arrayListTime)
        arrayAdapterDays = ArrayAdapter(activity, R.layout.item_spinner, arrayListDay)
        arrayAdapterMentors =
            ArrayAdapter(activity, R.layout.item_spinner, arrayListMentorsString)
    }
}