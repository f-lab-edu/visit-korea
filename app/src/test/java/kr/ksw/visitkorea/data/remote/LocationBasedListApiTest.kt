package kr.ksw.visitkorea.data.remote

import kotlinx.coroutines.test.runTest
import kr.ksw.visitkorea.data.repository.FakeLocationBasedListRepository
import org.junit.Before
import org.junit.Test

class LocationBasedListApiTest {
    private lateinit var fakeLocationBasedListRepository: FakeLocationBasedListRepository

    @Before
    fun setUp() {
        fakeLocationBasedListRepository = FakeLocationBasedListRepository()
    }

    @Test
    fun `getLocationBasedListByContentType match contentTypeId success, Items not empty`() = runTest {
        val items = fakeLocationBasedListRepository.getLocationBasedListByContentType(
            1, 10, "126.981611", "37.568477", "14"
        ).getOrNull()
        assert(items?.isNotEmpty() == true)
    }

    @Test
    fun `getLocationBasedListByContentType no match contentTypeId success, Items is empty`() = runTest {
        val items = fakeLocationBasedListRepository.getLocationBasedListByContentType(
            1, 10, "126.981611", "37.568477", "12"
        ).getOrNull()
        assert(items?.isEmpty() == true)
    }

}