package cz.quanti.spacex.core.presentation

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import quanti_space_x.composeapp.generated.resources.Res
import quanti_space_x.composeapp.generated.resources.unknown_date

@Composable
fun formatDate(localDate: LocalDate?): String {
    val format = LocalDate.Format {
        dayOfMonth()
        chars(".")
        monthNumber()
        chars(".")
        year()
    }
    return localDate?.let { format.format(it) } ?: stringResource(Res.string.unknown_date)
}