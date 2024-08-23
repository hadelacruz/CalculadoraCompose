package com.example.calculadora
/*
    Autor: Humberto Alexander de la Cruz - 23735
    Fecha: 15/08/2024
    Descripción: Calculora Compose, el cual permite calcular expresiones en formato infix
 */

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import java.util.Stack
import kotlin.math.pow


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

//Función principal
@Composable
fun Calculator(
    modifier: Modifier = Modifier
){

    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("0") }

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), // Padding adicional para dar espacio alrededor
        verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally ) {
        DisplayCalc(title, subtitle)

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.Center
            , modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)

        ) {
            CalculatorButton(onClickedValue = { title = title + "(" }, text = "(")
            CalculatorButton(onClickedValue = { title = title + ")" }, text = ")")
            CalculatorButton(onClickedValue = { title = title + "^" }, text = "^")
            CalculatorButton(onClickedValue = { title = title + "/" }, text = "/")
        }

        Row (
            verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.Center
            , modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            CalculatorButton(onClickedValue = { title = title + "7" }, text = "7", containerColor = Color.Unspecified)
            CalculatorButton(onClickedValue = { title = title + "8" }, text = "8", containerColor = Color.Unspecified)
            CalculatorButton(onClickedValue = { title = title + "9" }, text = "9", containerColor = Color.Unspecified)
            CalculatorButton(onClickedValue = { title = title + "*" }, text = "*")
        }

        Row (
            verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.Center
            , modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            CalculatorButton(onClickedValue = { title = title + "4" }, text = "4", containerColor = Color.Unspecified)
            CalculatorButton(onClickedValue = { title = title + "5" }, text = "5", containerColor = Color.Unspecified)
            CalculatorButton(onClickedValue = { title = title + "6" }, text = "6", containerColor = Color.Unspecified)
            CalculatorButton(onClickedValue = { title = title + "-" }, text = "-")
        }

        Row (
            verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.Center
            , modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            CalculatorButton(onClickedValue = { title = title + "1" }, text = "1", containerColor = Color.Unspecified)
            CalculatorButton(onClickedValue = { title = title + "2" }, text = "2", containerColor = Color.Unspecified)
            CalculatorButton(onClickedValue = { title = title + "3" }, text = "3", containerColor = Color.Unspecified)
            CalculatorButton(onClickedValue = { title = title + "+" }, text = "+")
        }

        Row (
            verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.Center
            , modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            CalculatorButton(onClickedValue = { title = title + "0" }, text = "0", containerColor = Color.Unspecified)
            CalculatorButton(onClickedValue = { title = ""; subtitle="0"}, text = "C" , containerColor = Color(0xFF515A5A))
            CalculatorButton(onClickedValue = { subtitle = evaluarExpresion(title) }, text = "=")
        }


    }


}

//Método para configurar los botones
@Composable
fun CalculatorButton(
    onClickedValue: () -> Unit
    , text: String = ""
    , modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFF6C3483),
){
    Button(

        onClick = onClickedValue,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,

        )
        , modifier = modifier.padding(end = 10.dp, start = 10.dp).size(70.dp)

    ) {
        Text(text = text)
    }

}
//Método para modificar el Text del resultado
@Composable
fun DisplayCalc(title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .padding(vertical = 15.dp).padding(top= 30.dp)

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

//-----------------------------LÓGICA DE OPERACIONES-----------------------------

// Método que evalua la expresión en title, y devuelve el resultado de la operacióm
fun evaluarExpresion(title: String): String {
    var expresion = title.toString();
    try {
        validarExpresion(expresion)
        var result = evaluate(expresion)
        return result.toString()

    } catch (e: IllegalArgumentException) {
        val error = "Syntax Error"
        return error;
    }
}


// Operaciones de la calculadora
private fun precedence(c: Char): Int {
    return when (c) {
        '+', '-' -> 1
        '*', '/' -> 2
        '^' -> 3
        else -> -1
    }
}

// Método que convierte la expresion de title de infix a Postfix
private fun infixToPostfix(expresion: String): String {
    val result = StringBuilder()
    val stack = Stack<Char>()

    val cleanedExpression = expresion.replace("\\s+".toRegex(), "")

    var i = 0
    while (i < cleanedExpression.length) {
        val c = cleanedExpression[i]

        if (c.isLetterOrDigit()) {
            while (i < cleanedExpression.length && cleanedExpression[i].isLetterOrDigit()) {
                result.append(cleanedExpression[i])
                i++
            }
            result.append(' ')
            i--
        } else if (c == '(') {
            stack.push(c)
        } else if (c == ')') {
            while (!stack.isEmpty() && stack.peek() != '(')
                result.append(stack.pop()).append(' ')
            if (!stack.isEmpty() && stack.peek() != '(') {
                throw IllegalArgumentException("Syntax Error") // expresión inválida
            } else {
                stack.pop()
            }
        } else {
            while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek()))
                result.append(stack.pop()).append(' ')
            stack.push(c)
        }
        i++
    }

    while (!stack.isEmpty()) {
        if (stack.peek() == '(')
            throw IllegalArgumentException("Syntax Error") // expresión inválida
        result.append(stack.pop()).append(' ')
    }

    return result.toString().trim()
}

private fun stringToList(expression: String): List<String> {
    return expression.split(" ").filter { it.isNotEmpty() }
}

private fun evaluatePostfix(postfix: List<String>): Double {
    val stack = Stack<Double>()

    for (token in postfix) {
        when {
            token.toDoubleOrNull() != null -> stack.push(token.toDouble())
            else -> {
                val b = stack.pop()
                val a = stack.pop()
                stack.push(when (token) {
                    "+" -> a + b
                    "-" -> a - b
                    "*" -> a * b
                    "/" -> a / b
                    "^" -> a.pow(b)
                    else -> throw IllegalArgumentException("Unknown operator: $token")
                })
            }
        }
    }
    return stack.pop()
}

private fun evaluate(expresion: String): Double {
    val postfixExpression = infixToPostfix(expresion)
    return evaluatePostfix(stringToList(postfixExpression))
}

//Método para validar la expresión de title antes de convertirla a postfix y evaluarla
private fun validarExpresion(expresion: String) {
    val cleanedExpression = expresion.replace("\\s+".toRegex(), "")
    val operators = setOf('+', '-', '*', '/', '^')
    var openParentheses = 0
    var lastChar: Char? = null

    for (char in cleanedExpression) {
        if (char == '(') {
            openParentheses++
        } else if (char == ')') {
            if (openParentheses == 0) {
                throw IllegalArgumentException("Syntax Error")
            }
            openParentheses--
        }

        if (char in operators) {
            if (lastChar != null && lastChar in operators) {
                throw IllegalArgumentException("Syntax Error")
            }
        }

        lastChar = char
    }

    if (openParentheses != 0) {
        throw IllegalArgumentException("Syntax Error")
    }

    if (lastChar in operators) {
        throw IllegalArgumentException("Syntax Error")
    }
}

