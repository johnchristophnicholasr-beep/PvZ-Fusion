package com.pvzfusion.game

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.pvzfusion.game.data.GameStateManager
import com.pvzfusion.game.ui.*

class MainActivity : AppCompatActivity() {
    private lateinit var manager: GameStateManager
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); manager=GameStateManager(this); showLoading() }
    private fun showLoading() { setContentView(R.layout.activity_loading); val bar=findViewById<ProgressBar>(R.id.loading_bar); val label=findViewById<TextView>(R.id.loading_stage); val percent=findViewById<TextView>(R.id.loading_percent); val stages=listOf("Loading Plants…","Loading Fusion Recipes…","Loading Levels…","Loading Sounds…","Loading Saved Progress…","Starting Game…"); Handler(Looper.getMainLooper()).post(object: Runnable { var p=0; override fun run(){ val i=(p*stages.size/100).coerceAtMost(stages.lastIndex); bar.progress=p; percent.text="$p%"; label.text=stages[i]; if(p<100){p++; Handler(Looper.getMainLooper()).postDelayed(this,18)} else {label.text="Ready!"; Handler(Looper.getMainLooper()).postDelayed({showMenu()},450)} } }) }
    private fun showMenu(){ setContentView(R.layout.activity_main); findViewById<Button>(R.id.btn_continue).setOnClickListener{startGame("story")}; findViewById<Button>(R.id.btn_play).setOnClickListener{startGame("story")}; findViewById<Button>(R.id.btn_tutorial).setOnClickListener{startActivity(Intent(this,TutorialActivity::class.java))}; findViewById<Button>(R.id.btn_almanac).setOnClickListener{startActivity(Intent(this,AlmanacActivity::class.java))}; findViewById<Button>(R.id.btn_fusion).setOnClickListener{startActivity(Intent(this,FusionEncyclopediaActivity::class.java))}; findViewById<Button>(R.id.btn_challenge).setOnClickListener{startActivity(Intent(this,ChallengeActivity::class.java))}; findViewById<Button>(R.id.btn_achievements).setOnClickListener{startActivity(Intent(this,AchievementsActivity::class.java))}; findViewById<Button>(R.id.btn_profile).setOnClickListener{startActivity(Intent(this,ProfileActivity::class.java))}; findViewById<Button>(R.id.btn_backup).setOnClickListener{startActivity(Intent(this,SaveBackupActivity::class.java))}; findViewById<Button>(R.id.btn_puzzle).setOnClickListener{Toast.makeText(this,"Puzzle mode coming soon.",Toast.LENGTH_SHORT).show()}; findViewById<Button>(R.id.btn_survival).setOnClickListener{Toast.makeText(this,"Survival mode coming soon.",Toast.LENGTH_SHORT).show()}; findViewById<Button>(R.id.btn_endless).setOnClickListener{Toast.makeText(this,"Endless mode coming soon.",Toast.LENGTH_SHORT).show()}; findViewById<Button>(R.id.btn_settings).setOnClickListener{Toast.makeText(this,"Offline settings are saved automatically.",Toast.LENGTH_SHORT).show()} }
    private fun startGame(mode:String){startActivity(Intent(this,GameActivity::class.java).putExtra("mode",mode))}
}
