package mobin.expandablerecyclerview.models

import mobin.expandablerecyclerview.adapters.ExpandableRecyclerViewAdapter

class Parent(val name: String, val id: Int) : ExpandableRecyclerViewAdapter.Expandable {
    override fun <T : Any> getExpandingItems(): List<T> {

    }
}