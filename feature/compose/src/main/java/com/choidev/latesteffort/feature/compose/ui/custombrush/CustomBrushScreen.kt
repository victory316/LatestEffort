package com.choidev.latesteffort.feature.compose.ui.custombrush

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.supergene.loki.feature.motion.R
import org.intellij.lang.annotations.Language

@Language("AGSL")
val PERLIN_NOISE = """
    uniform float2 resolution;
    uniform float time;
    uniform shader contents;
    
    // Perlin Noise function in AGSL
    float hash(vec2 p) {
        return fract(sin(dot(p, vec2(127.1, 311.7))) * 43758.5453123);
    }

    float noise(vec2 p) {
        vec2 i = floor(p);
        vec2 f = fract(p);

        vec2 u = f * f * (3.0 - 2.0 * f);

        return mix(
            mix(hash(i + vec2(0.0, 0.0)), hash(i + vec2(1.0, 0.0)), u.x),
            mix(hash(i + vec2(0.0, 1.0)), hash(i + vec2(1.0, 1.0)), u.x),
            u.y
        );
    }

    half4 main(in vec2 fragCoord) {
        vec2 uv = fragCoord.xy / resolution.xy;

        // Replace snoise with Perlin noise
        float noiseValue = noise(uv * 6.0 + time * 1.5);

        noiseValue *= exp(-length(abs(uv * 1.5)));
        vec2 offset1 = vec2(noiseValue * 0.02);
        vec2 offset2 = vec2(0.02) / resolution.xy;
        uv += offset1 - offset2;

        return contents.eval(uv * resolution.xy);
    }
""".trimIndent()

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CustomBrushScreen(modifier: Modifier = Modifier) {
    val time by produceState(initialValue = 0f) {
        while (true) {
            withInfiniteAnimationFrameMillis {
                value = it / 1000f
            }
        }
    }

    val shader = RuntimeShader(PERLIN_NOISE)
    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                shader.setFloatUniform(
                    "resolution",
                    size.width.toFloat(),
                    size.height.toFloat()
                )
            }
            .graphicsLayer {
                shader.setFloatUniform("time", time)
                renderEffect = RenderEffect
                    .createRuntimeShaderEffect(
                        shader,
                        "contents"
                    )
                    .asComposeRenderEffect()
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_sample), contentDescription = null,
            modifier = Modifier.matchParentSize()
        )
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
fun ShaderBrushExample() {
    CustomBrushScreen()
}