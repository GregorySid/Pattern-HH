package com.example.jobsearch.ui.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobsearch.ui.db.MainDb
import com.example.jobsearch.ui.db.Repo
import com.example.jobsearch.ui.vModel.SearchVM

//class FactoryVM(private val mainDb: MainDb) : ViewModelProvider.NewInstanceFactory() {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when (modelClass) {
//            SearchVM::class.java -> SearchVM(Repo(mainDb))
//            else -> throw IllegalAccessException("Cannot fild $modelClass")
//        } as T
//    }
//}