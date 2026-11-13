package com.drdisagree.teledrive.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FolderAppearanceTest {
    @Test fun storesAllColorsAndUnicodeWithoutChangingOtherEntries() {
        var entries = emptySet<String>()
        repeat(12) { color ->
            entries = FolderAppearance.put(entries, 123L, "id-$color", FolderAppearance(color, "👩🏽‍💻|🌈"))
        }
        repeat(12) { color ->
            assertEquals(FolderAppearance(color, "👩🏽‍💻|🌈"), FolderAppearance.read(entries, 123L, "id-$color"))
        }
        assertNull(FolderAppearance.read(entries, 124L, "id-0"))
        assertNull(FolderAppearance.read(entries, null, "id-0"))
        assertEquals(entries, FolderAppearance.put(entries, null, "id-0", FolderAppearance(0, "")))
        val removed = FolderAppearance.put(entries, 123L, "id-0", null)
        assertNull(FolderAppearance.read(removed, 123L, "id-0"))
        assertEquals(11, removed.size)
    }
    @Test fun invalidInputsDoNotEraseExistingData() {
        val old = FolderAppearance.put(emptySet(), 1L, "folder", FolderAppearance(1, "📁"))
        assertEquals(old, FolderAppearance.put(old, 1L, "folder", FolderAppearance(12, "")))
        assertEquals(old, FolderAppearance.put(old, 1L, "folder", FolderAppearance(0, "x".repeat(65))))
        assertNull(FolderAppearance.read(setOf("1:folder|99|x"), 1L, "folder"))
        assertTrue(FolderAppearance.put(emptySet(), 1L, "bad|id", FolderAppearance(0, "")).isEmpty())
    }
}
