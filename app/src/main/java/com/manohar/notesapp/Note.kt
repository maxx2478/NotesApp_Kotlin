package com.manohar.notesapp

class Note
{
    var noteid:Int?=null
    var notetitle:String?=null
    var notecontent:String?=null

    constructor(noteid: Int?, notetitle: String?, notecontent: String?) {
        this.noteid = noteid
        this.notetitle = notetitle
        this.notecontent = notecontent
    }


}