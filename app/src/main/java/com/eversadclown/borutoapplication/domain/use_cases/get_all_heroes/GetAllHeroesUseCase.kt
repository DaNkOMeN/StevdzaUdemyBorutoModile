package com.eversadclown.borutoapplication.domain.use_cases.get_all_heroes

import com.eversadclown.borutoapplication.data.repository.Repository

//юзкейс получения всех персонажей из репозитория (БД)
class GetAllHeroesUseCase(private val repository: Repository) {
    operator fun invoke() = repository.getAllHeroes()

}