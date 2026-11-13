package com.drdisagree.teledrive.domain.model

/** Device-local decoration only. Never written to the Telegram folder tree. */
data class FolderAppearance(val color: Int, val emoji: String) {
    companion object {
        const val COLOR_COUNT = 12

        fun read(entries: Set<String>, chatId: Long?, folderId: String): FolderAppearance? {
            if (chatId == null) return null
            val prefix = "$chatId:$folderId|"
            return entries.asSequence().filter { it.startsWith(prefix) }.mapNotNull { entry ->
                val parts = entry.removePrefix(prefix).split('|', limit = 2)
                val color = parts.firstOrNull()?.toIntOrNull() ?: return@mapNotNull null
                if (parts.size != 2 || color !in 0 until COLOR_COUNT || parts[1].length > 64) null
                else FolderAppearance(color, parts[1])
            }.firstOrNull()
        }

        fun put(entries: Set<String>, chatId: Long?, folderId: String, appearance: FolderAppearance?): Set<String> {
            if (chatId == null || folderId.isEmpty() || '|' in folderId) return entries
            val prefix = "$chatId:$folderId|"
            val rest = entries.filterNot { it.startsWith(prefix) }.toSet()
            if (appearance == null) return rest
            if (appearance.color !in 0 until COLOR_COUNT || appearance.emoji.length > 64) return entries
            return rest + "$prefix${appearance.color}|${appearance.emoji}"
        }
    }
}
