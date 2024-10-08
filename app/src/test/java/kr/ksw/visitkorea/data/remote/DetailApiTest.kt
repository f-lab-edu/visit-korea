package kr.ksw.visitkorea.data.remote

import kotlinx.coroutines.test.runTest
import kr.ksw.visitkorea.data.repository.FakeDetailRepository
import org.junit.Before
import org.junit.Test

class DetailApiTest {

    private lateinit var detailRepository: FakeDetailRepository

    @Before
    fun setUp() {
        detailRepository = FakeDetailRepository()
    }

    @Test
    fun `get detail common by contentId success, result is not null`() = runTest {
        val result = detailRepository.getDetailCommon("2662855")
        assert(result.getOrNull() != null)
    }

    @Test
    fun `get detail common by contentId failed, result is null`() = runTest {
        val result = detailRepository.getDetailCommon("1")
        assert(result.getOrNull() == null)
    }

}