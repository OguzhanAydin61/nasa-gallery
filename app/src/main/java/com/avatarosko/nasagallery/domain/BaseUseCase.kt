package com.avatarosko.nasagallery.domain

import kotlinx.coroutines.flow.Flow


abstract class BaseUseCase<In, Out> {

    abstract fun execute(input: In): Flow<Out>

}
