package kr.ksw.visitkorea.data.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.ksw.visitkorea.data.local.databases.AreaCodeDatabase
import kr.ksw.visitkorea.data.local.entity.AreaCodeEntity
import kr.ksw.visitkorea.data.local.entity.SigunguCodeEntity
import kr.ksw.visitkorea.data.repository.AreaCodeRepository

@HiltWorker
class AreaCodeWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val areaCodeRepository: AreaCodeRepository,
    private val areaCodeDatabase: AreaCodeDatabase
): CoroutineWorker(appContext, params) {
    
    override suspend fun doWork(): Result {
        val areaCodeItems = areaCodeRepository.getAreaCode().getOrNull() ?: return Result.failure()
        areaCodeItems.forEach { areaCodeItem ->
            withContext(Dispatchers.IO) {
                areaCodeDatabase.areaCodeDao.upsertAreaCodeEntity(
                    AreaCodeEntity(
                        code = areaCodeItem.code,
                        name = areaCodeItem.name
                    )
                )
                val sigunguItems = areaCodeRepository.getSigunguCode(areaCodeItem.code).getOrNull()
                sigunguItems?.forEach { sigunguItem ->
                    areaCodeDatabase.areaCodeDao.upsertSigunguCodeEntity(SigunguCodeEntity(
                        areaCode = areaCodeItem.code,
                        code = sigunguItem.code,
                        name = sigunguItem.name
                    ))
                }
            }
        }
        return Result.success()
    }

}