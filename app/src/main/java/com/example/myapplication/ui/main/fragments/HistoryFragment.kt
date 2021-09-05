package com.example.myapplication.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.R
import com.example.myapplication.ui.main.adapters.CustomAdapter
import com.example.myapplication.ui.main.DBClass
import com.example.myapplication.ui.main.models.GameModel
import com.example.myapplication.ui.main.models.ItemsViewModel
import kotlinx.android.synthetic.main.fragment_history.view.*


class HistoryFragment : Fragment() {

    private var adapter: CustomAdapter? = null
    private var db: Any? = null
    private var data: ArrayList<ItemsViewModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val applicationContext: Context = activity!!.applicationContext

        db = Room.databaseBuilder(
            applicationContext,
            DBClass.AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val recyclerview = view.recycler

        recyclerview.layoutManager = LinearLayoutManager(applicationContext)

        val games: List<GameModel> = (db as DBClass.AppDatabase).gameDao().getAll()

        for (gm: GameModel in games) {
            data.add(ItemsViewModel(formatResult(gm)))
        }

        adapter = CustomAdapter(data)
        recyclerview.adapter = adapter

        view.buttonClearHistory.setOnClickListener {
            (db as DBClass.AppDatabase).gameDao().deleteAll()

            data.clear()
            adapter!!.notifyDataSetChanged()
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        val games: List<GameModel> = (db as DBClass.AppDatabase).gameDao().getAll()

        if (games.size != data.size) {
            data.clear()

            for (gm: GameModel in games) {
                data.add(ItemsViewModel(formatResult(gm)))
            }

            adapter!!.notifyDataSetChanged()
        }
    }

    private fun formatResult(game: GameModel): String {
        return game.firstPlayer + " vs " + game.secondPlayer + "\n" + "Score: " + game.score + "  |  " + game.time
    }
}