<h1 align="center">Smooth Corner Rect Android Compose</h1>
<p align="center">An Android Jetpack Compose Library which implements true smooth rounded corner rectangles also known as Squircles/Superellipses with custumizable radius and smoothness values for each individual corner.</p>

## Usage

Shapes created by this library can be used in themes and regular composables, check out the [sample](/app) app for a working demo of the library.

####

Creating a Squircle shape

// Todo - screenshot

```kotlin
Surface(
    modifier = Modifier
        .padding(25.dp)
        .height(200.dp)
        .width(200.dp),
    color = Color(0xFFF6EABE),
    shape = AbsoluteSmoothCornerShape(50.dp, 100)
) {}
```
