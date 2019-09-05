package mobin.expandablerecyclerview.models

import mobin.expandablerecyclerview.adapters.ExpandableRecyclerViewAdapter

data class Parent(val name: String) : ExpandableRecyclerViewAdapter.ExpandableGroup<Child> {

    var expanded = false
    override fun getExpandingItems(): List<Child> {
        val list = ArrayList<Child>(10)
        for (i in 0 until 10)
            list.add(Child("Child $i"))
        return list


    }

    override fun isExpanded(): Boolean {
        return expanded
    }

}