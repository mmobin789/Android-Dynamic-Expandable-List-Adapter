package mobin.expandablerecyclerview.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Expandable Adapter for recycler view needs a child type and parent type and a parent list in constructor
 * to create an expandable listing view UI
 * @param ExpandableType Parent Type (This is the class providing concrete implementation of the Expandable Interface)
 * @param ExpandedType Child Type (This is the class providing concrete implementation of the Expanded Interface)
 */
abstract class ExpandableRecyclerViewAdapter<ExpandedType : ExpandableRecyclerViewAdapter.Expanded, ExpandableType : ExpandableRecyclerViewAdapter.Expandable<ExpandedType>, PVH : ExpandableRecyclerViewAdapter.ParentViewHolder, CVH : ExpandableRecyclerViewAdapter.ChildViewHolder>
    (private val mExpandableList: List<ExpandableType>) :
    RecyclerView.Adapter<PVH>() {

    private var expanded = false

    private var singleExpanded = true

    /*
     A list to keep account of expanded items.
     */
    private val selectedIndexList by lazy {
        val list = ArrayList<Int>(itemCount)

        list
    }

    override fun getItemCount(): Int {
        return mExpandableList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PVH {
        return onCreateParentViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: PVH, position: Int) {
        val expandableType = mExpandableList[position]
        val childListAdapter = ChildListAdapter(
            expandableType.getExpandingItems()
        ) { viewGroup, viewType ->
            onCreateChildViewHolder(viewGroup, viewType)
        }
        val childRecyclerView = holder.containerView.getRecyclerView()
        childRecyclerView?.adapter = childListAdapter

        onBindParentViewHolder(holder, expandableType)

    }


    abstract class ParentViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init {
            itemView.setOnClickListener {

            }
        }
    }

    abstract class ChildViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer


    interface Expandable<E : Expanded> {
        fun getExpandingItems(): List<E>
        fun isExpanded(): Boolean {
            return false
        }

    }

    interface Expanded


    abstract fun onCreateParentViewHolder(parent: ViewGroup, viewType: Int): PVH

    abstract fun onBindParentViewHolder(parentViewHolder: PVH, expandableType: ExpandableType)

    abstract fun onCreateChildViewHolder(child: ViewGroup, viewType: Int): CVH

    abstract fun onBindChildViewHolder(childViewHolder: CVH, expandedType: ExpandedType)

    /**
     *   Specifies if you want to show the UI in fully-expanded form.
     *   @param expanded A bit to toggle expansion.
     *   Default value is false.
     */
    fun setExpanded(expanded: Boolean) {
        this.expanded = expanded
    }

    /**
     * Specifies if you want to show one item expanded in UI.
     * @param singleExpanded A bit to toggle singular expansion.
     * Note: If you have setExpanded() to true then call to this method with either true or false will have no effect.
     */
    fun singleExpanded(singleExpanded: Boolean) {
        this.singleExpanded = singleExpanded
    }


    private inner class ChildListAdapter(
        private val mExpandedList: List<ExpandedType>,
        private val onChildRowCreated: (ViewGroup, Int) -> CVH
    ) :
        RecyclerView.Adapter<CVH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CVH {
            return onChildRowCreated(parent, viewType)
        }

        override fun getItemCount(): Int {
            return mExpandedList.size
        }

        override fun onBindViewHolder(holder: CVH, position: Int) {
            val expanded = mExpandedList[position]
            onBindChildViewHolder(holder, expanded)
        }

    }

    /**
     * Searches View hierarchy for an instance of RecyclerView
     * @return RecyclerView or null if not found
     */
    private fun View.getRecyclerView(): RecyclerView? {
        if (this is ViewGroup && childCount > 0) {
            forEach {
                if (it is RecyclerView)
                    return it

            }
        }
        Log.e("ExpandableAdapter", "Recycler View for expanded items not found in parent layout.")
        return null
    }
}

