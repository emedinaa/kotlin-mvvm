package com.emedinaa.kotlinmvvm.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.emedinaa.kotlinmvvm.di.Injection
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.ui.theme.ComposeSampleTheme
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import com.emedinaa.kotlinmvvm.viewmodel.ViewModelFactory

/**
 * @author Eduardo Medina
 */
private const val TAG = "CONSOLE"
class MuseumActivity : ComponentActivity() {

    private val viewModel by viewModels<MuseumViewModel> {
        ViewModelFactory(Injection.providerRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSampleTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Container(viewModel)
                }
            }
        }
        viewModel.loadMuseums()
    }
}

//https://developer.android.com/jetpack/compose/tutorial
//https://developer.android.com/jetpack/compose/state
@Composable
fun ErrorView(error:String) {
    Text(text = error)
}

//https://coil-kt.github.io/coil/compose/

@Composable
fun RowContent(museum: Museum) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp),
        horizontalAlignment = Alignment.Start) {
        Text(text = "${museum.name}")
        Spacer(modifier = Modifier.height(4.dp))
        Image(
            painter = rememberImagePainter(
                data = museum.photo,
                builder = {
                    crossfade(true)
                }
            ),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Saber más",
        modifier = Modifier.background(
            color = MaterialTheme.colors.secondaryVariant,
        ).padding(2.dp),
        color = MaterialTheme.colors.background)
        Spacer(modifier = Modifier.height(8.dp))
    }
}
@Composable
fun RowMuseum(museum: Museum) {
    Card(   backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        RowContent(museum)
    }
}

@Composable
fun Container(viewModel:MuseumViewModel) {
    val museums:List<Museum> by viewModel.museums.observeAsState(emptyList())
    val error:String by viewModel.onMessageError.observeAsState("")
    LazyColumn {
        items(museums) {
            RowMuseum(it)
        }
    }
    ErrorView(error)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        RowMuseum(Museum(1,"Museo Nacional de Arqueología, Antropología e Historia del Perú", "http://museos.cultura.pe/sites/default/files/styles/museos_portada/public/museos/imagen/rnm_1493925752.jpg"))
    }
}