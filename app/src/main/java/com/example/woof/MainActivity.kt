
package com.example.woof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
//import com.example.compose.woofThem
import com.example.woof.data.Dog
import com.example.woof.data.dogs
//import com.example.woof.ui.theme.woofThem?

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
//                    AppBar(modifier = Modifier)
                    WoofApp()
                }
            }
        }
    }
}

/**
 * Composable that displays an app bar and a list of dogs.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(modifier: Modifier){
    CenterAlignedTopAppBar(title = {
        Row() {
            Image(painter = painterResource(id = R.drawable.ic_woof_logo), contentDescription = null,
                modifier=modifier.size(32.dp))
            Text(text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge)
        }
    },
        modifier = Modifier)
}
@Composable
fun WoofApp() {

    LazyColumn {
        items(dogs) {
            DogItem(dog = it, modifier = Modifier.padding(16.dp))
        }
    }
}

/**
 * Composable that displays a list item containing a dog icon and their information.
 *
 * @param dog contains the data that populates the list item
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogItem(
    dog: Dog,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer)
    Card(modifier= Modifier
        .padding(12.dp)
        .clip(shape = RoundedCornerShape(34.dp, 134.dp, 12.dp, 3.dp))) {
        Column(modifier=Modifier.
        animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessVeryLow
            )

        )
            .background(color=color)) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(11.dp)
            ) {
                DogIcon(dog.imageResourceId)
                Spacer(modifier = Modifier.weight(0.5f))
                DogInformation(dog.name, dog.age)
                Spacer(modifier = Modifier.weight(1f))
                DogIconButton(extended = expanded, onClick = { expanded=!expanded })
            }
            if (expanded) {
                DogHobby(
                    dogHobby = dog.hobbies,
                    modifier = Modifier.padding(start = 12.dp, bottom = 9.dp)
                )
            }
        }

    }
}

/**
 * Composable that displays a photo of a dog.
 *
 * @param dogIcon is the resource ID for the image of the dog
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogIcon(
    @DrawableRes dogIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(84.dp)
            .padding(start = 23.dp)
            .clip(shape = RoundedCornerShape(34.dp)),
        painter = painterResource(dogIcon),

        // Content Description is not needed here - image is decorative, and setting a null content
        // description allows accessibility services to skip this element during navigation.

        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

/**
 * Composable that displays a dog's name and age.
 *
 * @param dogName is the resource ID for the string of the dog's name
 * @param dogAge is the Int that represents the dog's age
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogInformation(
    @StringRes dogName: Int,
    dogAge: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(start = 34.dp)) {
        Text(
            text = stringResource(dogName),
            modifier = Modifier.padding(top = 16.dp),

        )
        Text(
            text = stringResource(R.string.years_old, dogAge),
        )
    }
}

/**
 * Composable that displays what the UI of the app looks like in light theme in the design tab.
 */
@Composable
fun DogIconButton(
    extended:Boolean,
    onClick:()->Unit,
    modifier: Modifier=Modifier
){
    IconButton(onClick = onClick,
        modifier=modifier) {
        Icon(imageVector = if(extended) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore, contentDescription = stringResource(id = R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary)


    }
}
@Composable
fun DogHobby(
    @StringRes dogHobby:Int,
    modifier: Modifier=Modifier
){
    Column(modifier = modifier) {
        Text(text = stringResource(id = R.string.about),
            style = MaterialTheme.typography.labelSmall)
        Text(text = stringResource(dogHobby),
            style = MaterialTheme.typography.bodyLarge)
    }
}
@Preview
@Composable
fun WoofPreview() {
    AppTheme(darkTheme = true) {
        WoofApp()
    }
}