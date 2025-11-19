@file:OptIn(org.jetbrains.compose.resources.InternalResourceApi::class)

package quanti_space_x.composeapp.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi

private object CommonMainDrawable0 {
  public val burn: DrawableResource by 
      lazy { init_burn() }

  public val compose_multiplatform: DrawableResource by 
      lazy { init_compose_multiplatform() }

  public val engine: DrawableResource by 
      lazy { init_engine() }

  public val fuel: DrawableResource by 
      lazy { init_fuel() }

  public val reusable: DrawableResource by 
      lazy { init_reusable() }

  public val rocket: DrawableResource by 
      lazy { init_rocket() }
}

@InternalResourceApi
internal fun _collectCommonMainDrawable0Resources(map: MutableMap<String, DrawableResource>) {
  map.put("burn", CommonMainDrawable0.burn)
  map.put("compose_multiplatform", CommonMainDrawable0.compose_multiplatform)
  map.put("engine", CommonMainDrawable0.engine)
  map.put("fuel", CommonMainDrawable0.fuel)
  map.put("reusable", CommonMainDrawable0.reusable)
  map.put("rocket", CommonMainDrawable0.rocket)
}

internal val Res.drawable.burn: DrawableResource
  get() = CommonMainDrawable0.burn

private fun init_burn(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:burn",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/quanti_space_x.composeapp.generated.resources/drawable/burn.xml", -1, -1),
    )
)

internal val Res.drawable.compose_multiplatform: DrawableResource
  get() = CommonMainDrawable0.compose_multiplatform

private fun init_compose_multiplatform(): DrawableResource =
    org.jetbrains.compose.resources.DrawableResource(
  "drawable:compose_multiplatform",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/quanti_space_x.composeapp.generated.resources/drawable/compose-multiplatform.xml", -1, -1),
    )
)

internal val Res.drawable.engine: DrawableResource
  get() = CommonMainDrawable0.engine

private fun init_engine(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:engine",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/quanti_space_x.composeapp.generated.resources/drawable/engine.xml", -1, -1),
    )
)

internal val Res.drawable.fuel: DrawableResource
  get() = CommonMainDrawable0.fuel

private fun init_fuel(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:fuel",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/quanti_space_x.composeapp.generated.resources/drawable/fuel.xml", -1, -1),
    )
)

internal val Res.drawable.reusable: DrawableResource
  get() = CommonMainDrawable0.reusable

private fun init_reusable(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:reusable",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/quanti_space_x.composeapp.generated.resources/drawable/reusable.xml", -1, -1),
    )
)

internal val Res.drawable.rocket: DrawableResource
  get() = CommonMainDrawable0.rocket

private fun init_rocket(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:rocket",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(),
    "composeResources/quanti_space_x.composeapp.generated.resources/drawable/rocket.xml", -1, -1),
    )
)
