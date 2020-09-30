build.gradle



```groovy
    flavorDimensions "default"

productFlavors {
    usb {
        dimension "default"
        applicationId "com.herofun.demo.usb"
        manifestPlaceholders = [API_MODE: "USB"]
    }
    spi {
        dimension "default"
        applicationId "com.herofun.demo.spi"
        manifestPlaceholders = [API_MODE: "SPI"]
    }

    uart {
        dimension "default"
        applicationId "com.herofun.demo.uart"
        manifestPlaceholders = [API_MODE: "UART"]
    }

}
<meta-data
            android:name="API_MODE"
            android:value="${API_MODE}"/>
              
               ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            API_MODE_NAME = applicationInfo.metaData.getString("API_MODE", "USB");
```





```java

task moveApk(type:Copy){
    doLast{
        println("moveApk")
        def dir = file('release')
        dir.listFiles().each {
            file->
                file.delete()
        }

        from('build/outputs/apk/spi')
        from('build/outputs/apk/usb')
        from('build/outputs/apk/uart')
        into('release')
        include("*.apk")
    }
}


Task diyTask = project.task(['type':Copy],'diyTask') {
    doLast {
        println("diyTask do last" )
        def dir = file('release')
        dir.listFiles().each {
            file->
                file.delete()
        }

        from('build/outputs/apk/release')

        into('release')
        include("*.apk")
    }
}

project.tasks.whenTaskAdded {Task task->
    if (task.name == 'assembleRelease'){
        task.dependsOn(diyTask)
    }
}

```



https://stackoverflow.com/questions/21434554/copying-apk-file-in-android-gradle-project