package com.example.calculatortest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculatortest.ui.theme.CalculatorTestTheme
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTestTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    EntryTextInput()
                    ButtonsLayout()
                }
            }
        }
    }
}

@Composable
fun EntryTextInput(calculatorViewModel: CalculatorViewModel = viewModel()) {
    val text = calculatorViewModel.entryLive.observeAsState("")
    val backSpaceVisible = rememberSaveable{mutableStateOf(false)}
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)) {
            TextField(value = text.value, onValueChange = {}, textStyle = TextStyle(fontSize = 50.sp, textAlign = TextAlign.Center), maxLines = 1, readOnly = true, enabled = true, modifier = Modifier
                .weight(2f)
                .fillMaxHeight())
            backSpaceVisible.value = text.value.isNotEmpty()
            if (backSpaceVisible.value) {
                Backspace()
            }
        }
    }
}

@Composable
fun ButtonsLayout() {
    val layoutSet = ConstraintSet {
        val lineOne = createRefFor("lineOne")
        val lineTwo = createRefFor("lineTwo")
        val lineThree = createRefFor("lineThree")
        val lineFour = createRefFor("lineFour")

        constrain(lineOne) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(lineTwo.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(lineTwo) {
            start.linkTo(parent.start)
            top.linkTo(lineOne.bottom)
            end.linkTo(parent.end)
            bottom.linkTo(lineThree.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(lineThree) {
            start.linkTo(parent.start)
            top.linkTo(lineTwo.bottom)
            end.linkTo(parent.end)
            bottom.linkTo(lineFour.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(lineFour) {
            start.linkTo(parent.start)
            top.linkTo(lineThree.bottom)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        createVerticalChain(lineOne, lineTwo, lineThree, lineFour, chainStyle = ChainStyle.Spread)
    }

    ConstraintLayout(layoutSet, modifier = Modifier.fillMaxSize()) {
        LineOneInput()
        LineTwoInput()
        LineThreeInput()
        LineFourInput()
    }
}

@Composable
fun NumberButton(calculatorViewModel: CalculatorViewModel, number: String) {
    Button(shape = RectangleShape, border = BorderStroke(1.dp, Color.Black),
        onClick = { calculatorViewModel.onTextChange(number) },
        modifier = Modifier
            .layoutId(number)) {
        Text(text = number, fontSize = 30.sp)
    }
}

@Composable
fun LineOneInput(calculatorViewModel: CalculatorViewModel = viewModel()) {

    val buttonsSet = ConstraintSet {
        val seven = createRefFor("7")
        val eight = createRefFor("8")
        val nine = createRefFor("9")
        val divide = createRefFor("/")

        constrain(seven) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(eight.start)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        }

        constrain(eight) {
            start.linkTo(seven.end)
            top.linkTo(parent.top)
            end.linkTo(nine.start)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        }

        constrain(nine) {
            start.linkTo(eight.end)
            top.linkTo(parent.top)
            end.linkTo(divide.start)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        }

        constrain(divide) {
            start.linkTo(nine.end)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
            width = Dimension.fillToConstraints
        }

        createHorizontalChain(seven, eight, nine, divide, chainStyle = ChainStyle.Spread)
    }

    ConstraintLayout(buttonsSet, modifier = Modifier.layoutId("lineOne")) {
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.seven))
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.eight))
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.nine))
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.div))
    }
}

@Composable
fun LineTwoInput(calculatorViewModel: CalculatorViewModel = viewModel()) {

    val buttonSet = ConstraintSet {
        val four = createRefFor("4")
        val five = createRefFor("5")
        val six = createRefFor("6")
        val multiply = createRefFor("*")

        constrain(four) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(five.start)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(five) {
            start.linkTo(four.end)
            top.linkTo(parent.top)
            end.linkTo(six.start)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(six) {
            start.linkTo(five.end)
            top.linkTo(parent.top)
            end.linkTo(multiply.start)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(multiply) {
            start.linkTo(six.end)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        createHorizontalChain(four, five, six, multiply, chainStyle = ChainStyle.Spread)
    }

    ConstraintLayout(buttonSet, modifier = Modifier.layoutId("lineTwo")) {
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.four))
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.five))
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.six))
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.mult))
    }
}

@Composable
fun Backspace(calculatorViewModel: CalculatorViewModel = viewModel()) {
    IconButton(onClick = { calculatorViewModel.onBackspace() }, modifier = Modifier
        .wrapContentWidth()
        .fillMaxHeight()) {
        Icon(painter = painterResource(id = R.drawable.ic_backspace), contentDescription = "")
    }
}

@Composable
fun LineThreeInput(calculatorViewModel: CalculatorViewModel = viewModel()) {
    val buttonSet = ConstraintSet {
        val one = createRefFor("1")
        val two = createRefFor("2")
        val three = createRefFor("3")
        val subtract = createRefFor("-")

        constrain(one) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(two.start)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(two) {
            start.linkTo(one.end)
            top.linkTo(parent.top)
            end.linkTo(three.start)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(three) {
            start.linkTo(two.end)
            top.linkTo(parent.top)
            end.linkTo(subtract.start)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(subtract) {
            start.linkTo(three.end)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        createHorizontalChain(one, two, three, subtract, chainStyle = ChainStyle.Spread)
    }

    ConstraintLayout(buttonSet, modifier = Modifier.layoutId("lineThree")) {
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.one))
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.two))
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.three))
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.sub))
    }
}

@Composable
fun LineFourInput(calculatorViewModel: CalculatorViewModel = viewModel()) {

    val buttonSet = ConstraintSet {

        val zero = createRefFor("0")
        val add = createRefFor("+")
        val equals = createRefFor("equals")

        constrain(zero) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(equals.start)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(equals) {
            start.linkTo(zero.end)
            top.linkTo(parent.top)
            end.linkTo(add.start)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(add) {
            start.linkTo(equals.end)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        createHorizontalChain(zero, equals, add, chainStyle = ChainStyle.Spread)
    }

    ConstraintLayout(buttonSet, modifier = Modifier.layoutId("lineFour")) {
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.zero))
        Equals(calculatorViewModel = calculatorViewModel)
        NumberButton(calculatorViewModel = calculatorViewModel, number = stringResource(id = R.string.add))
    }
}

@Composable
fun Equals(calculatorViewModel: CalculatorViewModel) {
    Button(shape = RectangleShape, border = BorderStroke(1.dp, Color.Black), onClick = { calculatorViewModel.onEquals() }, modifier = Modifier
        .layoutId("equals")) {
        Text(text = stringResource(id = R.string.equal), fontSize = 30.sp)
        val error = calculatorViewModel.mathErrorLive.observeAsState()
        if (error.value != -1) {
            Toast.makeText(LocalContext.current, stringResource(id = error.value!!), Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculatorTestTheme {
        CalculatorTestTheme {
            Column(modifier = Modifier.fillMaxSize()) {
                EntryTextInput()
                ButtonsLayout()
            }
        }
    }
}