package co.mike.zemoga.base.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import co.mike.zemoga.base.BaseFragment


class PagerAdapter(fm: FragmentManager?, private val fragments: Array<BaseFragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): BaseFragment = fragments[position]

    override fun getCount(): Int = fragments.size
}