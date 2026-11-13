package com.drdisagree.teledrive.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drdisagree.teledrive.domain.model.FolderAppearance
import com.drdisagree.teledrive.resources.Res
import com.drdisagree.teledrive.resources.common_cancel
import com.drdisagree.teledrive.resources.common_save
import com.drdisagree.teledrive.resources.folder_appearance_title
import com.drdisagree.teledrive.resources.folder_appearance_local
import com.drdisagree.teledrive.resources.folder_appearance_emoji
import com.drdisagree.teledrive.resources.folder_appearance_reset
import com.drdisagree.teledrive.resources.folder_appearance_color
import org.jetbrains.compose.resources.stringResource

val folderColors = listOf(
    Color(0xFFE53935), Color(0xFFD81B60), Color(0xFF8E24AA), Color(0xFF5E35B1),
    Color(0xFF3949AB), Color(0xFF1E88E5), Color(0xFF00ACC1), Color(0xFF00897B),
    Color(0xFF43A047), Color(0xFFF9A825), Color(0xFFFB8C00), Color(0xFF6D4C41)
)

@Composable
fun FolderAppearanceIcon(appearance: FolderAppearance?, size: androidx.compose.ui.unit.Dp) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(size)) {
        Icon(Icons.Filled.Folder, contentDescription = null, modifier = Modifier.size(size),
            tint = appearance?.let { folderColors.getOrNull(it.color) } ?: MaterialTheme.colorScheme.primary)
        if (!appearance?.emoji.isNullOrEmpty()) {
            Text(appearance!!.emoji, fontSize = (size.value * 0.36f).sp, maxLines = 1)
        }
    }
}

@Composable
fun FolderAppearanceDialog(initial: FolderAppearance?, onSave: (FolderAppearance?) -> Unit, onDismiss: () -> Unit) {
    var color by remember(initial) { mutableIntStateOf(initial?.color ?: 0) }
    var emoji by remember(initial) { mutableStateOf(initial?.emoji ?: "") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(Res.string.folder_appearance_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(stringResource(Res.string.folder_appearance_local), style = MaterialTheme.typography.bodySmall)
                repeat(2) { row ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        repeat(6) { column ->
                            val index = row * 6 + column
                            val label = stringResource(Res.string.folder_appearance_color, index + 1)
                            Box(Modifier.size(32.dp).clip(CircleShape).background(folderColors[index])
                                .semantics { contentDescription = label }.clickable { color = index },
                                contentAlignment = Alignment.Center) {
                                if (color == index) Icon(Icons.Filled.Check, null, tint = Color.White)
                            }
                        }
                    }
                }
                OutlinedTextField(value = emoji, onValueChange = { if (it.length <= 64) emoji = it },
                    label = { Text(stringResource(Res.string.folder_appearance_emoji)) }, singleLine = true)
                TextButton(onClick = { onSave(null) }) { Text(stringResource(Res.string.folder_appearance_reset)) }
            }
        },
        confirmButton = { TextButton(onClick = { onSave(FolderAppearance(color, emoji)) }) { Text(stringResource(Res.string.common_save)) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(Res.string.common_cancel)) } }
    )
}
