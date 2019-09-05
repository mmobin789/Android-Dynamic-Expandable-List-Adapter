package mobin.expandablerecyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.child_row.*
import kotlinx.android.synthetic.main.parent_row.*
import mobin.expandablerecyclerview.R
import mobin.expandablerecyclerview.models.Child
import mobin.expandablerecyclerview.models.Parent

class MyAdapter(parents: List<Parent>) :
    ExpandableRecyclerViewAdapter<Child, Parent, MyAdapter.PViewHolder, MyAdapter.CViewHolder>(
        parents, ExpandingDirection.VERTICAL
    ) {

    override fun onCreateParentViewHolder(parent: ViewGroup, viewType: Int): PViewHolder {

        val pvh = PViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.parent_row,
                parent,
                false
            )
        )

        return pvh
    }


    override fun onCreateChildViewHolder(child: ViewGroup, viewType: Int): CViewHolder {
        return CViewHolder(
            LayoutInflater.from(child.context).inflate(
                R.layout.child_row,
                child,
                false
            )
        )
    }

    override fun onBindParentViewHolder(parentViewHolder: PViewHolder, expandableType: Parent) {
        parentViewHolder.tvP.text = expandableType.name

    }

    override fun onBindChildViewHolder(childViewHolder: CViewHolder, expandedType: Child) {
        childViewHolder.tvC.text = expandedType.name
    }

    override fun onParentViewClicked(expandableType: Parent, position: Int) {
        // expandableType.expanded =

    }

    override fun isSingleExpanded(): Boolean {
        return false
    }

    class PViewHolder(v: View) : ExpandableRecyclerViewAdapter.ParentViewHolder(v)

    class CViewHolder(v: View) : ExpandableRecyclerViewAdapter.ChildViewHolder(v)
}