# CargoLink
iTijuana Exerc

This is an example exercise app for android. To avoid giving more details or violating copyright rules will only explain how to build the app.

![test](https://user-images.githubusercontent.com/4595522/218427532-a3ced03c-0d62-4aa1-86e2-33b5cd630289.gif)


In order to build the app you can create a new android app on android studio using the "Empty Activity" template and kotlin as main language, 
this will be our base app where we will insert our files. If you want to match the original name, the name 
of the app is "CargoLink" and the package name is: "com.android.cargolink".

After creating the base app please download the zip files from this repo.

The resulting files from decompressing the downloades zip. Will be something similar to this: 
<img width="185" alt="image" src="https://user-images.githubusercontent.com/4595522/218409428-50b553b9-e37e-4c1d-b413-f9d5ce235e1f.png">

The source files are inside the "app/src/main/java/com/android/cargolink"

Inside of this route we have a collection of folders containing the different source files.
In this root folder we also have 3 Source files:
  CargoLinkApp.kt
  MainActivity.kt
  ShipmentViewModel.kt
  
Please copy/move this 2 files to your project root source folder:
  CargoLinkApp.kt
  ShipmentViewModel.kt
  
  You can do this by dragging from the folder and dropping them in you project sources root package inside android studio. 
  
For the MainActivity.kt file you should have one matching the name, please match the contents, if you used the same package name you should 
be able to copy and paste the whole file directly.

Moving back to the root folder we should be able to see 4 folders:
  data
  entities
  schedule
  util

To be able to copy these folders please create matching packages in your root source package, you should now have something like this: 
  <img width="304" alt="image" src="https://user-images.githubusercontent.com/4595522/218413004-a3137206-4c81-4b65-836f-85a0656ac89a.png">

Now we can move on to copy each folder files as we did with CargoLinkApp.kt and ShipmentViewModel.kt files, you can open each folder and drag 
all the files inside them to each corresponding package you recently created, refactor if needed.
  You should now have something like this:
    <img width="302" alt="image" src="https://user-images.githubusercontent.com/4595522/218413741-dc053f4a-397e-4c87-ba2e-7ed4130982c2.png">
    
Now moving back to folder "app\src\main\" we will find the file "Android Manifest".
We will open this file and match it with our manifest file inside android studio: <img width="163" alt="image" src="https://user-images.githubusercontent.com/4595522/218414338-16ef1531-113d-4466-b89e-e0738198252c.png">
  The main changes are: 
    The line/s "<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
        android:maxSdkVersion="28"/>"
    and the line: "android:name=".CargoLinkApp"" inside the "<application>" tag
    
In this same "app\src\main\" folder we can see the "res" folder, we also need some files from there, we will open it and we will see something like this: 
    <img width="146" alt="image" src="https://user-images.githubusercontent.com/4595522/218415425-0bccecb1-1392-4f61-abef-ea43766c2ee2.png">

Please open the "layout" folder and we will find 2 files:
  activity_main.xml and schedule_item.xml
If you open your "res/layout" folder in your project you will find a similar "activity_main.xml" file, please match the contents.
For the "schedule_item.xml" file please drag and drop the file in this same folder inside the project. Refactor if needed.
  You should have something like this: <img width="165" alt="image" src="https://user-images.githubusercontent.com/4595522/218416743-db06b7b0-7d1d-4eca-a98e-62f1a7d31040.png">

Moving now to the "res/values" folder we will find 4 files:
  <img width="133" alt="image" src="https://user-images.githubusercontent.com/4595522/218416966-99b29de8-f566-4f7b-a47c-2693d7907aa1.png">
  
  Checking your "res/value" folder in your project you should have a "strings.xml" and maybe some other file.
  Please match the content of the "strings.xml" in the folder with the one on your project.
  From this folder also please copy/move the "dimen.xml" file to your project. Refactor if needed
  You should now have something like this: <img width="185" alt="image" src="https://user-images.githubusercontent.com/4595522/218418953-fec5503c-94c8-4fb8-b836-d1a2807070fa.png">

Now we will move to the build.app files from gradle:
  First the app level gradle build file. To find this file in the downloaded files we need to go to the "\app" folder, inside we will find 
  a "build.gradle" file. Please open this file and match it with the app level build.gradle file in your android studio project: <img width="254" alt="image" src="https://user-images.githubusercontent.com/4595522/218419157-79b264d5-9fb5-463a-a344-af4fc39061b0.png">
  Please pay special attention to the "plugins" section, "compileSdk 33", "targetSdk 33", "viewBinding" configuration lines, the dependencies section and the "kapt" 
  configuration lines.

For the project level build.app file we will now move to the "\" folder (or "\CargoLink-master"), here we will have a "build.gradle" file along with 
some other that we will not use this time.
  Please open this "build.gradle" file and match it with the project level "build.gradle" file in your android studio project: <img width="255" alt="image" src="https://user-images.githubusercontent.com/4595522/218420752-2f09442e-bf0e-496a-b661-cc2aaa9a74ec.png">
    For this file we should only need to add the line "id 'com.google.dagger.hilt.android' version '2.44' apply false" in "plugins" section

After you have matched these two files "sync" your project with gradle. And that's it!, you should be able to run the project succesfully now!

Just one more thing, you will need to copy the "\CargoLink-master\data.json" and/or the "\CargoLink-master\data_short.json" file to the emulator in 
which you run the app, you can do so using the file explorer and draggin and dropping this file to it: <img width="295" alt="image" src="https://user-images.githubusercontent.com/4595522/218422705-ec7229eb-a81b-4bde-8e69-95a106697881.png">

  I recommend using the "\CargoLink-master\data_short.json" as the app will delay a lot to process the information with the original "data.json" file
 
As an extra step you can copy the test clases located in "CargoLink-master\app\src\test\java\com\android\cargolink\util" and copy/move them to 
the project and you should have something like this: <img width="266" alt="image" src="https://user-images.githubusercontent.com/4595522/218423124-40b49d79-9243-459d-9243-fee323ed9f16.png">

After this you should be able to also run the test's.
