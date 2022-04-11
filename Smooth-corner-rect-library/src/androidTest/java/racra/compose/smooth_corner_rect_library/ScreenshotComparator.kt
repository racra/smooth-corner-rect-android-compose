package racra.compose.smooth_corner_rect_library

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import androidx.test.platform.app.InstrumentationRegistry
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.lang.AssertionError

/**
 * Takes a screenshot of the given node and compares the resulting image to the expected screenshot
 * throwing an error if they aren't identical.
 *
 * Expected images are expected to be in androidTest/assets.
 *
 * Screenshots taken will be saved in /data/data/{package}/files on device.
 *
 * @param imageName the name given to the image file in the assets directory of the tests without
 * the file extension
 * @param node the node in the semantics tree of a composeTestRule to be tested
 * @param saveTestScreenshots set this to true if you would like to save the screenshots on device
 */
fun assertImageMatchesExpected(
    imageName: String,
    node: SemanticsNodeInteraction,
    saveTestScreenshots: Boolean = false
) {

    val image = node.captureToImage().asAndroidBitmap()

    if (saveTestScreenshots) {
        saveImage(imageName, image)
    }

    val expectedImage = openImage(imageName)

    image.compare(expectedImage)
}

private fun saveImage(
    imageName: String,
    image: Bitmap
) {
    val fileName = "${imageName}_${image.width}_${image.height}_${System.currentTimeMillis()}"
    val path = InstrumentationRegistry.getInstrumentation().targetContext.filesDir

    FileOutputStream("$path/$fileName.png").use {
        image.compress(Bitmap.CompressFormat.PNG, 100, it)
    }

    println("Test image saved at $path/$fileName.png")
}

private fun openImage(
    imageName: String
): Bitmap {
    val fileName = "${imageName}.png"

    try {
        return InstrumentationRegistry.getInstrumentation().context.resources.assets.open(
            fileName
        ).use {
            BitmapFactory.decodeStream(it)
        }
    } catch (error: FileNotFoundException) {
        throw AssertionError(
            "Could not find $fileName in androidTest/assets to compare it to test image")
    }
}

private fun Bitmap.compare(
    other: Bitmap
) {
    if (this.width != other.width || this.height != other.height) {
        throw AssertionError(
            """
                Test screenshot and expected screenshot have different sizes:
                 - Expected screenshot (${other.width} px by ${other.height} px)
                 - Test screenshot (${this.width} px by ${this.height} px)
                Ensure current testing device density is the same as the one used
                to produce expected screenshots.
            """.trimIndent()
        )
    }

    // These will be used to store the current rows being compared
    val thisRow = IntArray(this.width)
    val otherRow = IntArray(other.width)

    for (row in 0 until this.height) {

        // Populate the IntArrays with current rows
        this.getRow(thisRow, row)
        other.getRow(otherRow, row)

        if (!thisRow.contentEquals(otherRow)) {
            throw AssertionError("Test screenshot does not match expected screenshot")
        }
    }
}

private fun Bitmap.getRow(pixels: IntArray, row: Int) =
    this.getPixels(pixels, 0, this.width, 0, row, this.width, 1)