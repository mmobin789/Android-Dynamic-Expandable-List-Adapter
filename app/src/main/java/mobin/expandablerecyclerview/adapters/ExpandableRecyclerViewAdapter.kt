package mobin.expandablerecyclerview.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * ExpandableGroup Adapter for recycler view needs a child type and parent type and a parent list in constructor
 * to create an expandable listing view UI
 * @param ExpandableGroup Parent Type (This is the class providing concrete implementation of the ExpandableGroup Interface)
 * @param ExpandedType Child Type (This can be a subtype of Any object)
 * @author Mobin Munir
 */
abstract class ExpandableRecyclerViewAdapter<ExpandedType : Any, ExpandableGroup : ExpandableRecyclerViewAdapter.ExpandableGroup<ExpandedType>, PVH : ExpandableRecyclerViewAdapter.ParentViewHolder, CVH : ExpandableRecyclerViewAdapter.ChildViewHolder>
    (
    private val mExpandableList: ArrayList<ExpandableGroup>,
    private val expandingDirection: ExpandingDirection
) :
    RecyclerView.Adapter<PVH>() {

    private var expanded = false

    private var lastExpandedPosition = -1

    private var adapterAttached = false

    private var recyclerView: RecyclerView? = null

    private val TAG = "ExpandableGroupAdapter"


    enum class ExpandingDirection {
        HORIZONTAL,
        VERTICAL
    }


    private fun initializeChildRecyclerView(childRecyclerView: RecyclerView?) {

        if (childRecyclerView != null) {

            val linearLayoutManager = LinearLayoutManager(childRecyclerView.context)

            linearLayoutManager.orientation = if (expandingDirection == ExpandingDirection.VERTICAL)
                LinearLayoutManager.VERTICAL
            else LinearLayoutManager.HORIZONTAL

            childRecyclerView.layoutManager = linearLayoutManager


//            childRecyclerView.visibility = if (initiallyExpanded)
//                View.VISIBLE
//            else View.GONE


        }
    }


    override fun getItemCount(): Int {
        return mExpandableList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PVH {
        return onCreateParentView(parent, viewType)
    }


    private fun onCreateParentView(parent: ViewGroup, viewType: Int): PVH {
        val pvh = onCreateParentViewHolder(parent, viewType)

        initializeChildRecyclerView(pvh.containerView.getRecyclerView())

        pvh.containerView.setOnClickListener {
            val position = pvh.adapterPosition
            val expandable = mExpandableList[position]

            if (isSingleExpanded())
                handleSingleExpansion(position)
            else handleExpansion(expandable, position)

            handleLastPositionScroll(position)

            onParentViewClicked(expandable, position)

            Log.d(TAG, "Clicked @ $position")
        }

//        pvh.containerView.setOnLongClickListener {
//            removeGroup(pvh.adapterPosition)
//            true
//        }


        return pvh
    }

    private fun collapseAllGroups() {
        mExpandableList.applyExpansionState(false)
        expanded = false


    }

    private fun reverseExpandableState(expandableGroup: ExpandableGroup) {
        expandableGroup.isExpanded = !expandableGroup.isExpanded
    }

    private fun collapseAllExcept(position: Int) {
        val expandableGroup = mExpandableList[position]
        reverseExpandableState(expandableGroup)
        notifyItemChanged(position)
        if (lastExpandedPosition > -1 && lastExpandedPosition != position) {
            val previousExpandableGroup = mExpandableList[lastExpandedPosition]
            if (previousExpandableGroup.isExpanded) {
                previousExpandableGroup.isExpanded = false
                notifyItemChanged(lastExpandedPosition)
            }
        }

        lastExpandedPosition = position

    }

    private fun handleSingleExpansion(position: Int) {
        if (expanded) {
            collapseAllGroups()
        } else {
            collapseAllExcept(position)

        }


    }

    private fun handleExpansion(expandableGroup: ExpandableGroup, position: Int) {
        reverseExpandableState(expandableGroup)
        notifyItemChanged(position)


    }

    private fun handleLastPositionScroll(position: Int) {
        if (position == mExpandableList.lastIndex)
            recyclerView?.smoothScrollToPosition(position)
    }


    override fun onBindViewHolder(holder: PVH, position: Int) {
        setupChildRecyclerView(holder, position)

    }

    private fun setupChildRecyclerView(holder: PVH, position: Int) {
        val expandableGroup = mExpandableList[position]
        val childListAdapter = ChildListAdapter(
            expandableGroup
        ) { viewGroup, viewType ->
            onCreateChildViewHolder(viewGroup, viewType)

        }
        val childRecyclerView = holder.containerView.getRecyclerView()

        if (childRecyclerView?.adapter == null)
            childRecyclerView?.adapter = childListAdapter

        clickEvent(expandableGroup, holder.containerView)


        onBindParentViewHolder(holder, expandableGroup)
    }

    private fun clickEvent(expandableGroup: ExpandableGroup, containerView: View) {
        val childRecyclerView = containerView.getRecyclerView()

        childRecyclerView?.visibility = if (expandableGroup.isExpanded)
            View.VISIBLE
        else View.GONE

    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        adapterAttached = true

        this.recyclerView = recyclerView

        Log.d(TAG, "Attached: $adapterAttached")
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        adapterAttached = false
        this.recyclerView = null
    }

    /**
     * Specifies if you want to show all items expanded in UI.
     * @param expanded A bit to enable/disable initial expansion.
     * Note: If any group is clicked initial Expansion is instantly set to false.
     */
    fun setExpanded(expanded: Boolean) {
        this.expanded = expanded
        if (expanded)
            mExpandableList.applyExpansionState(true)
    }

    /**
     * A swift method to add a new group to the list.
     * @param expandableGroup The new group.
     * @param expanded An optional state for expansion to apply (false by default).
     * @param position An optional current position at which to insert the new group in the adapter. (not applicable by default).
     */
    fun addGroup(expandableGroup: ExpandableGroup, expanded: Boolean = false, position: Int = -1) {


        var atPosition = itemCount

        if (position > atPosition) {
            Log.e(TAG, "Position to add group exceeds the total group count of $atPosition")
            return
        }


        expandableGroup.isExpanded = expanded



        if (position == -1 || position == atPosition)
            mExpandableList.add(expandableGroup)
        else if (position > -1) {
            mExpandableList.add(position, expandableGroup)
            atPosition = position
        }

        if (adapterAttached)
            notifyItemInserted(atPosition)

        Log.d(TAG, "Group added at $atPosition")


    }

    /**
     * A swift method to remove a group from the list.
     * @param position The current position of the group the adapter.
     */
    fun removeGroup(position: Int) {

        if (position < 0 || position > itemCount) {
            Log.e(TAG, "Group can't be removed at position $position")
            return
        }

        mExpandableList.removeAt(position)

        if (adapterAttached)
            notifyItemRemoved(position)

        Log.d(TAG, "Group removed at $position")

    }

    /**
     * Synchronously applies the expansion state of all the list in a background thread swiftly
     * and notifies the adapter in an efficient manner to dispatch updates.
     * @param expansionState The expansion state to apply to all list.
     * This method can be made public to work on subset of @see ExpandableGroup Class by declaring it outside this class
     */
    private fun List<ExpandableGroup>.applyExpansionState(expansionState: Boolean) {

        GlobalScope.launch(Dispatchers.IO) {
            forEach {
                it.isExpanded = expansionState


            }
        }
        if (adapterAttached)
            notifyItemRangeChanged(0, itemCount)
    }

    /**
     * Searches View hierarchy for an instance of RecyclerView
     * @return RecyclerView or null if not found
     */
    private fun View.getRecyclerView(): RecyclerView? {
        if (this is ViewGroup && childCount > 0) {
            forEach {
                if (it is RecyclerView) {
                    return it
                }

            }
        }
        Log.e(TAG, "Recycler View for expanded items not found in parent layout.")
        return null
    }

    private inner class ChildListAdapter(
        private val expandableGroup: ExpandableGroup,
        private val onChildRowCreated: (ViewGroup, Int) -> CVH
    ) :
        RecyclerView.Adapter<CVH>() {

        private val mExpandedList = expandableGroup.getExpandingItems()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CVH {
            val cvh = onChildRowCreated(parent, viewType)
            cvh.containerView.setOnClickListener {
                val position = cvh.adapterPosition
                val expandedType = mExpandedList[position]
                onChildViewClicked(expandedType, expandableGroup, position)
            }
            return cvh
        }

        override fun getItemCount(): Int {
            return mExpandedList.size
        }

        override fun onBindViewHolder(holder: CVH, position: Int) {
            val expanded = mExpandedList[position]
            onBindChildViewHolder(holder, expanded)
        }

    }

    abstract class ParentViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer

    abstract class ChildViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer


    abstract class ExpandableGroup<out E> {
        /**
         * returns a list of provided type to be used for expansion.
         */
        abstract fun getExpandingItems(): List<E>


        /**
         *   Specifies if you want to show the UI in expanded form.
         */
        var isExpanded = false
    }


    abstract fun onCreateParentViewHolder(parent: ViewGroup, viewType: Int): PVH

    abstract fun onBindParentViewHolder(parentViewHolder: PVH, expandableType: ExpandableGroup)

    abstract fun onCreateChildViewHolder(child: ViewGroup, viewType: Int): CVH

    abstract fun onBindChildViewHolder(childViewHolder: CVH, expandedType: ExpandedType)

    abstract fun onParentViewClicked(expandableGroup: ExpandableGroup, position: Int)

    abstract fun onChildViewClicked(
        expandedType: ExpandedType,
        expandableGroup: ExpandableGroup,
        position: Int
    )

    /**
     * Specifies if you want to show one item expanded in UI at most.
     * @return true to enable one child expansion at a time.
     */
    abstract fun isSingleExpanded(): Boolean

}

