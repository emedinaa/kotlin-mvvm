package com.emedinaa.kotlinmvvm.domain

import com.emedinaa.kotlinmvvm.data.MuseumRepository

/**
 * @author Eduardo Medina
 */
class GetMuseumsUseCase(private val museumRepository: MuseumRepository) {
    suspend operator fun invoke() = museumRepository.fetchMuseums()

    //suspend fun execute() = museumRepository.fetchMuseums()
}