package co.mike.zemoga.di.module

import co.mike.zemoga.di.scopes.FragmentScope
import co.mike.zemoga.fragments.FragmentPostList
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilder {

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun bindAuthOptionsFragment(): FragmentPostList


}