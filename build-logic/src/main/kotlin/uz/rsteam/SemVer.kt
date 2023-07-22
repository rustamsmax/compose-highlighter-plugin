package uz.rsteam

data class SemVer(
    val major: Int,
    val minor: Int,
    val patch: Int,
) : Comparable<SemVer> {

    override fun compareTo(other: SemVer): Int {
        return major.compareTo(other.major).takeIf { it != 0 }
            ?: minor.compareTo(other.minor).takeIf { it != 0 }
            ?: patch.compareTo(other.patch)
    }

    override fun toString() = "$major.$minor.$patch"
}
