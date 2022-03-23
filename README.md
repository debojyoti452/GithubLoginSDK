# Github Login SDK for Android (LightWeight)

[![](https://jitpack.io/v/debojyoti452/GithubLoginSDK.svg)](https://jitpack.io/#debojyoti452/GithubLoginSDK)

## Motivation BTS: :octocat:
**Reason Behind Building SDK** :- As all we know that Github SDK is already available in Firebase Auth SDK but it has limitation like using custom redirect url can't be used or setting scopes is hard. Firebase Auth comes with a lot of extras like analytics, etc.. So I need a solution which simply just do the auth and return me token. So as usual was looking for an efficient SDK that can help me with this simple Auth, but TBH not a single solution I able to find, all were complex and head scratching. Finally I created my own and made it open source. 

:fire: Light Weight SDK (No Extra Libs used)
---
## Installation
#### Project Root Level Gradle (Below 7.0)
```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
## OR
#### settings.gradle Level Gradle (Below 7.0)
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```
#### App Level Gradle
```gradle
dependencies {
	 implementation 'com.github.debojyoti452:GithubLoginSDK:${version}'
}
```
---
## AuthResult Model
- Response will return this Params as ==**AuthResult**== model
 ```kotlin
        val scope: String? = null,
        val accessToken: String? = null,
        val tokenType: String? = null,
        val errorDescription: String? = null,
        val error: String? = null
 ```
 ---
## Integrations
- Initiate Github Auth SDK in **Kotlin**
 ```kotlin
        private val githubAuth: GithubAuth by lazy {
            GithubAuth.Builder(GIT_CLIENT_ID, GIT_CLIENT_SECRET)
                .setActivity(this)
                .setOnSuccess {
                    // Success Response Fetched.
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
                .setOnFailed {
                    // Exceptions Catched.
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                .setScopes(listOf(Scopes.PUBLIC_REPO, Scopes.USER_EMAIL))
                .build()
        }
 ```
 - Initiate Github Auth SDK in **JAVA**
 ```java
        private GithubAuth githubAuth = new GithubAuth.Builder(GIT_CLIENT_ID, GIT_CLIENT_SECRET)
                .setActivity(this)
                .setOnSuccess(response -> {
                    Toast.makeText(JavaActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    return null;
                })
                .setOnFailed(error -> {
                    Toast.makeText(JavaActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    return null;
                })
                .setScopes(Arrays.asList(Scopes.PUBLIC_REPO, Scopes.USER_EMAIL))
                .build();
 ```
* #### Scopes Available **(Constants)**
 ```kotlin
        - Scopes.PUBLIC_REPO
        - Scopes.USER_EMAIL
        - Scopes.REPO
        - Scopes.GIST
 ```
* #### Response Listener 
 ```kotlin
       setOnSuccess {
            // Success Response Fetched.
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
        setOnFailed {
            // Exceptions Catched.
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
 ```
 
* ####  Setting Scopes - **[Accepts Arrays of Strings]**
 -- **Kotlin Code**
 ```kotlin
        setScopes(listOf(Scopes.PUBLIC_REPO, Scopes.USER_EMAIL)) 
 ```
-- **JAVA Code**
 ```java
        setScopes(Arrays.asList(Scopes.PUBLIC_REPO, Scopes.USER_EMAIL))
 ```
  * ####  Setting Custom Redirect URL (Optional Builder Method)
 ```kotlin
        setRedirectUrl("WILL ACCEPT ONLY THE URL USED IN YOUR GITHUB OAUTH APP CREATION")
 ```
 * #### Deep Link Observer **(Mandatory)**
    It will check when we will return the ==Auth Code== after ==Github Web OAuth== Successfully done.
 ```kotlin
        override fun onResume() {
            super.onResume()
            githubAuth.onDeepLinkInitializer()
        }
 ```
  * #### Deep Link Manifest **(Mandatory)**
 ```xml
    <activity
        android:name="Activity Name"
        android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
            
            <data android:scheme="http" />
            <data android:scheme="https" />
            
            // example: <data android:host="***.firebaseapp.com" />
            <data android:host="Redirect URL" />
        </intent-filter>
    </activity>
 ```
 ---
## Demo Links
* Success -> https://youtu.be/sYPK4LWFHio
* Failed -> https://youtu.be/2_73Z_LEW14
## IMPORTANT NOTES

### ==(Feel free to raise a PR)==

## Open Source Plugins used
| Library | README |
| ------ | ------ |
| HttpUrlConnection | [https://developer.android.com/reference/java/net/HttpURLConnection][PlDb] |
| GSON Serializer | [https://github.com/google/gson][PlGd] |
| Android STD Libs | [https://developer.android.com/kotlin/first][PlMe] |
