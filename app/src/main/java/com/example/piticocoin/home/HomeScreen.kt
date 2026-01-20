package com.example.piticocoin.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.piticocoin.R

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
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

            // 🔼 Card de cima
            Card(
                modifier = Modifier
                    .fillMaxWidth()
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
                    Text(text = "10.00", fontSize = 40.sp)
                }
            }

            // 🔽 Card de baixo
            Card(
                modifier = Modifier
                    .fillMaxWidth()
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
                    Text(text = "2.00", fontSize = 40.sp)
                    CurrencyRow(
                        modifier = Modifier.fillMaxWidth(),
                        currencyCode = "BRL",
                        currencyName = "Real",
                        onDropDownIconclicked = {}
                    )
                }
            }

            // 🔄 Botão flutuante
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