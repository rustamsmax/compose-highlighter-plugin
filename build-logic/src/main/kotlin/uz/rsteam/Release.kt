package uz.rsteam

import java.time.LocalDate

data class Release(
  val version: SemVer,
  val date: LocalDate,
  val notes: List<String>,
)
