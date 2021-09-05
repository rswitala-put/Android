package com.example.myapplication.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val fragments = ArrayList<Fragment>(3);
    private val fragmentTitles = ArrayList<String>(3);

    override fun getItem(position: Int): Fragment{
        return fragments[position];
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitles[position];
    }

    override fun getCount(): Int {
       return fragments.size
    }

    fun addPage(fragment: Fragment, title: String){
        fragments.add(fragment);
        fragmentTitles.add(title);
        notifyDataSetChanged()
    }

    fun removePage(position: Int){
        fragments.removeAt(position)
        fragmentTitles.removeAt(position)
        notifyDataSetChanged()
    }

}