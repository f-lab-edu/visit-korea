package kr.ksw.visitkorea.presentation.splash

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.data.local.databases.AreaCodeDatabase
import kr.ksw.visitkorea.data.workmanager.AreaCodeWorker
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val areaCodeDatabase: AreaCodeDatabase
): ViewModel() {
    private val _sideEffect = MutableSharedFlow<SplashSideEffect>(replay = 0)
    val sideEffect: SharedFlow<SplashSideEffect> = _sideEffect.asSharedFlow()

    fun checkPermission(activity: ComponentActivity) {
        if(ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            viewModelScope.launch {
                _sideEffect.emit(SplashSideEffect.StartMainActivity)
            }
        } else {
            activity.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissionMap ->
                viewModelScope.launch {
                    if(permissionMap[Manifest.permission.ACCESS_COARSE_LOCATION] != true &&
                        permissionMap[Manifest.permission.ACCESS_FINE_LOCATION] != true) {
                        _sideEffect.emit(SplashSideEffect.PermissionDenied)
                    }
                    _sideEffect.emit(SplashSideEffect.StartMainActivity)
                }
            }.launch(arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ))
        }
    }

    fun initAreaCode(context: Context) {
        viewModelScope.launch {
            val areaCodeItems = areaCodeDatabase.areaCodeDao.getAllAreaCodeEntities()
            if(areaCodeItems.isEmpty()) {
                val workRequest = OneTimeWorkRequestBuilder<AreaCodeWorker>().build()
                WorkManager
                    .getInstance(context)
                    .enqueue(workRequest)
            } else {
                Log.d("SplashViewModel", areaCodeItems.toString())
                areaCodeItems.forEach {
                    Log.d("SplashViewModel", areaCodeDatabase.areaCodeDao.getSigunguCodeByAreaCode(it.code).toString())
                }
            }
        }
    }
}

sealed interface SplashSideEffect {
    data object PermissionDenied : SplashSideEffect
    data object StartMainActivity : SplashSideEffect
}