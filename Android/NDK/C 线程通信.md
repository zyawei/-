# C 线程通信

method `a()` wait for method `b()` be called

```c
typedef struct AdvertisingId
{
    bool limit;
    const char * id ;
    int idLength = -1;
    mtx_t mtx;// 一个互斥
    cnd_t cnd;               // 一个条件变量
} AdId;

AdvertisingId adid;
void a(){
   // wait adid be fill
    if (mtx_init(&adid.mtx, mtx_plain) != thrd_success) {
        return -2;
    }
    cnd_init(&adid.cnd);

    mtx_lock(&adid.mtx);

    struct timespec ts{};
    clock_gettime(CLOCK_REALTIME, &ts);
    ts.tv_sec += 3;
    LOGE("clock_gettime : tv_sec=%ld", ts.tv_sec);

    while (adid.idLength < 0) {
        int r = cnd_timedwait(&adid.cnd, &adid.mtx, &ts);
        if (r == thrd_error) {
            return -3;
        } else if (r == thrd_timedout) {
            return -4;
        }
    }

    mtx_unlock(&adid.mtx);

    mtx_destroy(&adid.mtx);
    cnd_destroy(&adid.cnd);
}

void b(){
		
    mtx_lock(&adid.mtx);

    adid.id = idChar;
    adid.idLength = idLen;
    adid.limit = false;

    cnd_broadcast(&adid.cnd);
    mtx_unlock(&adid.mtx);
}

```



[C语言，生产-消费](http://c.biancheng.net/view/434.html)