@file:JvmName("Mappers")

package com.mapbox.services.android.navigation.v5.utils.extensions

import com.mapbox.api.directions.v5.WalkingOptions
import com.mapbox.services.android.navigation.v5.navigation.WalkingOptionsNavigation

fun WalkingOptionsNavigation.mapToWalkingOptions(): WalkingOptions = WalkingOptions
    .builder()
    .walkingSpeed(walkingSpeed)
    .walkwayBias(walkwayBias)
    .alleyBias(alleyBias)
    .build()
