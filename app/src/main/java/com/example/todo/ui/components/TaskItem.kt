package com.example.todo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.edgeSwipeToDismiss
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.todo.core.PreviewPhone
import com.example.todo.core.onSizeMeasuredDp
import com.example.todo.domain.model.StorageStatus
import com.example.todo.theme.Typography
import com.example.todo.ui.model.TaskItemUiModel
import com.example.todo.ui.theme.Blue
import com.example.todo.ui.theme.TodoTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlin.random.Random

enum class CardSwipeState { Start, RightPinned, DismissedLeft }

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun TaskItem(
    state: TaskItemUiModel,
    onClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onAddedSubTaskClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    onTransferringSwipe: () -> Unit = {},
) {
    var itemHeight by remember { mutableStateOf(0.dp) }

    //стейт свайпа
    val swipeableState = rememberSwipeableState(CardSwipeState.Start)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenWidthPx = with(LocalDensity.current) { screenWidth.toPx() }
    val halfWidthScreen = with(LocalDensity.current) { screenWidthPx / 2f }

    val visibleState = remember { MutableTransitionState(true) }

    val anchors = mapOf(
        0f to CardSwipeState.Start,
        halfWidthScreen to CardSwipeState.RightPinned,
        -screenWidthPx to CardSwipeState.DismissedLeft
    )

    //стейт клика
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value
    val scale = animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        label = "scaleAnim"
    )

    LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue == CardSwipeState.DismissedLeft) {
            visibleState.targetState = false
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        exit = shrinkVertically(
            animationSpec = tween(300),
            shrinkTowards = Alignment.Top
        ) + fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .onSizeMeasuredDp { _, height ->
                    itemHeight = height - 16.dp
                },
            contentAlignment = Alignment.CenterStart
        ) {
            when {
                swipeableState.offset.value > 0 -> {
                    Row(
                        modifier = Modifier
                            .width(screenWidth / 2)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(onClick = onDeleteClick) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Удалить",
                                tint = Blue,
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        }
                        IconButton(onClick = onAddedSubTaskClick) {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "Добавить сабтаску",
                                tint = Blue,
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        }
                    }
                }

                swipeableState.offset.value < 0 -> {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(color = Blue, shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "В работу",
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 24.dp)
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        thresholds = { from, to ->
                            when {
                                (from == CardSwipeState.Start && to == CardSwipeState.RightPinned) ||
                                        (from == CardSwipeState.RightPinned && to == CardSwipeState.Start)
                                    -> FractionalThreshold(.5f)

                                else -> FractionalThreshold(.7f)
                            }
                        },
                        orientation = Orientation.Horizontal,
                        resistance = null
                    )
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) },
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .scale(scale.value)
                        .combinedClickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = onClick,
                            onLongClick = onLongClick
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PriorityStatusMark(
                        state = state.priority,
                        modifier = Modifier.height(itemHeight)
                    )
                    Column(
                        modifier = Modifier
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "срок: ${state.dateOfCreate}",
                                style = Typography.labelSmall
                            )
                        }
                        Text(
                            text = state.title,
                            modifier = Modifier,
                            style = Typography.titleLarge
                        )
                        Text(
                            text = state.description,
                            modifier = Modifier,
                            style = Typography.labelSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(visibleState.currentState, visibleState.isIdle) {
        if (!visibleState.currentState && visibleState.isIdle) {
            onTransferringSwipe()
        }
    }
}

@Composable
fun PriorityStatusMark(
    state: Priority,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (state) {
        Priority.Low -> Color.Green
        Priority.Medium -> Color.Yellow
        Priority.High -> Color.Red
    }

    Box(
        modifier = modifier
            .width(8.dp)
            .background(backgroundColor)
    )
}

@Composable
@PreviewPhone
private fun TaskItemPreview() {
    TodoTheme {
        val date = remember {
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }
        TaskItem(
            state = TaskItemUiModel(
                id = Random.nextLong(),
                title = "title",
                description = "description description description description description description ",
                dateOfCreate = date,
                priority = Priority.Low,
                storageStatus = StorageStatus.BACKLOG
            )
        )
    }
}