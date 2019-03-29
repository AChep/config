package com.artemchep.config.common

import com.artemchep.config.util.reduce

/**
 * @author Artem Chepurnoy
 */
open class MapStringConfig(
    map: MutableMap<String, Any> = HashMap()
) : MapConfig<String>(
    map = map,
    reduceKeys = ::reduce
)
