package com.eversadclown.borutoapplication.domain.use_cases.read_onboarding

import com.eversadclown.borutoapplication.data.repository.Repository
import kotlinx.coroutines.flow.Flow

//класс для чтения данных приложения из памяти
class ReadOnBoardingUseCase(
    private val repository: Repository
) {

    operator fun invoke() : Flow<Boolean> {
        return repository.readOnBoardingState()
    }

}