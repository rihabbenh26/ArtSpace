package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ArtSpace()
                }
            }
        }
    }
}

data class Artwork(
    @DrawableRes val imageResourceId: Int,
    val title: String,
    val artist: String,
    val year: String
)

@Composable
fun ArtSpace() {
    val artworks = listOf(
        Artwork(R.drawable.photo1, "Paris Street; Rainy Day", "Gustave Caillebotte", "1877"),
        Artwork(R.drawable.photo2, "The Basket of Apples", "Paul Cezanne", "1893"),
        Artwork(R.drawable.photo3, "Portrait of Pablo Picasso", "Juan Gris", "January–February 1912")
    )

    val currentArtworkIndex = remember { mutableStateOf(0) }
    val currentArtwork = artworks[currentArtworkIndex.value]

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f)) // Push content down

        ArtworkWall(currentArtwork)

        ArtworkDescriptor(currentArtwork)

        Spacer(modifier = Modifier.weight(1f)) // Balance layout

        DisplayController(
            onPreviousClick = {
                currentArtworkIndex.value =
                    if (currentArtworkIndex.value > 0) currentArtworkIndex.value - 1 else artworks.size - 1
            },
            onNextClick = {
                currentArtworkIndex.value =
                    if (currentArtworkIndex.value < artworks.size - 1) currentArtworkIndex.value + 1 else 0
            }
        )
    }
}

@Composable
fun ArtworkWall(artwork: Artwork) {
    Surface(
        modifier = Modifier
            .shadow(8.dp)
            .border(
                width = 2.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp),
        color = Color.White
    ) {
        Image(
            painter = painterResource(id = artwork.imageResourceId),
            contentDescription = artwork.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun ArtworkDescriptor(artwork: Artwork) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = artwork.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "${artwork.artist} ${artwork.year}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DisplayController(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onPreviousClick) {
            Text(text = "Previous")
        }

        Button(onClick = onNextClick) {
            Text(text = "Next")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpace()
    }
}
