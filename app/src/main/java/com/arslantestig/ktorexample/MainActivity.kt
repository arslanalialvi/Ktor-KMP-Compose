package com.arslantestig.ktorexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.arslantestig.ktorexample.model.ResponseModel
import com.arslantestig.ktorexample.network.ApiService
import com.arslantestig.ktorexample.ui.theme.KtorExampleTheme

class MainActivity : ComponentActivity() {
    private val apiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtorExampleTheme {
                val products = produceState(initialValue = emptyList<ResponseModel>(), producer = {
                    value = apiService.getProducts()
                })
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumnOfProducts(products = products.value)

                }
            }
        }
    }
}

@Composable
fun LazyColumnOfProducts(products: List<ResponseModel>) {
    LazyColumn {
        items(products) {
            LazyColumnItem(item = it)
        }
    }
}

@Composable
fun LazyColumnItem(item: ResponseModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp, top = 6.dp, start = 6.dp, end = 6.dp)
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //set image URL
            val painter = rememberImagePainter(
                data = item.image,
                builder = { error(R.drawable.ic_launcher_background) })

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop,
                painter = painter,
                contentDescription = "Coil Image"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = item.title, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = item.description, fontSize = 14.sp)


        }
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KtorExampleTheme {}
}