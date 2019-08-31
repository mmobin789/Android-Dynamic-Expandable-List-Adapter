package mobin.expandablerecyclerview.models

import mobin.expandablerecyclerview.adapters.ExpandableRecyclerViewAdapter

data class Parent(val name: String) : ExpandableRecyclerViewAdapter.Expandable<Child> {

    override fun getExpandingItems(): List<Child> {
        val list = ArrayList<Child>(20)
        for (i in 0 until 20)
            list.add(Child("Child $i"))
        return list


    }

    override fun isExpanded(): Boolean {
        return super.isExpanded()
    }

}