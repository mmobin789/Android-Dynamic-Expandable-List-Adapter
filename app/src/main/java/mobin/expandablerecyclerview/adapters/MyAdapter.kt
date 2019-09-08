package mobin.expandablerecyclerview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.child_row.*
import kotlinx.android.synthetic.main.parent_row.*
import mobin.expandablerecyclerview.R
import mobin.expandablerecyclerview.models.Child
import mobin.expandablerecyclerview.models.Parent

class MyAdapter(private val context: Context, parents: ArrayList<Parent>) :
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

    override fun onBindParentViewHolder(parentViewHolder: PViewHolder, expandableType: Parent) {
        parentViewHolder.tvP.text = expandableType.name

    }

    override fun onBindChildViewHolder(childViewHolder: CViewHolder, expandedType: Child) {
        childViewHolder.tvC.text = expandedType.name
    }

    override fun onParentViewClicked(expandableGroup: Parent, position: Int) {
        Toast.makeText(context, expandableGroup.name + " Position: " + position, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onChildViewClicked(expandedType: Child, expandableGroup: Parent, position: Int) {
        Toast.makeText(
            context,
            expandableGroup.name + " " + expandedType.name + " Position: " + position,
            Toast.LENGTH_SHORT
        ).show()
    }


    override fun isSingleExpanded(): Boolean {
        return false
    }

    class PViewHolder(v: View) : ExpandableRecyclerViewAdapter.ParentViewHolder(v)

    class CViewHolder(v: View) : ExpandableRecyclerViewAdapter.ChildViewHolder(v)
}