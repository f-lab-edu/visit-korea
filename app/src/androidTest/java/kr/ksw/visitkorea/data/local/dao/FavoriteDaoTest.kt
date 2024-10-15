package kr.ksw.visitkorea.data.local.dao

import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kr.ksw.visitkorea.data.local.databases.FavoriteDatabase
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.domain.common.TYPE_TOURIST_SPOT
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
class FavoriteDaoTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var favoriteDatabase: FavoriteDatabase

    private lateinit var dao: FavoriteDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = favoriteDatabase.favoriteDao
    }

    @After
    fun tearDown() {
        favoriteDatabase.close()
    }

    @Test
    fun getAllFavorite_favoriteListIsEmpty() = runTest {
        val result = dao.getAllFavoriteEntity().first()
        assert(result.isEmpty())
    }

    @Test
    fun upsertFavorite_favoriteListIsNotEmpty() = runTest {
        dao.upsertFavoriteEntity(FavoriteEntity(
            title = "관광지",
            firstImage = "https://www.eee.rrr",
            address = "경기도 수원시 망포동",
            dist = null,
            contentId = "2029039",
            contentTypeId = TYPE_TOURIST_SPOT,
            eventStartDate = null,
            eventEndDate = null,
        ))

        val result = dao.getAllFavoriteEntity().first()
        assert(result.isNotEmpty())
    }

    @Test
    fun deleteFavorite_favoriteListIsEmpty() = runTest {
        val entity = FavoriteEntity(
            id = 1,
            title = "관광지",
            firstImage = "https://www.eee.rrr",
            address = "경기도 수원시 망포동",
            dist = null,
            contentId = "2029039",
            contentTypeId = TYPE_TOURIST_SPOT,
            eventStartDate = null,
            eventEndDate = null,
        )
        dao.upsertFavoriteEntity(entity)
        assert(dao.getAllFavoriteEntity().first().isNotEmpty())
        dao.deleteFavoriteEntity(entity)
        assert(dao.getAllFavoriteEntity().first().isEmpty())
    }

    @Test
    fun deleteFavoriteByContetnId_favoriteListIsEmpty() = runTest {
        val entity = FavoriteEntity(
            title = "관광지",
            firstImage = "https://www.eee.rrr",
            address = "경기도 수원시 망포동",
            dist = null,
            contentId = "2029039",
            contentTypeId = TYPE_TOURIST_SPOT,
            eventStartDate = null,
            eventEndDate = null,
        )
        dao.upsertFavoriteEntity(entity)
        assert(dao.getAllFavoriteEntity().first().isNotEmpty())
        dao.deleteFavoriteEntityByContentId("2029039")
        assert(dao.getAllFavoriteEntity().first().isEmpty())
    }

    @Test
    fun deleteFavoriteByContetnId_favoriteListNotIsEmpty() = runTest {
        val entity = FavoriteEntity(
            title = "관광지",
            firstImage = "https://www.eee.rrr",
            address = "경기도 수원시 망포동",
            dist = null,
            contentId = "2029039",
            contentTypeId = TYPE_TOURIST_SPOT,
            eventStartDate = null,
            eventEndDate = null,
        )
        val entity2 = FavoriteEntity(
            title = "행사",
            firstImage = "https://www.eee.rrr",
            address = "경기도 수원시 망포동",
            dist = null,
            contentId = "1154987",
            contentTypeId = TYPE_TOURIST_SPOT,
            eventStartDate = null,
            eventEndDate = null,
        )
        dao.upsertFavoriteEntity(entity)
        dao.upsertFavoriteEntity(entity2)
        assert(dao.getAllFavoriteEntity().first().isNotEmpty())
        dao.deleteFavoriteEntityByContentId("1154987")
        assert(dao.getAllFavoriteEntity().first().isNotEmpty())
    }

    @Test
    fun existFavoriteEntity_isExist() = runTest {
        val entity = FavoriteEntity(
            title = "관광지",
            firstImage = "https://www.eee.rrr",
            address = "경기도 수원시 망포동",
            dist = null,
            contentId = "2029039",
            contentTypeId = TYPE_TOURIST_SPOT,
            eventStartDate = null,
            eventEndDate = null,
        )
        dao.upsertFavoriteEntity(entity)
        assert(dao.existFavoriteEntity("2029039") == 1)
    }

    @Test
    fun existFavoriteEntity_isNotExist() = runTest {
        val entity = FavoriteEntity(
            title = "관광지",
            firstImage = "https://www.eee.rrr",
            address = "경기도 수원시 망포동",
            dist = null,
            contentId = "2029039",
            contentTypeId = TYPE_TOURIST_SPOT,
            eventStartDate = null,
            eventEndDate = null,
        )
        dao.upsertFavoriteEntity(entity)
        assert(dao.existFavoriteEntity("1154987") != 1)
        assert(dao.existFavoriteEntity("1154987") == 0)
    }
}