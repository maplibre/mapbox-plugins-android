package org.maplibre.android.plugins.testapp.activity.localization

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.OnMapReadyCallback
import org.maplibre.android.maps.Style
import org.maplibre.android.plugins.localization.LocalizationPlugin
import org.maplibre.android.plugins.localization.MapLocale
import org.maplibre.android.plugins.testapp.TestStyles
import org.maplibre.android.plugins.testapp.R
import org.maplibre.android.plugins.testapp.Utils
import org.maplibre.android.plugins.testapp.databinding.ActivityLocalizationBinding

class LocalizationActivity : AppCompatActivity(), OnMapReadyCallback {

    private var localizationPlugin: LocalizationPlugin? = null
    private var maplibreMap: MapLibreMap? = null
    private var mapIsLocalized: Boolean = false
    private lateinit var mapView: MapView
    private lateinit var binding: ActivityLocalizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalizationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapIsLocalized = true
        Toast.makeText(this, R.string.change_language_instruction, Toast.LENGTH_LONG).show()
        mapView = findViewById<View>(R.id.mapView) as MapView
        mapView.onCreate(savedInstanceState)
    }

    override fun onMapReady(maplibreMap: MapLibreMap) {
        maplibreMap?.setStyle(TestStyles.BRIGHT.url) { style ->
            this.maplibreMap = maplibreMap
            localizationPlugin =
                LocalizationPlugin(
                    mapView,
                    maplibreMap,
                    style
                ).also { localizationPlugin ->
                    localizationPlugin.matchMapLanguageWithDeviceDefault()
                }

            binding.fabLocalize.setOnClickListener {
                mapIsLocalized = if (mapIsLocalized) {
                    localizationPlugin?.setMapLanguage(
                        MapLocale(
                            MapLocale.FRENCH
                        )
                    )
                    Toast.makeText(this, R.string.map_not_localized, Toast.LENGTH_SHORT).show()
                    false
                } else {
                    localizationPlugin?.matchMapLanguageWithDeviceDefault()
                    Toast.makeText(this, R.string.map_localized, Toast.LENGTH_SHORT).show()
                    true
                }
            }

            binding.fabCamera.setOnClickListener {
                val locale = nextMapLocale
                localizationPlugin?.apply {
                    setMapLanguage(locale)
                    setCameraToLocaleCountry(locale, 25)
                }
            }

            binding.fabStyles.setOnClickListener {
                maplibreMap.setStyle(Style.Builder().fromUri(Utils.nextStyle))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_languages, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.english -> {
                localizationPlugin?.setMapLanguage(MapLocale.ENGLISH)
                return true
            }

            R.id.spanish -> {
                localizationPlugin?.setMapLanguage(MapLocale.SPANISH)
                return true
            }

            R.id.french -> {
                localizationPlugin?.setMapLanguage(MapLocale.FRENCH)
                return true
            }

            R.id.german -> {
                localizationPlugin?.setMapLanguage(MapLocale.GERMAN)
                return true
            }

            R.id.russian -> {
                localizationPlugin?.setMapLanguage(MapLocale.RUSSIAN)
                return true
            }

            R.id.chinese -> {
                localizationPlugin?.setMapLanguage(MapLocale.CHINESE)
                return true
            }

            R.id.simplified_chinese -> {
                localizationPlugin?.setMapLanguage(MapLocale.SIMPLIFIED_CHINESE)
                return true
            }

            R.id.portuguese -> {
                localizationPlugin?.setMapLanguage(MapLocale.PORTUGUESE)
                return true
            }

            R.id.arabic -> {
                localizationPlugin?.setMapLanguage(MapLocale.ARABIC)
                return true
            }

            R.id.japanese -> {
                localizationPlugin?.setMapLanguage(MapLocale.JAPANESE)
                return true
            }

            R.id.korean -> {
                localizationPlugin?.setMapLanguage(MapLocale.KOREAN)
                return true
            }

            R.id.vietnamese -> {
                localizationPlugin?.setMapLanguage(MapLocale.VIETNAMESE)
                return true
            }

            R.id.italian -> {
                localizationPlugin?.setMapLanguage(MapLocale.ITALIAN)
                return true
            }

            R.id.local -> {
                localizationPlugin?.setMapLanguage(MapLocale.LOCAL_NAME)
                return true
            }

            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val LOCALES = arrayOf(
            MapLocale.CANADA, MapLocale.GERMANY, MapLocale.CHINA,
            MapLocale.US, MapLocale.CANADA_FRENCH, MapLocale.JAPAN, MapLocale.KOREA,
            MapLocale.FRANCE, MapLocale.SPAIN, MapLocale.VIETNAM, MapLocale.ITALY, MapLocale.UK
        )

        private var index: Int = 0

        val nextMapLocale: MapLocale
            get() {
                index++
                if (index == LOCALES.size) {
                    index = 0
                }
                return LOCALES[index]
            }
    }
}
