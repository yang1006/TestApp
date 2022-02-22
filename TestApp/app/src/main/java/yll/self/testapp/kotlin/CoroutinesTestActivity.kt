package yll.self.testapp.kotlin

import android.os.Bundle
import kotlinx.coroutines.*
import yll.self.testapp.BaseActivity
import yll.self.testapp.utils.LogUtil

/** 协程测试 https://juejin.cn/post/6908271959381901325 */
class CoroutinesTestActivity : BaseActivity() {

    companion object {
        private const val TAG = "CoroutinesTestActivity"
    }

    //协程作用域(GlobalScope) 挂起函数（suspend 修饰的函数 delay） 协程上下文（context = Dispatchers.IO），协程构造器(launch async)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main3()
//        GlobalScope.launch {
//            fetchDoc()
//        }
    }

    private fun log(msg: String) {
        LogUtil.d(TAG, "${Thread.currentThread().name}, $msg" )
    }

    fun main() {
        log("start")
        GlobalScope.launch {
            launch {
                delay(400)
                log("launch A")
            }
            launch {
                delay(300)
                log("launch B")
            }
            log("GlobalScope end")
        }
        log("main end")

    }

    //suspend 函数,只能由协程调用，或者其他 suspend 函数调用
    suspend fun fetchDoc() {
        val result = get("test url")
        log(result)
    }

    suspend fun get(url : String) = withContext(Dispatchers.IO) {
        delay(2000)
        "test doc data $url"
    }

    fun main2() {
        Thread(Runnable {
            //runBlocking 会阻塞当前线程，内部相同作用域的所有协程执行完成后才能执行在 runBlocking 之后声明的代码
            runBlocking {
                launch {
                    repeat(3) {
                        delay(100)
                        log("launch A")
                    }
                }
                launch {
                    repeat(3) {
                        delay(100)
                        log("launch B")
                    }
                }
                GlobalScope.launch {
                    repeat(3) {
                        delay(120)
                        log("GlobalScope ")
                    }
                }
                log("runBlocking end")
            }
            log("end")
        }).start()
    }


    fun main3() {
        Thread(Runnable {
            //runBlocking 会阻塞当前线程，内部相同作用域的所有协程执行完成后才能执行在 runBlocking 之后声明的代码
            runBlocking {
                // coroutineScope不会阻塞当前线程
                coroutineScope {
                    launch {
                        repeat(3) {
                            delay(100)
                            log("launch A")
                        }
                    }
                    launch {
                        repeat(3) {
                            delay(100)
                            log("launch B")
                        }
                    }
                    log("coroutineScope end")
                }
                log("runBlocking end")
            }
            log("thread end")
        }).start()
    }
}