package com.eversadclown.borutoapplication.domain.use_cases.save_onboarding

import com.eversadclown.borutoapplication.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(completed: Boolean) {
        repository.saveOnBoardingState(completed = completed)
    }
}