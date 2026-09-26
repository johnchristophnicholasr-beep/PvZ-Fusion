package com.pvzfusion.game.ui

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.R
import com.pvzfusion.game.data.GameStateManager

class SaveBackupActivity : AppCompatActivity() {
    private lateinit var manager: GameStateManager
    private lateinit var list: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_save_backup); manager=GameStateManager(this); list=findViewById(R.id.slot_list); refresh() }
    private fun refresh() {
        list.removeAllViews()
        manager.allSlotSummaries().forEach { slot ->
            val text = if (slot.exists) "Slot ${slot.slotId + 1}: ${slot.playerName}\nWorld ${slot.world + 1}, Level ${slot.level + 1} • ${slot.completion}% complete\nPlants ${slot.plants} • Fusions ${slot.fusions}" else "Slot ${slot.slotId + 1}: Empty"
            val row = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(16,16,16,16) }
            row.addView(TextView(this).apply { this.text=text; textSize=16f })
            val actions = LinearLayout(this)
            actions.addView(Button(this).apply { this.text=if(slot.exists) "Continue" else "New Game"; setOnClickListener { if(slot.exists) finish() else newGame(slot.slotId) } })
            if (slot.exists) actions.addView(Button(this).apply { text="Delete"; setOnClickListener { confirmDelete(slot.slotId) } })
            row.addView(actions); list.addView(row)
        }
    }
    private fun newGame(slot: Int) { EditText(this).also { input -> input.hint="Player name"; AlertDialog.Builder(this).setTitle("New Game").setView(input).setPositiveButton("Create") { _, _ -> manager.createNewGame(slot,input.text.toString()); refresh() }.setNegativeButton("Cancel",null).show() } }
    private fun confirmDelete(slot: Int) { AlertDialog.Builder(this).setTitle("Delete save?").setMessage("This permanently deletes this slot's active save and automatic backups.").setPositiveButton("Delete") { _, _ -> manager.deleteSlot(slot); refresh() }.setNegativeButton("Cancel",null).show() }
}
