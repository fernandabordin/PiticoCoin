package com.example.piticocoin.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.piticocoin.R
import com.example.piticocoin.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {

    val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "C")

    LaunchedEffect(Unit) {
        viewModel.fetchRates()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "PitiCoin",
            fontFamily = FontFamily.Cursive,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (topCard, bottomCard, swapButton) = createRefs()
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.selectTop() }
                    .constrainAs(topCard) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    CurrencyRow(
                        modifier = Modifier.fillMaxWidth(),
                        currencyCode = "ICO",
                        currencyName = "PiticoCoin",
                        onDropDownIconclicked = {}
                    )
                    Text(
                        text = viewModel.eurValue.ifEmpty{ "" },
                        fontSize = 40.sp
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.selectBottom() }
                    .constrainAs(bottomCard) {
                        top.linkTo(topCard.bottom, margin = 40.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = viewModel.brlValue.ifEmpty { "" },
                        fontSize = 40.sp
                    )
                    CurrencyRow(
                        modifier = Modifier.fillMaxWidth(),
                        currencyCode = "BRL",
                        currencyName = "Real",
                        onDropDownIconclicked = {}
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.surface, CircleShape)
                    .constrainAs(swapButton) {
                        top.linkTo(topCard.bottom)
                        bottom.linkTo(bottomCard.top)
                        start.linkTo(parent.start, 20.dp)
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_sync),
                    contentDescription = "Swap Currency",
                    modifier = Modifier.size(24.dp)
                )
            }

        }
        LazyVerticalGrid(
            modifier = Modifier
                .padding(horizontal = 35.dp),
            columns = GridCells.Fixed(3)
        ) {
            items(keys.size) { indice ->
                KeyboardButton(
                    modifier = Modifier
                        .aspectRatio(1f),
                    key = keys[indice],
                    backgroundColor = if (keys[indice] == "C") MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.tertiary,
                    onClick = { key ->
                        viewModel.onKeyPressed(key)
                    }
                )
            }
        }

    }
}

@Composable
fun CurrencyRow(
    modifier: Modifier = Modifier,
    currencyCode: String,
    currencyName: String,
    onDropDownIconclicked: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = currencyCode, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = onDropDownIconclicked) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "jhdijhfdsjfvdjngv"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = currencyName, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun KeyboardButton(
    modifier: Modifier = Modifier,
    key: String,
    backgroundColor: Color,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(color = backgroundColor)
            .clickable { onClick(key) },
        contentAlignment = Alignment.Center
    ){
        Text(text = key, fontSize = 32.sp)
    }
}