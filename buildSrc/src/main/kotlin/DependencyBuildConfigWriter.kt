fun List<Dependency>.toJavaField(): Pair<String, String> {
    val fieldType = "String[]"
    val fieldValue = toJavaFieldValue()
    return fieldType to fieldValue
}

private fun List<Dependency>.toJavaFieldValue() =
    joinToString(separator = ",") {
        """"${it.name} version ${it.version}""""
    }.let {
        "new String[]{$it}"
    }
