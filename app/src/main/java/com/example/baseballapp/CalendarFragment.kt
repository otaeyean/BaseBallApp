package com.example.baseballapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var eventsGrid: GridView
    private lateinit var editTextMemo: EditText
    private lateinit var buttonSaveMemo: Button
    private lateinit var buttonViewMemo: Button
    private lateinit var buttonAddPhoto: Button
    private val apiService by lazy { ApiObject.getRetrofitService }
    private lateinit var sharedPreferences: SharedPreferences
    private var selectedDate: String? = null

    private val REQUEST_PICK_IMAGE = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        calendarView = view.findViewById(R.id.calendarView)
        eventsGrid = view.findViewById(R.id.events_grid)
        editTextMemo = view.findViewById(R.id.editTextMemo)
        buttonSaveMemo = view.findViewById(R.id.buttonSaveMemo)
        buttonViewMemo = view.findViewById(R.id.buttonViewMemo)
        buttonAddPhoto = view.findViewById(R.id.buttonAddPhoto)

        sharedPreferences = requireContext().getSharedPreferences("memo_prefs", 0)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = convertDateToServerFormat(year, month, dayOfMonth)
            fetchSchedule(selectedDate!!)
        }

        buttonSaveMemo.setOnClickListener {
            saveMemo()
        }

        buttonViewMemo.setOnClickListener {
            selectedDate?.let { date ->
                showMemoDialog(date)
            } ?: run {
                Toast.makeText(requireContext(), "날짜를 먼저 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        buttonAddPhoto.setOnClickListener {
            selectedDate?.let { date ->
                showImagePickerDialog(date)
            } ?: run {
                Toast.makeText(requireContext(), "날짜를 먼저 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun fetchSchedule(date: String) {
        apiService.getSchedule(date).enqueue(object : Callback<List<ScheduleData>> {
            override fun onResponse(call: Call<List<ScheduleData>>, response: Response<List<ScheduleData>>) {
                if (response.isSuccessful) {
                    val scheduleList = response.body() ?: emptyList()
                    val filteredList = scheduleList.filter { it.date == date }.take(5)
                    updateEventsGrid(filteredList)
                } else {
                    showErrorToast()
                }
            }

            override fun onFailure(call: Call<List<ScheduleData>>, t: Throwable) {
                showErrorToast()
            }
        })
    }

    private fun updateEventsGrid(scheduleList: List<ScheduleData>) {
        val adapter = CustomScheduleAdapter(requireContext(), scheduleList)
        eventsGrid.adapter = adapter
    }

    private fun showErrorToast() {
        Toast.makeText(requireContext(), "Failed to fetch schedule.", Toast.LENGTH_SHORT).show()
    }

    private fun convertDateToServerFormat(year: Int, month: Int, day: Int): String {
        val date = Calendar.getInstance().apply {
            set(year, month, day)
        }.time
        val dateFormat = SimpleDateFormat("MM.dd(E)", Locale.KOREAN)
        return dateFormat.format(date)
    }

    private fun saveMemo() {
        val memo = editTextMemo.text.toString()
        selectedDate?.let { date ->
            val editor = sharedPreferences.edit()
            editor.putString(date, memo)
            editor.apply()
            Toast.makeText(requireContext(), "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(requireContext(), "날짜를 먼저 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showMemoDialog(date: String) {
        val savedMemo = sharedPreferences.getString(date, "작성하신 메모가 없습니다.")
        val photoPath = sharedPreferences.getString("${date}_photo", null)

        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_memo, null)
        builder.setView(view)

        val memoTextView: TextView = view.findViewById(R.id.memoText)
        val photoImageView: ImageView = view.findViewById(R.id.memoPhoto)

        memoTextView.text = savedMemo

        if (photoPath != null) {
            val photoFile = File(photoPath)
            if (photoFile.exists()) {
                val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                photoImageView.setImageBitmap(bitmap)
                photoImageView.visibility = View.VISIBLE
            } else {
                photoImageView.visibility = View.GONE
            }
        } else {
            photoImageView.visibility = View.GONE
        }

        builder.setTitle("Memo for $date")
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    private fun showImagePickerDialog(date: String) {
        val photoPath = sharedPreferences.getString("${date}_photo", null)

        if (photoPath != null) {
            AlertDialog.Builder(requireContext())
                .setTitle("이미 사진이 존재합니다.")
                .setMessage("이미 저장된 사진이 있습니다. 기존 사진을 덮어쓰시겠습니까?")
                .setPositiveButton("확인") { _, _ ->
                    // 기존 사진 파일 삭제
                    val existingFile = File(photoPath)
                    if (existingFile.exists()) {
                        existingFile.delete()
                    }
                    dispatchPickPictureIntent(date)
                }
                .setNegativeButton("취소", null)
                .show()
        } else {
            dispatchPickPictureIntent(date)
        }
    }

    private fun dispatchPickPictureIntent(date: String) {
        Intent(Intent.ACTION_PICK).also { pickPhotoIntent ->
            pickPhotoIntent.type = "image/*"
            startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_PICK_IMAGE -> {
                    val selectedImageUri = data?.data
                    val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageUri)

                    val photoPath = savePhotoToInternalStorage(bitmap)
                    selectedDate?.let { date ->
                        val editor = sharedPreferences.edit()
                        editor.putString("${date}_photo", photoPath)
                        editor.apply()
                    }

                    displayPhoto(bitmap)
                }
            }
        }
    }

    private fun savePhotoToInternalStorage(bitmap: Bitmap): String {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val photoFile = File(requireContext().filesDir, "photo_$timeStamp.jpg")
        FileOutputStream(photoFile).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
        return photoFile.absolutePath
    }

    private fun displayPhoto(bitmap: Bitmap) {
    }
}