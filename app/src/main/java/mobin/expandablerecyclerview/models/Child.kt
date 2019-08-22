package mobin.expandablerecyclerview.models

import mobin.expandablerecyclerview.adapters.ExpandableRecyclerViewAdapter

class Child(val name: String, val id: Int) : ExpandableRecyclerViewAdapter.Expanded {

    override fun getParent(): ExpandableRecyclerViewAdapter.Expandable {
        return Parent("Hmm", 3)
    }
}