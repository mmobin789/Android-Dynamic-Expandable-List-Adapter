package mobin.expandablerecyclerview.models

import mobin.expandablerecyclerview.adapters.ExpandableRecyclerViewAdapter

data class Parent(val name: String) : ExpandableRecyclerViewAdapter.ExpandableGroup<Child>() {


    override fun getExpandingItems(): List<Child> {
        val list = ArrayList<Child>(50)
        for (i in 0..50)
            list.add(Child("Child $i"))
        return list


    }


}