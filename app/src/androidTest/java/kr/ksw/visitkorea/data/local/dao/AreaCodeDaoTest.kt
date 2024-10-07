package kr.ksw.visitkorea.data.local.dao

import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import kr.ksw.visitkorea.data.local.databases.AreaCodeDatabase
import kr.ksw.visitkorea.data.local.entity.AreaCodeEntity
import kr.ksw.visitkorea.data.local.entity.SigunguCodeEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
class AreaCodeDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var areaCodeDatabase: AreaCodeDatabase

    private lateinit var dao: AreaCodeDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = areaCodeDatabase.areaCodeDao
    }

    @After
    fun tearDown() {
        areaCodeDatabase.close()
    }

    @Test
    fun getAllAreaCodeEntities_areaCodeListIsEmpty() = runTest {
        assert(dao.getAllAreaCodeEntities().isEmpty())
    }

    @Test
    fun upsertAreaCodeEntity_areaCodeIsUpserted() = runTest {
        val areaCodeEntity = AreaCodeEntity(
            code = "1", name = "서울"
        )
        dao.upsertAreaCodeEntity(areaCodeEntity)
        assert(dao.getAllAreaCodeEntities().isNotEmpty())
    }

    @Test
    fun upsertSigunguCodeEntity_sigunguCodeIsUpserted() = runTest {
        val sigunguCodeEntity = SigunguCodeEntity(
            areaCode = "1", code = "1", name = "강남구"
        )
        dao.upsertSigunguCodeEntity(sigunguCodeEntity)
        assert(dao.getSigunguCodeByAreaCode("1").isNotEmpty())
    }

    @Test
    fun `getSigunguCodeByAreaCode_sigunguCodeListByAreaCode1`() = runTest {
        for(i in 1..4) {
            dao.upsertSigunguCodeEntity(SigunguCodeEntity(
                id = i,
                areaCode = "1",
                code = "$i",
                name = "시군구 $i"
            ))
        }
        dao.upsertSigunguCodeEntity(SigunguCodeEntity(
            id = 5,
            areaCode = "2",
            code = "1",
            name = "가평군"
        ))

        val sigunguCodeEntities = dao.getSigunguCodeByAreaCode("1")
        assert(sigunguCodeEntities.isNotEmpty())
        assert(sigunguCodeEntities.size == 4)
    }
}