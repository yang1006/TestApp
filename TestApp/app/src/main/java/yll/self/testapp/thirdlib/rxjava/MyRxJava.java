package yll.self.testapp.thirdlib.rxjava;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import yll.self.testapp.utils.LogUtil;


/**
 * Created by yll on 17/9/26.
 *
 * 给 Android 开发者的 RxJava 详解
 * https://gank.io/post/560e15be2dca930e00da1083#toc_1
 *
 * RxJava 的观察者模式：
 * RxJava 有4个基本概念：Observable（可观察者，即被观察者）、Observer（观察者）、subscribe（订阅）、event（事件）
 * Observable 和 Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。
 *
 * 与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick() / onEvent()）之外，
 * 还定义了两个特殊的事件：onCompleted() 和 onError()。
 *
 */

public class MyRxJava {

    public static final String TAG = "MyRxJava";

//    public void helloWorld(){
//        Flowable.just("Hello World").subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println(s);
//            }
//        });
//    }

    /**创建一个观察者 Observer */
    Observer<String> observer = new Observer<String>() {

        @Override
        public void onNext(String value) {
            LogUtil.println("onNext->" + value);
            Log.e(TAG, "onNext->" + value);
        }

        @Override
        public void onCompleted() {
            LogUtil.println( "onCompleted");
            Log.e(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            LogUtil.println("onError->" + e);
            Log.e(TAG, "onError->" + e);
        }

    };

    /**
     * Subscriber 订阅者 实现了 Observer 接口并有扩展， 和Observer使用方式完全一样
     * 增加 onStart() 方法在 subscribe 刚开始，而事件还未发送之前被调用
     * 和 unsubscribe() 方法用于取消订阅
     * todo 2.0源码并不是实现Observer接口
     * */
    Subscriber<String> subscriber = new Subscriber<String>() {

        @Override
        public void onStart() {
            LogUtil.println("onStart");
            Log.e(TAG, "onStart");
        }

        @Override
        public void onNext(String value) {
            LogUtil.println("onNext->" + value);
            Log.e(TAG, "onNext->" + value);
        }

        @Override
        public void onCompleted() {
            LogUtil.println("onCompleted");
            Log.e(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            LogUtil.println("onError->" + e);
            Log.e(TAG, "onError->" + e);
        }

    };

    /**
     * 创建一个被观察者 Observable
     * 它决定什么时候触发事件以及触发怎样的事件
     * todo
     * create() 方法是 RxJava 最基本的创造事件序列的方法
     * */
    Observable observable = Observable.create(new Observable.OnSubscribe<String>(){
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello!");
            subscriber.onNext("Hi!");
            subscriber.onNext("How");
            subscriber.onCompleted();
        }
    });

    /**
     *  RxJava 还提供了一些方法用来快捷创建事件队列
     *  just(T...): 将传入的参数依次发送出来。
     * */
    Observable observableJust = Observable.just("hello", "hi", "how");

    /**
     * from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
     * */
    String[] words = {"hello", "hi", "how"};
    Observable observableFrom = Observable.from(words);

    /***/
    public void testCreate(){
        observable.subscribe(subscriber);
    }

    public void testJust(){
        observableJust.subscribe(subscriber);
    }

    public void testFrom(){
        observableFrom.subscribe(subscriber);
    }

    /**subscribe() 还支持不完整定义的回调*/

    /** Action1 1参数无返回值*/
    Action1<String> onNextAction = new Action1<String>() {
        @Override
        public void call(String s) {
            LogUtil.println("onNextAction->"+s);
            Log.e(TAG, "onNextAction->"+s);
        }
    };

    Action1<Throwable> onErrorAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            LogUtil.println("onErrorAction->"+throwable);
            Log.e(TAG, "onErrorAction->"+throwable);
        }
    };

    /** Action0 无参数无返回值 */
    Action0 onCompletedAction = new Action0() {
        @Override
        public void call() {
            LogUtil.println("onCompletedAction");
            Log.e(TAG, "onCompletedAction");
        }
    };

    /**测试不完整定义的回调*/
    public void testActions(){
        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted(）
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }


    /**打印字符串数组*/
    public void printStringArray(){
        String[] names = {"111", "222", "333", "444"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.println(s);
                        Log.e("yll", s);
                    }
                });

    }

    /**
     * 一次基本的 RxJava 调用
     * 由指定的一个 drawable 文件 id drawableRes 取得图片，并显示在 ImageView 中，并在出现异常的时候打印 Toast 报错*/
    public void setImageView(final Activity activity, final int resId, final ImageView imageView){

       Observable.create(new Observable.OnSubscribe<Drawable>() {
           @Override
           public void call(Subscriber<? super Drawable> subscriber) {
               Drawable drawable = activity.getResources().getDrawable(resId);
               subscriber.onNext(drawable);
               subscriber.onCompleted();
           }
       }).subscribe(new Observer<Drawable>() {
           @Override
           public void onCompleted() {

           }

           @Override
           public void onError(Throwable e) {
               Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onNext(Drawable drawable) {
               imageView.setImageDrawable(drawable);
           }
       });

    }



    /**  线程控制 —— Scheduler ********************************************************
     *
     *   Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
         Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
         Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。
            行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，
            因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
         Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，
           例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，
           否则 I/O 操作的等待时间会浪费 CPU。
         另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
     *
     *    subscribeOn(): 指定 subscribe() 所发生的线程, 即 Observable.OnSubscribe 被激活时所处的线程
     *    observeOn():   指定 Subscriber 回调 所运行在的线程, 叫做事件消费的线程
     * */


    public void testScheduler1(){
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())              // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        LogUtil.println(Thread.currentThread()+" number->" + integer);
                        Log.e("yll", Thread.currentThread()+" number->" + integer);
                    }
                });
    }

    /**同样加载图片的例子，在io线程加载drawable， 在主线程设置drawable*/
    public void setSchedulerImageView(final Activity activity, final int resId, final ImageView imageView){

        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) { //Schedulers.io()
                LogUtil.yll("加载drawable->" + Thread.currentThread());
                Drawable drawable = activity.getResources().getDrawable(resId);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
        .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
        .subscribe(new Subscriber<Drawable>() {

            @Override
            public void onStart() {
                LogUtil.yll("onStart->" + Thread.currentThread());
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Drawable drawable) {
                LogUtil.yll("设置drawable->" + Thread.currentThread());
                imageView.setImageDrawable(drawable);
            }
        });

    }

    /** 变换 将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列 *******************
     *
     *  Func1 它和 Action1 非常相似，也是 RxJava 的一个接口，用于包装含有一个参数的方法。
     *  区别在于 FuncX 有返回值， ActionX 没有返回值
     *
     *  flatMap() 和 map() 有一个相同点：它也是把传入的参数转化之后返回另一个对象，只是 flatMap() 中返回的是个 Observable 对象
     *
     *  flatMap() 也常用于嵌套的异步操作, 比如网络操作
     * */


    /**
     * 转换事件
     * map() 方法将一个 int 值转换层 drawable 对象然后返回
     * */
    public void testMap(final Activity activity, final int resId, final ImageView imageView){

        Observable.just(resId)         //输入参数类型 int
                .map(new Func1<Integer, Drawable>() {
                    @Override
                    public Drawable call(Integer integer) {               //参数类型 int
                        return activity.getResources().getDrawable(resId); //返回类型 Drawable
                    }
                })
                .subscribe(new Action1<Drawable>() {
                    @Override
                    public void call(Drawable drawable) {  //参数类型 Drawable
                        imageView.setImageDrawable(drawable);
                    }
                });

    }

    /**学生类 一个名字 多个课程*/
    class Student{
        String name;
        List<String> courses = new ArrayList<>();
    }

    /**初始化学生数据*/
    private ArrayList<Student> initStudents(){
        ArrayList<Student> students = new ArrayList<>();

        Student s0 = new Student();
        s0.name = "张三";
        s0.courses.add("语文");
        s0.courses.add("数学");
        s0.courses.add("英语");
        students.add(s0);

        s0 = new Student();
        s0.name = "李四";
        s0.courses.add("政治");
        s0.courses.add("历史");
        s0.courses.add("地理");
        students.add(s0);

        s0 = new Student();
        s0.name = "王五";
        s0.courses.add("物理");
        s0.courses.add("化学");
        s0.courses.add("生物");
        students.add(s0);

        return students;
    }

    /**输出学生所有课程  使用 flatMap 1对多的转换*/
    public void testFlatMap(){

        ArrayList<Student> students = initStudents();

        /** 不用 flatMap 时可以这样实现*/
//        Observable.from(students)
//                .subscribe(new Action1<Student>() {
//                    @Override
//                    public void call(Student student) {
//                        LogUtil.println("名字："+student.name);
//                        for (String s : student.courses){
//                            LogUtil.println("课程：" + s);
//                        }
//                    }
//                });

        /** 使用 flatMap 这样实现 将一个 student 转换成多个 string*/
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<String>>() {
                    @Override
                    public Observable<String> call(Student student) {
                        LogUtil.println("名字："+student.name);
                        return Observable.from(student.courses);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.println("课程：" + s);
                    }
                });


        // 不建议开发者自定义 Operator 来直接使用 lift()，
