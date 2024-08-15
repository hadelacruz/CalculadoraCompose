package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.example.calculadora.ui.theme.CalculadoraTheme
import androidx.compose.runtime.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                Calculator()
            }
        }
    }
}

@Composable
fun Calculator(
    modifier: Modifier = Modifier
){
    var title by remember { mutableStateOf("0") }
    var subtitle by remember { mutableStateOf("0") }


    Column (modifier = Modifier
        .fillMaxSize()) {
        DisplayCalc(title, subtitle)

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.Center
            , modifier = modifier.fillMaxWidth()
        ) {
            CalculatorButton(onClickedValue = { title = title + "(" }, text = "(")
            CalculatorButton(onClickedValue = { title = title + ")" }, text = ")")
            CalculatorButton(onClickedValue = { title = title + "^" }, text = "^")
            CalculatorButton(onClickedValue = { title = title + "/" }, text = "/")
        }

        Row (
            verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.Center
            , modifier = modifier.fillMaxWidth()
        ) {
            CalculatorButton(onClickedValue = { title = title + "7" }, text = "7")
            CalculatorButton(onClickedValue = { title = title + "8" }, text = "8")
            CalculatorButton(onClickedValue = { title = title + "9" }, text = "9")
            CalculatorButton(onClickedValue = { title = title + "x" }, text = "x")
        }


    }


}

@Composable
fun CalculatorButton(
    onClickedValue: () -> Unit
    , text: String = ""
    , modifier: Modifier = Modifier
){
    Button(
        onClick = onClickedValue
        , modifier = modifier.padding(end = 10.dp, start = 10.dp)
    ) {
        Text(text = text)
    }

}

@Composable
fun DisplayCalc(title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RightAlignedCardPreview() {
    Calculator()
}

