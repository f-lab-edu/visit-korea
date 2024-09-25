package kr.ksw.visitkorea.data.remote

import kotlinx.coroutines.test.runTest
import kr.ksw.visitkorea.data.repository.FakeAreaCodeRepository
import org.junit.Before
import org.junit.Test

class AreaCodeApiTest {

    private lateinit var fakeAreaCodeRepository: FakeAreaCodeRepository

    @Before
    fun setUp() {
        fakeAreaCodeRepository = FakeAreaCodeRepository()
    }

    @Test
    fun `getAreaCode request success`() = runTest {
        val areaCodeItems = fakeAreaCodeRepository.getAreaCode().getOrNull()
        assert(areaCodeItems != null)
    }

    @Test
    fun `getSigunguCode request success, valid area code`() = runTest {
//        val areaCodeItems = fakeAreaCodeRepository.getSigunguCode("1").getOrNull()
        val areaCodeItems = fakeAreaCodeRepository.getSigunguCode("31").getOrNull()
        assert(areaCodeItems != null)
    }

    @Test
    fun `getSigunguCode request failed, invalid area code`() = runTest {
        val areaCodeItems = fakeAreaCodeRepository.getSigunguCode("10").getOrNull()
        assert(areaCodeItems == null)
    }

}