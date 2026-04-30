package com.example.ascentlister.route.presentation.route_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ascentlister.composeapp.generated.resources.Res
import ascentlister.composeapp.generated.resources.arrow_forward_24px
import com.example.ascentlister.route.domain.Route
import com.example.ascentlister.core.presentation.LightBlue
import org.jetbrains.compose.resources.painterResource

@Composable
fun RouteListItem(
    route: Route,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        color = LightBlue.copy(alpha = 0.2f),
        modifier = modifier
            .clickable(onClick = onClick)
    ){
        Row(
           modifier = Modifier
               .padding(16.dp)
               .fillMaxWidth()
               .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = route.routeName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = route.grade,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = route.location.locationName + ", " + route.location.locationAreaName,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                painter = painterResource(Res.drawable.arrow_forward_24px),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
