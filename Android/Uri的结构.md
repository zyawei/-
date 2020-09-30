### Uri的结构

`[scheme:]scheme-specific-part[#fragment]`

`[scheme:][//authority][path][?query][#fragment]`

`[scheme:][//host:port][path][?query][#fragment]`

```xml
<activity
    android:name=".SecondActivity"
    android:label="@string/title_activity_second">
    <intent-filter>
        <action android:name="android.qijian.schemeurl.activity" />
        <category android:name="android.intent.category.DEFAULT" />
        <data
            android:scheme="qijian"
            android:host="test.uri.activity" />
    </intent-filter>
</activity>
```

