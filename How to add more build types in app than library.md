#[How to add more build types in app than library](https://stackoverflow.com/questions/49286743/how-to-add-more-build-types-in-app-than-library)

```java
buildTypes {
    release {
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }
    debug {
        applicationIdSuffix '.debug'
    }
    staging {
        initWith release
        applicationIdSuffix '.staging'
        matchingFallbacks = ['release']
    }
}
```

You'll need to specify `matchingFallback` with the Android Gradle Plugin 3.0.0 for the plugin to know which fallback build type of library to use when being compiled with app code in case a certain build type defined in your app is not found in library.