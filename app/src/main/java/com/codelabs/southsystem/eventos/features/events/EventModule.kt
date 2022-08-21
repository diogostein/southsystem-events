package com.codelabs.southsystem.eventos.features.events

import android.content.Context
import com.codelabs.southsystem.eventos.core.helpers.GeocoderHelper
import com.codelabs.southsystem.eventos.core.network.WebService
import com.codelabs.southsystem.eventos.features.events.data.EventRepositoryImpl
import com.codelabs.southsystem.eventos.features.events.domain.repositories.EventRepository
import com.codelabs.southsystem.eventos.features.events.domain.usecases.GetEventDetailUseCase
import com.codelabs.southsystem.eventos.features.events.domain.usecases.GetEventsUseCase
import com.codelabs.southsystem.eventos.features.events.domain.usecases.PerformCheckInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object EventModule {

    @Provides
    @ViewModelScoped
    fun provideEventRepository(webService: WebService): EventRepository {
        return EventRepositoryImpl(webService)
    }

    @Provides
    @ViewModelScoped
    fun provideGetEventsUseCase(repository: EventRepository): GetEventsUseCase {
        return GetEventsUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetEventDetailUseCase(repository: EventRepository): GetEventDetailUseCase {
        return GetEventDetailUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun providePerformCheckInUseCase(repository: EventRepository): PerformCheckInUseCase {
        return PerformCheckInUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGeocodeHelper(
        @ApplicationContext context: Context,
        @Named("IODispatcher") dispatcher: CoroutineDispatcher
    ): GeocoderHelper {
        return GeocoderHelper(context, dispatcher)
    }

}