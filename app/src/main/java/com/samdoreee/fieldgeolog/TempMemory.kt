package com.samdoreee.fieldgeolog

import android.app.Application
import com.samdoreee.fieldgeolog.record.Memo
import com.samdoreee.fieldgeolog.record.Project
import com.samdoreee.fieldgeolog.record.Record

public class TempMemory : Application() {
    companion object {
        public var tempprojectmemory = arrayListOf<Project>(
            Project("1st record", "Hyerim Pung", "Cheongju", "igneous_3434"),
            Project("2nd record", "Minju Kim", "Yongin", "seimentary_1759"),
            Project("3rd record", "Chisan Ahn", "Seoul", "seimentary_2389"),
            Project("4th record", "Jinyoung Lee", "Busan", ""),
            Project("5th record", "Minjeong Seo", "Jeonju", "seimentary_2389"),
            Project("6th record", "Seunghyeon Lee", "Daejeon", "igneous_3434")
        )
        public var temprecordmemory = arrayListOf<Record>()
        public var tempmemomemory = arrayListOf<Memo>()
    }
}

