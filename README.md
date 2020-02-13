[![](https://jitpack.io/v/Mugambi-Ian/Widget-Pack.svg)](https://jitpack.io/#Mugambi-Ian/Widget-Pack)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
# Widget Pack
Widget pack is a UI/UX library designed for android developers who want develop unique, reliable and responsive designs. The library includes the following:  
* [Progress Bar](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#progress-bar)  
* [Num Pad](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#num-pad)  
* [Pin Indicator](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#pin-indicator)  
* [Password Field](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#password-field)  
* [Text Pad](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#text-pad)  
* [ImageView](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#imageview)  
* [Day Picker](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#preview-5)  
* [Animations](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#animations)  

##### Adding to project 
#### Gradle
Step 1. Add the code below in your project/root build.gradle.  

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}  
  
Step 2. Add the code below to your app build.gradle.

	dependencies {
		...
	        implementation 'com.github.Mugambi-Ian:Widget-Pack:1.0.0'
	}  
	
## Progress Bar
#### Preview
![](https://github.com/Mugambi-Ian/Widget-Pack/raw/master/Widgets/Preview/pb_black.gif)	![](https://github.com/Mugambi-Ian/Widget-Pack/raw/master/Widgets/Preview/pb_yellow.gif)
### Usage
* To use this widget add this code to your layout xml file.
```xml
<nenecorp.widgets.ProgressBar  
	android:id="@+id/_progressBar"  
	android:layout_width="wrap_content"  
	android:layout_height="wrap_content"/>
```  
* To change the progress bar color, add color resource "progressColor" to res/values/colors.xml .  
```xml 
<resources>
    ...
    <color name="progressColor">#000000</color>
    ...
</resources>
```  
* To use this widget as a timer, add this code to your activity file.
##### Java
```java 
ProgressBar progressBar = findViewById(R.id._progressBar);
progressBar.newTimer(3000, new ProgressBar.OnComplete() {
	@Override
	public void onComplete() {
		//onComplete
	}
});
```
##### Kotlin
```kotlin 
val progressBar = findViewById<ProgressBar>(R.id._progressBar)
progressBar.newTimer(3000) {
	//onComplete
}

```  
method "newTimer" has a parameter for the timer duration in miliseconds.  
* To use this widget as a progress bar, add this code to your activity class.
##### Java
```java
ProgressBar progressBar = findViewById(R.id._progressBar);
progressBar.initializeProgress(100, 0, new ProgressBar.OnComplete() {
	@Override
	public void onComplete() {
	// onComplete
	}
});
```
##### Kotlin
```kotlin
val progressBar = findViewById<ProgressBar>(R.id._progressBar)
progressBar.initializeProgress(100, 0) 	{ 
	// onComplete
	}
```
method "initializeProgress" has a parameter for max and current progress.  
* To update progress use method "updateProgress".  
##### Java
```java
progressBar.updateProgess(20);
```
##### Kotlin
```kotlin
progressBar.updateProgess(20)
```
* The onComplete method is called when the timer runs out.
## Num Pad
#### Preview
## Pin Indicator
#### Preview
## Password Field
#### Preview
## Text Pad
#### Preview
## ImageView
#### Preview 
## Day Picker
#### Preview
## Animations
