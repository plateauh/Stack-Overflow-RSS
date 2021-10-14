package com.najed.stackoverflowrss

import androidx.core.text.htmlEncode
import androidx.core.text.parseAsHtml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.lang.Exception

class XMLParser {
    private var text: String? = null

    private lateinit var qustions: ArrayList<Question>
    private var questionTitle = ""
    private var questionAuthor = ""
    private var questionCategories = ""
    private var questionDetails = ""

    fun parse (stream: InputStream): ArrayList<Question> {
        qustions = arrayListOf()
        try {
            val parser = XmlPullParserFactory.newInstance().newPullParser()
            parser.setInput(stream, null)
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name
                when (eventType){
                    XmlPullParser.START_TAG -> startTag(parser, tagName)
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> endTag(tagName)
                }
                eventType = parser.next()
            }
        } catch (e: Exception){ e.printStackTrace() }
        return qustions
    }

    private fun startTag(parser: XmlPullParser, tagName: String){
        if (tagName.equals("category", true)){
            questionCategories += "${parser.getAttributeValue(1)}, "
        }
    }

    private fun endTag(tagName: String){
        when {
            tagName.equals("title", true) -> {
                questionTitle = text.toString()
            }
            tagName.equals("name", true) -> {
                questionAuthor = text.toString()
            }
            tagName.equals("summary", true) -> {
                questionDetails = text.toString()
            }
            tagName.equals("entry", true) -> {
                questionCategories = questionCategories.removeSuffix(", ")
                qustions.add(Question(questionTitle, questionAuthor, questionCategories, questionDetails))
                questionCategories = ""
            }
        }
    }
}