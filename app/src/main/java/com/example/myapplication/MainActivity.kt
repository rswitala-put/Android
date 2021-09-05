package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.main.fragments.CurrentFragment
import com.example.myapplication.ui.main.fragments.HistoryFragment
import com.example.myapplication.ui.main.fragments.StartFragment
import com.example.myapplication.ui.main.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

    var firstPlayerName: String = ""
    var secondPlayerName: String = ""
    var firstPlayerStarts: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);

        bindTabs()

        adapter.addPage(StartFragment(), "Start");
    }

    fun addMainFragments(
        firstPlayerName: String,
        secondPlayerName: String,
        firstPlayerStarts: Boolean
    ) {
        this.firstPlayerName = firstPlayerName
        this.secondPlayerName = secondPlayerName
        this.firstPlayerStarts = firstPlayerStarts

        val tabLayout = binding.tabs

        adapter.addPage(CurrentFragment(), "Current game");
        adapter.addPage(HistoryFragment(), "History");

        val tab = tabLayout.getTabAt(1)
        tab!!.select()

        tabLayout.removeTabAt(0)
        adapter.removePage(0)

        bindTabs()
    }

    fun removeMainFragments() {
        val tabLayout = binding.tabs

        bindTabs()

        tabLayout.removeTabAt(1)
        adapter.removePage(1)

        tabLayout.removeTabAt(0)
        adapter.removePage(0)

        adapter.addPage(StartFragment(), "Start");

        val tab = tabLayout.getTabAt(0)
        tab!!.select()

        bindTabs()
    }

    private fun bindTabs() {
        val viewPager: ViewPager = binding.viewPager;
        viewPager.adapter = adapter;

        val tabs: TabLayout = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }
}