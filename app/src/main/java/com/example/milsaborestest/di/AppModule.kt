package com.example.milsaborestest.di

import android.content.Context
import com.example.milsaborestest.data.local.database.AppDatabase
import com.example.milsaborestest.data.repository.CartRepositoryImpl
import com.example.milsaborestest.data.repository.ProductRepositoryImpl
import com.example.milsaborestest.data.source.local.ProductJsonDataSource
import com.example.milsaborestest.domain.repository.CartRepository
import com.example.milsaborestest.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideProductJsonDataSource(
        @ApplicationContext context: Context
    ): ProductJsonDataSource {
        return ProductJsonDataSource(context)
    }
    
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
    
    @Provides
    fun provideCartDao(database: AppDatabase) = database.cartDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    
    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
    
    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository
}

