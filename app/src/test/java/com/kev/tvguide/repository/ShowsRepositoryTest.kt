package com.kev.tvguide.repository

import com.kev.tvguide.data.remote.APIService
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShowsRepositoryTest {
    @Mock
    private lateinit var apiService: APIService

    private lateinit var repository: ShowsRepository

    @Before
    fun setup() {
        repository = ShowsRepository(apiService)
    }
}