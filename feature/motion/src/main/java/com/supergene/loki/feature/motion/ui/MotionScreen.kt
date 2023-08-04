package com.supergene.loki.feature.motion.ui

import android.graphics.Typeface
import androidx.compose.animation.core.spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.choidev.core.actions.presenter.ActionPresenter
import com.choidev.latesteffort.core.design.compose.DividerPaddingVertical
import com.choidev.latesteffort.core.design.compose.ScreenPaddingHorizontal
import com.choidev.latesteffort.core.util.motion.AccelerometerData
import com.choidev.latesteffort.core.util.motion.CachedAccelerometerData
import com.choidev.latesteffort.core.util.motion.SensorRate
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.ChartScrollSpec
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.legendItem
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.DefaultColors
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.scroll.AutoScrollCondition
import com.patrykandpatrick.vico.core.scroll.InitialScroll
import com.supergene.loki.feature.motion.MotionViewModel
import com.supergene.loki.feature.motion.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotionTestScreen(
    presenter: ActionPresenter,
    viewModel: MotionViewModel = hiltViewModel()
) {
    val accelerometerData by viewModel.accelerometerData.collectAsStateWithLifecycle()
    val currentRate by viewModel.currentRate.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.title_motion_test))
            })
        },
        modifier = Modifier
            .padding(horizontal = ScreenPaddingHorizontal())
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            AccelerometerUi(
                accelerometerData = accelerometerData,
                currentRate = currentRate,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun AccelerometerUi(
    accelerometerData: AccelerometerData,
    currentRate: SensorRate,
    viewModel: MotionViewModel,
    modifier: Modifier = Modifier
) {
    var openRateDialog by remember { mutableStateOf(false) }
    val shakeThreshold by viewModel.shakeThreshold.collectAsStateWithLifecycle()
    val fractionDigit by viewModel.fractionDigit.collectAsStateWithLifecycle()
    val cachedAccelerometerData by viewModel.cachedAccelerometerData.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Accelerometer",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            OutlinedButton(
                onClick = { viewModel.incrementFractionDigit() },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = fractionDigit.toString(),
                    textAlign = TextAlign.Center
                )
            }
            OutlinedButton(
                onClick = { openRateDialog = true },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = currentRate.name,
                    textAlign = TextAlign.Center
                )
            }
        }

        Text(text = "GVT X : ${accelerometerData.gravityX}")
        Text(text = "GVT Y : ${accelerometerData.gravityY}")
        Text(text = "GVT Z : ${accelerometerData.gravityZ}")
        Text(text = "ACC X : ${accelerometerData.accelerationX}")
        Text(text = "ACC Y : ${accelerometerData.accelerationY}")
        Text(text = "ACC Z : ${accelerometerData.accelerationZ}")

        Divider(modifier = Modifier.padding(vertical = DividerPaddingVertical()))

        Text(
            text = stringResource(id = R.string.title_shake_threshold),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.label_sensitive),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = stringResource(id = R.string.label_insensitive),
                style = MaterialTheme.typography.labelMedium
            )
        }
        Slider(
            value = shakeThreshold,
            onValueChange = { viewModel.shakeThreshold.value = it },
            valueRange = 0f..20f,
            steps = 10,
            modifier = Modifier.fillMaxWidth()
        )

        DynamicAccelerometerChart(cachedAccelerometerData)
    }

    if (openRateDialog) {
        RateDialog(
            currentRate = currentRate,
            onDismiss = { openRateDialog = false },
            onConfirmed = {
                viewModel.observeAccelerometer(rate = it)

                openRateDialog = false
            }
        )
    }
}

private const val COLOR_1_CODE = 0xffb983ff
private const val COLOR_2_CODE = 0xff91b1fd
private const val COLOR_3_CODE = 0xff8fdaff
private val color1 = Color(COLOR_1_CODE)
private val color2 = Color(COLOR_2_CODE)
private val color3 = Color(COLOR_3_CODE)
private val chartColors = listOf(color1, color2, color3)

