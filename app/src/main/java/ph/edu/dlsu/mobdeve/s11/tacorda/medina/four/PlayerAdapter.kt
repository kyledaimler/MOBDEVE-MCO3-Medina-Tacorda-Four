package ph.edu.dlsu.mobdeve.s11.tacorda.medina.four

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

//Adapter to display profiles in the application

class PlayerAdapter (private var context: Context, private var playerList: ArrayList<Player>) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_student,parent,false))
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    override fun onBindViewHolder(holder: PlayerAdapter.ViewHolder, position: Int) {
        holder.onBind(playerList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun onBind(player: Player){

            context = itemView.getContext()

            itemView.setOnClickListener {
                context.startActivity(Intent(context, MainMenuActivity::class.java))
            }


            val Wins = itemView.findViewById<TextView>(R.id.tv_nim)
            Wins.text = "Wins:" + player.wins.toString()

            val Name = itemView.findViewById<TextView>(R.id.tv_name)
            Name.text = player.name


            if (player.gender=="Male"){
                itemView.findViewById<ImageView>(R.id.iv_student).setImageResource(R.drawable.maleprofile)
            }
            else{
                itemView.findViewById<ImageView>(R.id.iv_student).setImageResource(R.drawable.femaleprofile)
            }
            itemView.setOnLongClickListener{
                val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                alertDialogBuilder.setTitle("Confirm")
                    .setMessage("Are you sure to delete "+player.name+"?")
                    .setCancelable(true)
                    .setPositiveButton("No"){dialog,which->
                        Toast.makeText(itemView.context, "Delete Cancelled", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Yes"){dialog,which->
                        val db = DBHelper(itemView.context)
                        db.deletePlayer(player)
                        playerList.remove(player)
                        notifyDataSetChanged()
                        Toast.makeText(itemView.context,"Sucessfully Deleted", Toast.LENGTH_SHORT).show()
                    }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
                true
            }
        }
    }

    fun getUpdate(){
        val db = DBHelper(context)
        playerList = db.getAllPlayer()
        notifyDataSetChanged()
    }
}