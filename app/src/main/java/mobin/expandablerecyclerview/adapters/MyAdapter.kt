package mobin.expandablerecyclerview.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mobin.expandablerecyclerview.models.Child
import mobin.expandablerecyclerview.models.Parent

class MyAdapter :
    ExpandableRecyclerViewAdapter<Parent, Child, MyAdapter.PViewHolder, MyAdapter.CViewHolder>(listOf(Parent("Munir"))) {


    override fun onCreateParentViewHolder(vg: ViewGroup, viewType: Int): PViewHolder {
        val textView = TextView(vg.context)
        textView.text = "Parent"
        return PViewHolder(textView)
    }

    override fun onCreateChildViewHolder(vg: ViewGroup, viewType: Int): CViewHolder {
        val textView = TextView(vg.context)
        textView.text = "Child"
        return CViewHolder(textView)
    }


    class PViewHolder(v: View) : ExpandableRecyclerViewAdapter.ParentViewHolder(v)

    class CViewHolder(v: View) : ExpandableRecyclerViewAdapter.ChildViewHolder(v)
}