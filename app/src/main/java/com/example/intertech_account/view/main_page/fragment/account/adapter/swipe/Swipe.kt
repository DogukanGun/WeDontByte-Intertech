package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.view.main_page.activity.MainActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


//All accounts sayfası için swwipe özelliği sınıfı

abstract class Swipe(
    context: MainActivity,
    private val recyclerView: RecyclerView,
    internal val buttonWidth:Int):ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
    // Gerekli değişkenler
    private var buttonList:MutableList<SwipeButton>?=null
    lateinit var gestureDetector: GestureDetector
    var swipePosition = -1
    var swipeThreshold = 0.5f
    var buttonBuffer:MutableMap<Int,MutableList<SwipeButton>>
    lateinit var removeQueue:LinkedList<Int>

    //S wipe button creator
    abstract fun instantiateSwipeButton(viewHolder: RecyclerView.ViewHolder,
                                        buffer:MutableList<SwipeButton>)


    // Swipe button click listener (true - false)

    private val gestureListener = object:GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            for(button in buttonList!!){
                if(button.onClick(e!!.x,e!!.y))
                    break}

            return true
        }
    }


    // Touch event handler

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = View.OnTouchListener { _, motionEvent ->
        if (swipePosition < 0) return@OnTouchListener false
        /*
        if((recyclerView.findViewHolderForAdapterPosition(swipePosition)?.itemViewType ?:  0) as Boolean){
            return@OnTouchListener false
        }
        if((recyclerView.findViewHolderForAdapterPosition(swipePosition)?.itemViewType ?:  1) as Boolean){
            return@OnTouchListener false
        }*/
        val point = Point(motionEvent.rawX.toInt(), motionEvent.rawY.toInt())
        val swipeViewHolder = recyclerView.findViewHolderForAdapterPosition(swipePosition)
        val swipedItem = swipeViewHolder!!.itemView
        val rect = Rect()
        swipedItem.getGlobalVisibleRect(rect)
        if (motionEvent.action == MotionEvent.ACTION_MOVE ||
            motionEvent.action == MotionEvent.ACTION_UP ||
            motionEvent.action == MotionEvent.ACTION_DOWN

        ) {
            if (rect.top < point.y && rect.bottom > point.y)
                gestureDetector.onTouchEvent((motionEvent))
            else {
                removeQueue.add(swipePosition)
                swipePosition = -1
                recoverSwipeItem()
            }
        }
        false
    }

    private fun recoverSwipeItem() {
        while(!removeQueue.isEmpty()){
            val pos = removeQueue.poll()!!.toInt()
            if(pos > -1)
                recyclerView.adapter!!.notifyItemChanged(pos)
        }

    }
    // sınıf içi initialization

    init{
        this.buttonList = ArrayList()
        this.gestureDetector = GestureDetector(context,gestureListener)
        this.recyclerView.setOnTouchListener(onTouchListener)
        this.buttonBuffer = HashMap()
        this.removeQueue = IntLinkList()

        attachSwipe()

    }

    // Swipe to RecyclerView

    private fun attachSwipe() {
        val itemTouchHelper=ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    //Swipe elemanları için LikedList

    class IntLinkList : LinkedList<Int>() {
        override fun contains(element: Int): Boolean {
            return false
        }

        override fun lastIndexOf(element: Int): Int {
            return element
        }

        override fun remove(element: Int): Boolean {
            return false
        }

        override fun indexOf(element: Int): Int {
            return element
        }

        override fun add(element: Int): Boolean {
            return if(contains(element))
                false
            else
                super.add(element)
        }

    }

    // RecyclerView row hareket ederken false dönüyor durunca true dönecek

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    // Kaydırma thresholdu yakalnınca button oluşturma aksiyonu

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        if(swipePosition !=pos)
            removeQueue.add(swipePosition)
        swipePosition = pos
        if(buttonBuffer.containsKey(swipePosition))
            buttonList=buttonBuffer[swipePosition]
        else
            buttonList!!.clear()
        buttonBuffer.clear()
        swipeThreshold = 0.5f*buttonList!!.size.toFloat()*buttonWidth.toFloat()
        recoverSwipeItem()
    }

    //Getters

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return swipeThreshold
    }
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f*defaultValue
    }
    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 5.0f*defaultValue
    }

    //Child creator ?
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView
        if(pos<0){
            swipePosition = pos
            return
        }
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            if(dX<0){
                var buffer: MutableList<SwipeButton> = ArrayList()
                if(!buttonBuffer.containsKey(pos)){
                    instantiateSwipeButton(viewHolder,buffer)
                    buttonBuffer[pos] = buffer
                }
                else{
                    buffer = buttonBuffer[pos]!!
                }
                translationX = dX*buffer.size.toFloat()*buttonWidth.toFloat()/itemView.width
                drawButton(c,itemView,buffer,pos,translationX)

            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive)
    }

    // Make buttons visible rectangles

    private fun drawButton(c: Canvas, itemView: View, buffer: MutableList<SwipeButton>, pos: Int, translationX: Float) {
        var right = itemView.right.toFloat()
        val dButtonWidth = -1*translationX/buffer.size
        for(button in buffer){
            val left =right-dButtonWidth
            button.onDraw(c, RectF(left,itemView.top.toFloat(),right,itemView.bottom.toFloat()),pos)
            right = left
        }
    }


}