import android.util.Log
import com.wassha.androidcodechallenge.data.JokeRepositoryImpl
import com.wassha.androidcodechallenge.data.local.dao.JokeDao
import com.wassha.androidcodechallenge.data.local.entities.JokeEntity
import com.wassha.androidcodechallenge.data.remote.JokeApi
import com.wassha.androidcodechallenge.domain.JokeRepository
import com.wassha.androidcodechallenge.domain.models.Joke
import com.wassha.androidcodechallenge.domain.models.JokeSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doThrow

@ExperimentalCoroutinesApi
class JokeRepositoryTest {
    private lateinit var jokeRepository: JokeRepository

    @Mock
    private lateinit var jokeApi: JokeApi

    @Mock
    private lateinit var jokeDao: JokeDao

    @Mock
    private lateinit var logger: Log

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        jokeRepository = JokeRepositoryImpl(jokeApi, jokeDao)
    }

    @Test
    fun `fetchJoke when success`() =
        runTest {
            val testJoke = Joke(1, "Mocked Joke")
            Mockito.`when`(jokeApi.getJoke()).thenReturn(testJoke)
            val result: JokeSource = jokeRepository.fetchJoke()

            assertEquals("Mocked Joke", result.joke.joke)
            assertEquals(false, result.isFromDb)
        }

    @Test
    fun `fetchJoke from db if network error`() =
        runTest {
            val testLocalJoke = JokeEntity(1, "Local Joke")
            Mockito.`when`(jokeApi.getJoke()).doThrow(RuntimeException("Error"))
            Mockito.`when`(jokeDao.getJoke()).thenReturn(testLocalJoke)

            val result: JokeSource = jokeRepository.fetchJoke()

            assertEquals("Local Joke", result.joke.joke)
            assertEquals(true, result.isFromDb)
        }

    @Test
    fun `fetchJoke when network error and no stored jokes`() =
        runTest {
            Mockito.`when`(jokeApi.getJoke()).doThrow(RuntimeException("Error"))
            Mockito.`when`(jokeDao.getJoke()).thenReturn(null)

            val result: JokeSource = jokeRepository.fetchJoke()

            assertEquals("", result.joke.joke)
            assertEquals(true, result.isFromDb)
        }

    @Test
    fun `fetchJoke when db exception return remote response`() =
        runTest {
            val testRemoteJoke = Joke(1, "Remote Joke")
            Mockito.`when`(jokeApi.getJoke()).thenReturn(testRemoteJoke)
            Mockito.`when`(jokeDao.clearTable()).doThrow(RuntimeException("Error"))

            val result: JokeSource = jokeRepository.fetchJoke()

            assertEquals("Remote Joke", result.joke.joke)
            assertEquals(false, result.isFromDb)
        }
}
