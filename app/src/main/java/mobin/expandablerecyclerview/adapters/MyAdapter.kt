package mobin.expandablerecyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.child_row.*
import kotlinx.android.synthetic.main.parent_row.*
import mobin.expandablerecyclerview.R
import mobin.expandablerecyclerview.models.Child
import mobin.expandablerecyclerview.models.Parent

class MyAdapter(parents: ArrayList<Parent>) :
    ExpandableRecyclerViewAdapter<Child, Parent, MyAdapter.PViewHolder, MyAdapter.CViewHolder>(
        parents, ExpandingDirection.VERTICAL
    ) {

    override fun onCreateParentViewHolder(parent: ViewGroup, viewType: Int): PViewHolder {

        return PViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.parent_row,
                parent,
                false
            )
        )
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

    override fun onBindParentViewHolder(
        parentViewHolder: PViewHolder,
        expandableType: Parent,
        position: Int
    ) {
        parentViewHolder.tvP.text = expandableType.name

    }

    override fun onBindChildViewHolder(
        childViewHolder: CViewHolder,
        expandedType: Child,
        expandableType: Parent,
        position: Int
    ) {
        childViewHolder.tvC.text = expandedType.name
    }


    override fun onExpandedClick(
        expandableViewHolder: PViewHolder,
        expandedViewHolder: CViewHolder,
        expandedType: Child,
        expandableType: Parent
    ) {
        Toast.makeText(
            expandableViewHolder.containerView.context,
            expandableType.name + " " + expandedType.name + " Position: " + expandedViewHolder.adapterPosition,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onExpandableClick(
        expandableViewHolder: PViewHolder,
        expandableType: Parent
    ) {
        Toast.makeText(
            expandableViewHolder.containerView.context,
            expandableType.name + " Position: " + expandableViewHolder.adapterPosition,
            Toast.LENGTH_SHORT
        ).show()
    }

    class PViewHolder(v: View) : ExpandableRecyclerViewAdapter.ExpandableViewHolder(v)

    class CViewHolder(v: View) : ExpandableRecyclerViewAdapter.ExpandedViewHolder(v)
}