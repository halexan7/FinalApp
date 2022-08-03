/*
* CS3013 - Mobile App Dev. - Summer 2022
* Instructor: Thyago Mota
* Student(s): Horace Alexander
* Description: Quiz Game, Final App
*/

package edu.msudenver.FinalApp

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryScreen: AppCompatActivity(), View.OnLongClickListener {

    lateinit var dbHelper: DBHelper
    lateinit var recyclerView: RecyclerView

    private inner class ScoreHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtId: TextView = view.findViewById(R.id.txtId)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val txtScore: TextView = view.findViewById(R.id.txtScore)
        val txtGametype: TextView = view.findViewById(R.id.txtGametype)
        val txtPlayerName: TextView = view.findViewById(R.id.txtPlayerName)
    }

    private inner class ScoreAdapter(var scores: List<Score>, var onLongClickListener: View.OnLongClickListener): RecyclerView.Adapter<ScoreHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
            view.setOnLongClickListener(onLongClickListener)
            return ScoreHolder(view)
        }

        override fun onBindViewHolder(holder: ScoreHolder, position: Int) {
            val score = scores[position]
            holder.txtId.text = score.id.toString()
            holder.txtScore.text = score.value.toString()
            holder.txtDate.text = DBHelper.USA_FORMAT.format(score.date)
            holder.txtGametype.text = score.game.toString()
            holder.txtPlayerName.text = score.playername.toString()
        }

        override fun getItemCount(): Int {
            return scores.size
        }
    }


    fun populateRecyclerView() {
        val db = dbHelper.readableDatabase
        val items = mutableListOf<Score>()
        val cursor = db.query(
            "scores",
            arrayOf("rowid", "date", "playername", "game", "value"),
            null,
            null,
            null,
            null,
            null,
            null
        )
        with (cursor) {
            while (moveToNext()){
                val id = getInt(0)
                val date = DBHelper.ISO_FORMAT.parse(getString(1))
                val playername = getString(2)
                val game = getString(3)
                val value = getDouble(4)
                val item = Score(id, date, playername, game, value)
                items.add(item)
            }
        }
        recyclerView.adapter = ScoreAdapter(items, this)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        dbHelper = DBHelper(this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        populateRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        populateRecyclerView()
    }

    override fun onLongClick(view: View?): Boolean {
        class MyDialogInterfaceListener(val id: Int): DialogInterface.OnClickListener {
            override fun onClick(dialogInterface: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    try {
                        val db = dbHelper.writableDatabase
                        db.execSQL("""
                            DELETE FROM scores
                            WHERE rowid = "${id}"
                        """)
                        populateRecyclerView()
                    }
                    catch (ex: Exception) {

                    }
                }
            }
        }

        if (view != null) {
            val id = view.findViewById<TextView>(R.id.txtId).text.toString().toInt()
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setMessage("Are you sure you want to delete this score?")
            alertDialogBuilder.setPositiveButton("Yes", MyDialogInterfaceListener(id))
            alertDialogBuilder.setNegativeButton("No", MyDialogInterfaceListener(id))
            alertDialogBuilder.show()
            return true
        }
        return false
    }
}