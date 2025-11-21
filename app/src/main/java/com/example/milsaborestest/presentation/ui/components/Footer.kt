package com.example.milsaborestest.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.milsaborestest.util.Constants.Design
import java.util.Calendar

@Composable
fun AppFooter(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = Design.TOPBAR_ELEVATION
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Design.PADDING_STANDARD),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Design.PADDING_MEDIUM)
        ) {
            // Logo o nombre de la app
            Text(
                text = "Mil Sabores",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            // Links de redes sociales
            Row(
                horizontalArrangement = Arrangement.spacedBy(Design.PADDING_STANDARD),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialMediaIcon(
                    icon = Icons.Default.Facebook,
                    onClick = { /* TODO: Abrir Facebook */ },
                    description = "Facebook"
                )
                
                SocialMediaIcon(
                    icon = Icons.Default.Place,
                    onClick = { /* TODO: Abrir Instagram */ },
                    description = "Instagram"
                )
                
                SocialMediaIcon(
                    icon = Icons.Default.Call,
                    onClick = { /* TODO: Abrir WhatsApp */ },
                    description = "WhatsApp"
                )
            }
            
            // Copyright
            Text(
                text = "© ${Calendar.getInstance().get(Calendar.YEAR)} Mil Sabores. Todos los derechos reservados.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SocialMediaIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    description: String
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(40.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

