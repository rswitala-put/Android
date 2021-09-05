package com.example.myapplication.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_start.view.*


class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity!!.applicationContext

        val view = inflater.inflate(R.layout.fragment_start, container, false)

        view.buttonStart.setOnClickListener {
            (activity as MainActivity).addMainFragments(
                view.textFirstName.text.toString(),
                view.textSecondName.text.toString(),
                view.switchPlayer.isChecked
            )
        }

        view.textFirstName.setOnFocusChangeListener { view: View, _: Boolean ->
            if (view.textFirstName.hasFocus())
                view.textFirstName.setText("")
        }

        view.textSecondName.setOnFocusChangeListener { view: View, _: Boolean ->
            if (view.textSecondName.hasFocus())
                view.textSecondName.setText("")
        }

        return view
    }

}