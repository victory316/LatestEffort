package com.choidev.latesteffort.core.util.chart

import android.graphics.Typeface
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.choidev.latesteffort.core.util.motion.CachedAccelerometerData
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
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

private const val COLOR_1_CODE = 0xffb983ff
private const val COLOR_2_CODE = 0xff91b1fd
private const val COLOR_3_CODE = 0xff8fdaff
private val color1 = Color(COLOR_1_CODE)
private val color2 = Color(COLOR_2_CODE)
private val color3 = Color(COLOR_3_CODE)
private val chartColors = listOf(color1, color2, color3)

private val legendItemLabelTextSize = 12.sp

private val legendStrings = listOf("ACC X", "ACC Y", "ACC Z")
private val legendItemIconSize = 8.dp
private val legendItemIconPaddingValue = 10.dp
private val legendItemSpacing = 4.dp
private val legendTopPaddingValue = 8.dp
private val legendPadding = dimensionsOf(top = legendTopPaddingValue)

@Composable
fun DynamicAccelerometerChart(data: CachedAccelerometerData) {
    val axisValueOverrider = AxisValuesOverrider.fixed(minY = -20f, maxY = 20f)

    val chartEntryModel = entryModelOf(
        entriesOf(*data.accelerationX.toNumberPairs()),
        entriesOf(*data.accelerationY.toNumberPairs()),
        entriesOf(*data.accelerationZ.toNumberPairs()),
    )

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
            legend = rememberLegend()
        )
    }
}

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

private fun List<Float>.toNumberPairs(): Array<Pair<Number, Number>> {
    return this.mapIndexed { index, value -> index to value }.toTypedArray()
}
