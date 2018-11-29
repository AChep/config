# config
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/612ab41a8b9444cd96ddb0543df1415e)](https://app.codacy.com/app/AChep/config?utm_source=github.com&utm_medium=referral&utm_content=AChep/config&utm_campaign=Badge_Grade_Dashboard)
[![Download](https://api.bintray.com/packages/achep/maven/config/images/download.svg)](https://bintray.com/achep/maven/config/_latestVersion) [![Build Status](https://travis-ci.org/AChep/config.svg?branch=master)](https://travis-ci.org/AChep/config) [![Code Climate](https://codeclimate.com/github/AChep/config/badges/gpa.svg)](https://codeclimate.com/github/AChep/config)

Utility library for Android with Kotlin to help you to create and manage simple settings of application. 

Download
----------------
Gradle:
```groovy
repositories {
  jcenter()
}

dependencies {
  implementation 'com.artemchep.config:config:${latestVersion}'
}
```

How to use
----------------
Firstly, create your configuration class based on `Config`, for example:
``` kotlin
object Cfg : SharedPrefConfig("cfg") {  
    const val KEY_INT = "my_int_key"  
    const val KEY_STRING = "my_string_key"  
  
    var intProperty by configDelegate(KEY_INT, 0)
      
    var stringProperty by configDelegate(KEY_STRING, "")  
}
```
Then, init it on application create:
``` kotlin
class App : Application() {  
    override fun onCreate() {  
        super.onCreate()  
        Cfg.init(this)  
    }  
}
```
That's all!  

#### Read value
``` kotlin
val value = Cfg.intProperty + 1
```

#### Write value
``` kotlin
Cfg.edit(context) { 
    Cfg.intProperty = 100
    Cfg.stringProperty = ""
}
```

#### Observe changes
``` kotlin
val observer = object : Config.OnConfigChangedListener<String> {
    override fun onConfigChanged(keys: Set<String>) {
        // Check if the keys include your 
        // key and update something.
    }
}

Cfg.observe(observer)
// Do not forget to unregister it later on
// by calling:
// Cfg.removeObserver(observer)
```

#### Sample project
You may want to check a [sample project][4] for a working example.

Restrictions
----------------
1. Property can not be `null`-able.
⋅⋅⋅_Use empty String for this_ 

Report a bug or request a feature
----------------
Before creating a new issue please make sure that same or similar issue is not already created by checking [open issues][2] and [closed issues][3] *(please note that there might be multiple pages)*. If your issue is already there, don't create a new one, but leave a comment under already existing one.

Checklist for creating issues:

- Keep titles short but descriptive.
- For feature requests leave a clear description about the feature with examples where appropriate.
- For bug reports leave as much information as possible about your device, android version, etc.
- For bug reports also write steps to reproduce the issue.

[Create new issue][1]

Versioning
----------------
For transparency in a release cycle and in striving to maintain backward compatibility, a project should be maintained under the Semantic Versioning guidelines. Sometimes we screw up, but we should adhere to these rules whenever possible.

Releases will be numbered with the following format: `<major>.<minor>.<patch>` and constructed with the following guidelines:
- Breaking backward compatibility bumps the major while resetting minor and patch
- New additions without breaking backward compatibility bumps the minor while resetting the patch
- Bug fixes and misc changes bumps only the patch

For more information on SemVer, please visit http://semver.org/.

Build
----------------
Clone the project and come in:

``` bash
$ git clone git://github.com/AChep/config.git
$ cd config/
```

[1]: https://github.com/AChep/config/issues/new
[2]: https://github.com/AChep/config/issues?state=open
[3]: https://github.com/AChep/config/issues?state=closed
[4]: https://github.com/AChep/config/tree/master/sample