@Composable
fun DynamicAccelerometerChart(data: CachedAccelerometerData) {
    val axisValueOverrider = AxisValuesOverrider.fixed(minY = -20f, maxY = 20f)

    val chartEntryModel = entryModelOf(
        entriesOf(*data.accelerationX.toNumberPairs()),
        entriesOf(*data.accelerationY.toNumberPairs()),
        entriesOf(*data.accelerationZ.toNumberPairs()),
    )

    val mySpec by remember {
        mutableStateOf(
            ChartScrollSpec(
                isScrollEnabled = true,
                initialScroll = InitialScroll.End,
                autoScrollCondition = AutoScrollCondition.OnModelSizeIncreased,
                autoScrollAnimationSpec = spring(),
            )
        )
    }

    ProvideChartStyle(rememberChartStyle(chartColors)) {
        val defaultLines = currentChartStyle.lineChart.lines
        Chart(
            chart = lineChart(
                remember(defaultLines) {
                    defaultLines.map { defaultLine -> defaultLine.copy(lineBackgroundShader = null) }
                },
                axisValuesOverrider = axisValueOverrider
            ),
            model = chartEntryModel,
            startAxis = startAxis(),
            bottomAxis = bottomAxis(),
            legend = rememberLegend(),
            chartScrollSpec = mySpec
        )
    }
}

private val legendItemLabelTextSize = 12.sp

private val legendStrings = listOf("ACC X", "ACC Y", "ACC Z")
private val legendItemIconSize = 8.dp
private val legendItemIconPaddingValue = 10.dp
private val legendItemSpacing = 4.dp
private val legendTopPaddingValue = 8.dp
private val legendPadding = dimensionsOf(top = legendTopPaddingValue)

@Composable
private fun rememberLegend() = verticalLegend(
    items = chartColors.mapIndexed { index, chartColor ->
        legendItem(
            icon = shapeComponent(Shapes.pillShape, chartColor),
            label = textComponent(
                color = currentChartStyle.axis.axisLabelColor,
                textSize = legendItemLabelTextSize,
                typeface = Typeface.MONOSPACE,
            ),
            labelText = legendStrings[index],
        )
    },
    iconSize = legendItemIconSize,
    iconPadding = legendItemIconPaddingValue,
    spacing = legendItemSpacing,
    padding = legendPadding,
)

@Composable
internal fun rememberChartStyle(
    columnChartColors: List<Color>,
    lineChartColors: List<Color>
): ChartStyle {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    return remember(columnChartColors, lineChartColors, isSystemInDarkTheme) {
        val defaultColors = if (isSystemInDarkTheme) DefaultColors.Dark else DefaultColors.Light
        ChartStyle(
            ChartStyle.Axis(
                axisLabelColor = Color(defaultColors.axisLabelColor),
                axisGuidelineColor = Color(defaultColors.axisGuidelineColor),
                axisLineColor = Color(defaultColors.axisLineColor),
            ),
            ChartStyle.ColumnChart(
                columnChartColors.map { columnChartColor ->
                    LineComponent(
                        columnChartColor.toArgb(),
                        DefaultDimens.COLUMN_WIDTH,
                        Shapes.roundedCornerShape(DefaultDimens.COLUMN_ROUNDNESS_PERCENT),
                    )
                },
            ),
            ChartStyle.LineChart(
                lineChartColors.map { lineChartColor ->
                    LineChart.LineSpec(
                        lineColor = lineChartColor.toArgb(),
                        lineBackgroundShader = DynamicShaders.fromBrush(
                            Brush.verticalGradient(
                                listOf(
                                    lineChartColor.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                    lineChartColor.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END),
                                ),
                            ),
                        ),
                    )
                },
            ),
            ChartStyle.Marker(),
            Color(defaultColors.elevationOverlayColor),
        )
    }
}

@Composable
internal fun rememberChartStyle(chartColors: List<Color>) =
    rememberChartStyle(columnChartColors = chartColors, lineChartColors = chartColors)

fun List<Float>.toNumberPairs(): Array<Pair<Number, Number>> {
    return this.mapIndexed { index, value -> index to value }.toTypedArray()
}
