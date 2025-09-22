package com.cs407.cardfolio

import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.tooling.preview.Preview
import com.cs407.cardfolio.ui.theme.CardfolioTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cs407.cardfolio.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            CardfolioTheme {
                val gradientTopColor = AppTheme.customColors.gradientTop
                val gradientBottomColor = AppTheme.customColors.gradientBottom
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    gradientTopColor,
                                    gradientBottomColor
                                )
                            )
                        ),
                    color = Color.Transparent
                ) {


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                        )
                        Cardfolio()
                    }
                }
            }
        }
    }
}
@Composable
fun Cardfolio() {

    var name by remember { mutableStateOf(value = "") }
    var hobby by remember { mutableStateOf(value ="") }
    var age by remember { mutableStateOf(value ="") }
    var isEditing by remember { mutableStateOf(true) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(all=16.dp)
    ) {
        ElevatedCard (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.extraLarge,
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(  containerColor = MaterialTheme.colorScheme.surface)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.thefigure),
                    contentDescription = "pfp",
                    modifier = Modifier
                        .size(size = 84.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            CircleShape
                        )
                )
                Spacer(Modifier.width(width = 16.dp))

                Column(Modifier.weight(weight = 1f)) {
                    Text(
                        text = if (name.isBlank()) stringResource(id = R.string.name) else name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = if (hobby.isBlank()) stringResource(id = R.string.hobby) else hobby,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp ),
                    verticalArrangement = Arrangement.spacedBy(space = 12.dp)
                ){
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = stringResource(id = R.string.card_name_label)) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        enabled = isEditing,
                        modifier = Modifier.fillMaxWidth()

                    )
                    OutlinedTextField(
                        value = hobby,
                        enabled = isEditing,
                        onValueChange = { hobby = it },
                        label = { Text(text=stringResource(id = R.string.card_hobby_label)) },
                        leadingIcon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(

                        value = age,
                        onValueChange = { input ->
                            if (input.all { it.isDigit() }) {
                                age = input
                            }
                        },
                        label = { Text(text=stringResource(id = R.string.card_age_label)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        enabled = isEditing,
                        supportingText = {if (isEditing) Text(text=stringResource(id = R.string.age_warning))},
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },

                        modifier = Modifier.fillMaxWidth(),


                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)

                    ) {
                        OutlinedButton( //edit
                            onClick = { isEditing = true },
                            enabled = !isEditing,


                        ) {
                            Text(text = stringResource(id = R.string.edit_button))
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Show"
                            )
                        }

                        OutlinedButton( //show
                            onClick = {
                                if (name.isNotBlank() && hobby.isNotBlank() && age.isNotBlank()) {
                                    isEditing = false
                                }
                            },
                            enabled = isEditing,


                        ) {
                            Text(text = stringResource(id = R.string.show_button));
                            Text(text = stringResource(id = R.string.edit_button))
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Show"
                            )

                        }
                    }
                    AssistChip(
                        onClick = { isEditing = !isEditing },
                        label = {
                            Text(
                                text = if (isEditing)
                                    stringResource(id = R.string.edit)
                                else
                                    stringResource(id = R.string.lock)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = if (isEditing) Icons.Default.Edit else Icons.Default.Lock,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }

@Preview(showBackground = true)
@Composable
fun CardfolioPreview() {
    CardfolioTheme {
        Cardfolio()
    }
}