//        Observable observable = Observable.create(new Observable.OnSubscribe() {
//            @Override
//            public void call(Object o) {
//
//            }
//        });
//
//        observable.lift(new Observable.Operator<String, Integer>() {
//
//            @Override
//            public Subscriber<? super Integer> call(Subscriber<? super String> subscriber) {
//                return null;
//            }
//
//        });

    }

    /**
     * compose: 对 Observable 整体的变换
     *
     * 除了 lift() 之外， Observable 还有一个变换方法叫做 compose(Transformer)。
     * 它和 lift() 的区别在于， lift() 是针对事件项和事件序列的，而 compose() 是针对 Observable 自身进行变换。
     */

    private class LiftAllTransformer implements Observable.Transformer<String, String>{
        @Override
        public Observable<String> call(Observable<String> integerObservable) {
            return integerObservable.map(new Func1<String, String>() {
                @Override
                public String call(String s) {   //一次变换
                    return s + " 第一次变换";
                }
            })
            .map(new Func1<String, String>() {
                @Override
                public String call(String s) {   //2次变换
                    return s + " 第二次变换";
                }

            })
            .map(new Func1<String, String>() {      //3次变换
                @Override
                public String call(String s) {
                    return s + " 第三次变换";
                }
            });
        }
    }

    /** compose 将多次变换封装到一起*/
    public void testCompose(){
        observable.compose(new LiftAllTransformer()).subscribe(subscriber);
    }



    /**
     * 线程控制：Scheduler (二)
     * 线程的自由控制 如果有多次切换线程的需求，只要在每个想要切换线程的位置调用一次 observeOn() 即可
     *
     * 当使用了多个 subscribeOn() 的时候，只有第一个 subscribeOn() 起作用。
     *
     * */

    public void testScheduler2(){

        Observable.just(1, 2)             // IO 线程，由 subscribeOn() 指定
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<Integer, String>() {  // 新线程，由 observeOn() 指定
                    @Override
                    public String call(Integer integer) {
                        LogUtil.println("第一次变换线程->" + Thread.currentThread());
                        return String.valueOf(integer);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() { // IO 线程，由 observeOn() 指定
                    @Override
                    public String call(String s) {
                        LogUtil.println("第二次变换 线程->" + Thread.currentThread());
                        return s;
                    }
                })
                .observeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.println(s + " 线程->"+Thread.currentThread());
                    }
                });

    }

    /**
     * doOnSubscribe()
     *
     * onStart() 由于在 subscribe() 发生时就被调用了，因此不能指定线程，而是只能执行在 subscribe() 被调用时的线程。
     *
     * 与 Subscriber.onStart() 相对应的，有一个方法 Observable.doOnSubscribe() 。
     * 它和 Subscriber.onStart() 同样是在 subscribe() 调用后而且在事件发送前执行，
     * 但区别在于它可以指定线程。默认情况下， doOnSubscribe() 执行在 subscribe() 发生的线程；
     * 而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。
     * */

    public void testDoOnSubscribe(){

        Observable.just(1, 2)
                    .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {  //运行在 computation 线程
                    @Override
                    public void call() {
                        LogUtil.println("doOnSubscribe Thread->" + Thread.currentThread());
                    }
                })
                .subscribeOn(Schedulers.computation())  // doOnSubscribe 指定 computation 线程
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onStart() {
                        /** onStart() 由于在 subscribe() 发生时就被调用了，因此不能指定线程，而是只能执行在 subscribe() 被调用时的线程*/
                        LogUtil.println("onStart Thread->" + Thread.currentThread());
                    }

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(Integer integer) { //执行在 newThread
                        LogUtil.println("onNext Thread->"+Thread.currentThread());
                    }
                });


    }

    /** 其他应用
     * 1、与 Retrofit 的结合
     * 2、RxBinding 去抖动  https://github.com/JakeWharton/RxBinding
     * 3、各种异步操作
     * 4、RxBus 使用 RxJava 实现了 EventBus
     * */

}
