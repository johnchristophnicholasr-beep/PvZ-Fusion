package com.pvzfusion.game

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.data.GameStateManager
import com.pvzfusion.game.ui.GameActivity
import com.pvzfusion.game.ui.SaveBackupActivity

class MainActivity : AppCompatActivity() {
    private lateinit var manager: GameStateManager
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); manager=GameStateManager(this); showLoading() }
    private fun showLoading() { setContentView(R.layout.activity_loading); val bar=findViewById<ProgressBar>(R.id.loading_bar); val label=findViewById<TextView>(R.id.loading_stage); val percent=findViewById<TextView>(R.id.loading_percent); val stages=listOf("Loading Plants…","Loading Fusion Recipes…","Loading Levels…","Loading Sounds…","Loading Saved Progress…","Starting Game…"); Handler(Looper.getMainLooper()).post(object: Runnable { var p=0; override fun run(){ val i=(p*stages.size/100).coerceAtMost(stages.lastIndex); bar.progress=p; percent.text="$p%"; label.text=stages[i]; if(p<100){p++; Handler(Looper.getMainLooper()).postDelayed(this,18)} else {label.text="Ready!"; Handler(Looper.getMainLooper()).postDelayed({showMenu()},450)} } }) }
    private fun showMenu(){ setContentView(R.layout.activity_main); findViewById<Button>(R.id.btn_continue).setOnClickListener{startGame("story")}; findViewById<Button>(R.id.btn_play).setOnClickListener{startGame("story")}; findViewById<Button>(R.id.btn_tutorial).setOnClickListener{startGame("tutorial")}; findViewById<Button>(R.id.btn_backup).setOnClickListener{startActivity(Intent(this,SaveBackupActivity::class.java))}; findViewById<Button>(R.id.btn_settings).setOnClickListener{Toast.makeText(this,"Offline settings are saved automatically.",Toast.LENGTH_SHORT).show()}; listOf(R.id.btn_almanac,R.id.btn_fusion,R.id.btn_challenge,R.id.btn_puzzle,R.id.btn_survival,R.id.btn_endless,R.id.btn_achievements,R.id.btn_profile).forEach{findViewById<Button>(it).setOnClickListener{Toast.makeText(this,"This offline mode is ready for expansion.",Toast.LENGTH_SHORT).show()}} }
    private fun startGame(mode:String){startActivity(Intent(this,GameActivity::class.java).putExtra("mode",mode))}
}
