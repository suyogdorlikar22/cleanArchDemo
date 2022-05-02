package com.shayu.cleanarchdemo


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import coil.Coil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.shayu.cleanarchdemo.components.CircularIndeterminateProgressBar
import com.shayu.cleanarchdemo.utils.visible
import com.shayu.cleanarchdemo.viewmodel.HomeViewModel
import com.shayu.network.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)


        // progressbar.visible(false)
        viewModel.sliderData()


                 viewModel.sliderResponse?.observe(this) {
                     when (it) {

                         is Resource.Success -> {
                             //   progressbar.visible(false)
                             lifecycleScope.launch {

                                 Log.d("___sliderPlainData", "" + viewModel.loading.value)
                                 Log.d("___sliderPlainData", "" + it.value.Data.get(0).image_name)
                                 setContent {
                                 Column(
                                     horizontalAlignment = Alignment.CenterHorizontally,
                                     modifier = Modifier
                                         .fillMaxSize()
                                         .wrapContentSize()
                                         .padding(24.dp)
                                 ) {
                                     CircularIndeterminateProgressBar(
                                         isDisplayed = viewModel.loading.value,
                                         verticalBias = 0.3f
                                     )
                                     NetworkImageComponentGlide(
                                         url = "https://dev2.mymedisage.com/storage/images/slider/" +it.value.Data.get(0).image_name
                                     )

                                     Image(
                                         painterResource(R.drawable.ic_launcher_foreground),
                                         contentDescription = null,
                                     )



                                     Spacer(Modifier.height(24.dp))
                                     Text(
                                         text = stringResource(R.string.app_name),
                                         style = MaterialTheme.typography.subtitle1,
                                         textAlign = TextAlign.Center,
                                         modifier = Modifier.fillMaxWidth()
                                     )
                                     Spacer(Modifier.height(16.dp))
                                     Text(
                                         text = stringResource(R.string.app_name),
                                         style = MaterialTheme.typography.body2,
                                         textAlign = TextAlign.Center,
                                         modifier = Modifier.fillMaxWidth()
                                     )
                                 }
                             }
                                  }
                         }
                         is Resource.Loading -> {
                             setContent {
                                 CircularIndeterminateProgressBar(
                                     isDisplayed = viewModel.loading.value,
                                     verticalBias = 0.3f
                                 )
                             }
                         }
                         is Resource.Failure -> {
                             Log.d("___sliderPlainFailure", "" + viewModel.loading.value)

                         }
                     }
                 }
    }
    @Composable
    fun NetworkImageComponentGlide(
        url: String, modifier: Modifier = Modifier
    ) {
        // Reacting to state changes is the core behavior of Compose. You will notice a couple new
        // keywords that are compose related - remember & mutableStateOf.remember{} is a helper
        // composable that calculates the value passed to it only during the first composition. It then
        // returns the same value for every subsequent composition. Next, you can think of
        // mutableStateOf as an observable value where updates to this variable will redraw all
        // the composable functions that access it. We don't need to explicitly subscribe at all. Any
        // composable that reads its value will be recomposed any time the value
        // changes. This ensures that only the composables that depend on this will be redraw while the
        // rest remain unchanged. This ensures efficiency and is a performance optimization. It
        // is inspired from existing frameworks like React.
        var image by remember { mutableStateOf<ImageBitmap?>(null) }
        var drawable by remember { mutableStateOf<Drawable?>(null) }
        val sizeModifier = modifier
            .height(100.dp)
            .width(100.dp)
            .fillMaxWidth(50f)
            .fillMaxHeight(50f)
            .sizeIn(maxHeight = 100.dp)

        // LocalContext is a LocalComposition for accessting the context value that we are used to using
        // in Android.

        // LocalComposition is an implicit way to pass values down the compose tree. Typically, we pass values
        // down the compose tree by passing them as parameters. This makes it easy to have fairly
        // modular and reusable components that are easy to test as well. However, for certain types
        // of data where multiple components need to use it, it makes sense to have an implicit way
        // to access this data. For such scenarios, we use LocalComposition. In this example, we use the
        // LocalContext to get hold of the Context object. In order to get access to the latest
        // value of the LocalComposition, use the "current" property eg - LocalContext.current. Some other
        // examples of common LocalComposition's are LocalTextInputService,LocalDensity, etc.
        val context = LocalContext.current
        // Sometimes we need to make changes to the state of the app. For those cases, Composes provides
        // some Effect API's which provide a way to perform side effects in a predictable manner.
        // DisposableEffect is one such side effect API that provides a mechanism to perform some
        // clean up actions if the key to the effect changes or if the composable leaves composition.
        DisposableEffect(url) {
            val glide = Glide.with(context)
            val target = object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    image = null
                    drawable = placeholder
                }

                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                    image = bitmap.asImageBitmap()
                }
            }
            glide
                .asBitmap()
                .load(url)
                .into(target)

            onDispose {
                image = null
                drawable = null
                glide.clear(target)
            }
        }

        val theImage = image
        val theDrawable = drawable
        if (theImage != null) {
            // Column is a composable that places its children in a vertical sequence. You
            // can think of it similar to a LinearLayout with the vertical orientation.
            // In addition we also pass a few modifiers to it.

            // You can think of Modifiers as implementations of the decorators pattern that are
            // used to modify the composable that its applied to.
            Column(
                modifier = sizeModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image is a pre-defined composable that lays out and draws a given [ImageBitmap].
                Image(bitmap = theImage, contentDescription = null)
            }
        } else if (theDrawable != null) {
            // We use the Canvas composable that gives you access to a canvas that you can draw
            // into. We also pass it a modifier.
            Canvas(modifier = sizeModifier) {
                drawIntoCanvas { canvas ->
                    theDrawable.draw(canvas.nativeCanvas)
                }
            }
        }
    }

}
