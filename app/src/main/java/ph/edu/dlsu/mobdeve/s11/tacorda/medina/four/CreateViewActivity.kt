package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.databinding.ActivityCreateBinding
import ph.edu.dlsu.mobdeve.s11.tacorda.medina.four.databinding.ActivityCreateViewBinding

class CreateViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateViewBinding.inflate(layoutInflater)
        setContentView(binding!!.root)



        binding.bInsertStudent.setOnClickListener{
            val intent = Intent(this,CreateActivity::class.java)
            startActivity(intent)
        }

        val dataHelper = DBHelper(this)
        val playerlist = dataHelper.getAllPlayer()
        var playerAdapter = PlayerAdapter(this@CreateViewActivity,playerlist)
        binding.rvStudent.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CreateViewActivity)
            adapter = playerAdapter
        }

        binding.refreshSwipe.setOnRefreshListener {
            binding.refreshSwipe.isRefreshing=false
            playerAdapter.getUpdate()
        }
    }
}