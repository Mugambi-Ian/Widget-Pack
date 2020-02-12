# Widget Pack
Widget pack is a UI/UX library designed for android developers who want develop unique, reliable and responsive designs. The library includes the following:  
[Progress Bar](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#progress-bar), [Num Pad](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#num-pad), [Pin Indicator](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#pin-indicator), [Password Field](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#password-field)  
[Text Pad](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#text-pad), [ImageView](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#imageview), [Day Picker](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#preview-5) and [Animations](https://github.com/Mugambi-Ian/Widget-Pack/blob/master/README.md#animations)  
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
* Add this code to your layout xml file.
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
</resources>
```
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
