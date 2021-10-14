package com.najed.stackoverflowrss

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var questionsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionsRecyclerView = findViewById(R.id.questions_rv)
        questionsRecyclerView.layoutManager = LinearLayoutManager(this)
        FetchQuestions().execute()
    }


    private inner class FetchQuestions: AsyncTask<Void, Void, ArrayList<Question>>(){

        var questions = arrayListOf<Question>()
        val parser = XMLParser()

        override fun doInBackground(vararg params: Void?): ArrayList<Question> {
            val url = URL("https://stackoverflow.com/feeds")
            val urlConnection = url.openConnection() as HttpURLConnection
            questions = urlConnection.inputStream?.let{ parser.parse(it) } as ArrayList<Question>
            return questions
        }

        override fun onPostExecute(result: ArrayList<Question>?) {
            super.onPostExecute(result)
            questionsRecyclerView.adapter = Adapter(questions, this@MainActivity)
        }
    }
}