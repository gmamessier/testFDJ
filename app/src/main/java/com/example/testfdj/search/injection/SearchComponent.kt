package com.example.testfdj.search.injection

import com.example.testfdj.search.detail.SearchTeamDetailPresenterImpl
import com.example.testfdj.search.home.SearchPresenterImpl
import com.example.testfdj.search.injection.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface SearchComponent {
    fun inject(searchPresenterImpl: SearchPresenterImpl)
    fun inject(searchTeamDetailPresenterImpl: SearchTeamDetailPresenterImpl)
}