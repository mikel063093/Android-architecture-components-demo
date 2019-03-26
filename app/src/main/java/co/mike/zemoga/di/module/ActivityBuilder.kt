package co.mike.zemoga.di.module

import co.mike.zemoga.activities.DetailActivity
import co.mike.zemoga.activities.MainActivity
import co.mike.zemoga.di.scopes.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [DatabaseModule::class, GeneralService::class])
abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentsBuilder::class])
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindDetailActivity(): DetailActivity

}