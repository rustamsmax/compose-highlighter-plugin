package uz.rsteam.highlighting

import uz.rsteam.ChangeNotes
import uz.rsteam.Release
import uz.rsteam.SemVer
import java.time.LocalDate
import java.time.Month

val pluginChangeNotes = ChangeNotes(
    releases = listOf(
        Release(
            version = SemVer(1, 0, 6),
            date = LocalDate.of(2023, Month.DECEMBER, 20),
            notes = listOf("Color editor on everywhere (variables, references)")
        ),
        Release(
            version = SemVer(1, 0, 6),
            date = LocalDate.of(2023, Month.DECEMBER, 20),
            notes = listOf("Added Color gutter")
        ),
        Release(
            version = SemVer(1, 0, 5),
            date = LocalDate.of(2023, Month.DECEMBER, 20),
            notes = listOf("2023.3 Compatibility")
        ),
        Release(
            version = SemVer(1, 0, 3),
            date = LocalDate.of(2023, Month.JULY, 24),
            notes = listOf("2023.2 Compatibility")
        ),
        Release(
            version = SemVer(1, 0, 2),
            date = LocalDate.of(2023, Month.JULY, 24),
            notes = listOf("Author website")
        ),
    )
)
