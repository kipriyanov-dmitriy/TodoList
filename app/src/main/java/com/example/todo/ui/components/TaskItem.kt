package com.example.todo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.todo.core.PreviewPhone
import com.example.todo.core.onSizeMeasuredDp
import com.example.todo.domain.model.StorageStatus
import com.example.todo.theme.Typography
import com.example.todo.ui.model.TaskItemUiModel
import com.example.todo.ui.theme.TodoTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlin.random.Random

enum class CardSwipeState { Start, SwipingToRight, SwipingToLeft }

sealed class SwipeConfig {
    object Disabled : SwipeConfig()

    data class Enabled(
        val text: String,
        val backgroundColor: Color,
        val onSwiped: () -> Unit
    ) : SwipeConfig()
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun TaskItem(
    state: TaskItemUiModel,
    onClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onAddedSubTaskClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    onVisibilityValueChanged: () -> Boolean = { true },
    swipeToRight: SwipeConfig = SwipeConfig.Disabled,
    swipeToLeft: SwipeConfig = SwipeConfig.Disabled,
) {
    var itemHeight by remember { mutableStateOf(0.dp) }

    //стейт свайпа
    val swipeableState = rememberSwipeableState(CardSwipeState.Start)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenWidthPx = with(LocalDensity.current) { screenWidth.toPx() }

    val visibleState = remember { MutableTransitionState(true) }

    val anchors = buildMap {
        put(0f, CardSwipeState.Start)
        if (swipeToRight is SwipeConfig.Enabled) put(screenWidthPx, CardSwipeState.SwipingToRight)
        if (swipeToLeft is SwipeConfig.Enabled) put(-screenWidthPx, CardSwipeState.SwipingToLeft)
    }

    LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue == CardSwipeState.SwipingToRight
            || swipeableState.currentValue == CardSwipeState.SwipingToLeft
        ) {
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
                swipeableState.offset.value > 0 && swipeToRight is SwipeConfig.Enabled -> {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                color = swipeToRight.backgroundColor,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = swipeToRight.text,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 24.dp)
                        )
                    }
                }

                swipeableState.offset.value < 0 && swipeToLeft is SwipeConfig.Enabled -> {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                color = swipeToLeft.backgroundColor,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = swipeToLeft.text,
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
                    .then(
                        if (anchors.size > 1) {
                            Modifier.swipeable(
                                state = swipeableState,
                                anchors = anchors,
                                thresholds = { from, to ->
                                    when {
                                        (from == CardSwipeState.Start && to == CardSwipeState.SwipingToRight) ||
                                                (from == CardSwipeState.SwipingToRight && to == CardSwipeState.Start)
                                            -> FractionalThreshold(.5f)

                                        else -> FractionalThreshold(.7f)
                                    }
                                },
                                orientation = Orientation.Horizontal,
                                resistance = null
                            )
                        } else {
                            Modifier
                        }
                    )
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) },
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .combinedClickable(
                            interactionSource = remember { MutableInteractionSource() },
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
                                text = "срок: ${state.deadlineDate}",
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
        val isVisible = !visibleState.currentState && visibleState.isIdle
        if (isVisible && swipeableState.currentValue == CardSwipeState.SwipingToLeft) {
            (swipeToLeft as SwipeConfig.Enabled).onSwiped()
        } else if (isVisible && swipeableState.currentValue == CardSwipeState.SwipingToRight) {
            (swipeToRight as SwipeConfig.Enabled).onSwiped()
        }
    }

    LaunchedEffect(state.storageStatus) {
        if (swipeableState.currentValue == CardSwipeState.SwipingToRight
            || swipeableState.currentValue == CardSwipeState.SwipingToLeft
        ) {
            visibleState.targetState = onVisibilityValueChanged()
            swipeableState.snapTo(CardSwipeState.Start)
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
                deadlineDate = date,
                priority = Priority.Low,
                storageStatus = StorageStatus.BACKLOG
            ),
        )
    }
}