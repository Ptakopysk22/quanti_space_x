@file:OptIn(InternalResourceApi::class)

package quanti_space_x.composeapp.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem

private const val MD: String = "composeResources/quanti_space_x.composeapp.generated.resources/"

internal val Res.drawable.burn: DrawableResource by lazy {
      DrawableResource("drawable:burn", setOf(
        ResourceItem(setOf(), "${MD}drawable/burn.xml", -1, -1),
      ))
    }

internal val Res.drawable.compose_multiplatform: DrawableResource by lazy {
      DrawableResource("drawable:compose_multiplatform", setOf(
        ResourceItem(setOf(), "${MD}drawable/compose-multiplatform.xml", -1, -1),
      ))
    }

internal val Res.drawable.engine: DrawableResource by lazy {
      DrawableResource("drawable:engine", setOf(
        ResourceItem(setOf(), "${MD}drawable/engine.xml", -1, -1),
      ))
    }

internal val Res.drawable.fuel: DrawableResource by lazy {
      DrawableResource("drawable:fuel", setOf(
        ResourceItem(setOf(), "${MD}drawable/fuel.xml", -1, -1),
      ))
    }

internal val Res.drawable.reusable: DrawableResource by lazy {
      DrawableResource("drawable:reusable", setOf(
        ResourceItem(setOf(), "${MD}drawable/reusable.xml", -1, -1),
      ))
    }

internal val Res.drawable.rocket: DrawableResource by lazy {
      DrawableResource("drawable:rocket", setOf(
        ResourceItem(setOf(), "${MD}drawable/rocket.xml", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainDrawable0Resources(map: MutableMap<String, DrawableResource>) {
  map.put("burn", Res.drawable.burn)
  map.put("compose_multiplatform", Res.drawable.compose_multiplatform)
  map.put("engine", Res.drawable.engine)
  map.put("fuel", Res.drawable.fuel)
  map.put("reusable", Res.drawable.reusable)
  map.put("rocket", Res.drawable.rocket)
}
