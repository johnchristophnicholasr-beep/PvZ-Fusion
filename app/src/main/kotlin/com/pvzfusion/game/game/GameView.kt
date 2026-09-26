package com.pvzfusion.game.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.pvzfusion.game.data.*
import kotlin.math.max

class GameView(context: Context, attrs: AttributeSet?=null): View(context,attrs) {
 private val paint=Paint(Paint.ANTI_ALIAS_FLAG); private var sun=150; private var wave=1; private var selected="peashooter"; private val plants=PlantDatabase.getBasePlants(); private val placed=mutableListOf<Pair<Int,Int>>(); private var last=0L
 override fun onDraw(c:Canvas){super.onDraw(c); val w=width.toFloat(); val h=height.toFloat(); c.drawColor(Color.rgb(126,198,91)); paint.color=Color.rgb(220,245,255); c.drawRect(0f,0f,w,h*.16f,paint); paint.color=Color.DKGRAY; paint.textSize=34f; c.drawText("Wave $wave    Sun $sun",20f,55f,paint); paint.textSize=20f; c.drawText("Tap a tile to plant • Tap the palette to select",20f,95f,paint); paint.color=Color.rgb(91,64,40); for(row in 0..4) for(col in 0..7){val l=col*w/8;val t=h*.2f+row*h*.14f; paint.color=if((row+col)%2==0)Color.rgb(112,181,72) else Color.rgb(101,169,66);c.drawRect(l+2,t+2,l+w/8-2,t+h*.14f-2,paint)}; placed.forEach{(col,row)->paint.color=if(selected.contains("cherry"))Color.RED else Color.rgb(55,150,55);c.drawCircle((col+.5f)*w/8,h*.2f+(row+.5f)*h*.14f,28f,paint)};paint.color=Color.WHITE; c.drawText("PEA",20f,h-95,paint);c.drawText("CHERRY",100f,h-95,paint);c.drawText("ICE",220f,h-95,paint) }
 override fun onTouchEvent(e:MotionEvent):Boolean{if(e.action!=MotionEvent.ACTION_UP)return true; val h=height.toFloat(); if(e.y>h-140){selected=when{e.x<95->"peashooter";e.x<205->"cherry_bomb";else->"ice_peashooter"};invalidate();return true};if(e.y>h*.2f&&e.y<h*.9f){val col=(e.x/(width/8f)).toInt().coerceIn(0,7);val row=((e.y-h*.2f)/(h*.14f)).toInt().coerceIn(0,4);if(!placed.contains(col to row)&&sun>=plants.firstOrNull{it.id==selected}?.cost?:0){sun-=plants.firstOrNull{it.id==selected}?.cost?:0;placed.add(col to row);invalidate()}};return true}
}
