LOCAL_LDLIBS is always ignored for static libraries

Apart from that, the warning is perfectly normal. You should use LOCAL_EXPORT_LDLIBS if you want to export dependent system libraries for your static library. LOCAL_LDLIBS is only used when building a shared library or executable.







openssl 1.0在64位机器上崩溃的原因

用开源rsa算法的时候,还要注意,那个年代的人把long当4bytes用,如今 放在64位的机器上,就会死循环啊多悲催....因为有个循环里让一个4bytes做递减....64位机上long是8bytes,这个循环进去后个把 小时都出不来....所以要注意下哦....同理对于所有年代久远的开源库都得注意下...