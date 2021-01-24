
FOR RUNNING THE PROJECT
    Need to use JDK 9 for Robolectric unit test cases.

Please note:
1. I had removed items whose all data are null. So if I am getting fact image, description as well as title - "null"
then am not showing that item on screen

2. Changing orientation will preserve item position.

3. I had use Robolectric library and this library has issue right now which
could be fixed by using JaCoco as Code Coverage inside android studio.

If you don't set it then one test will fail inside CanadaFactsViewModelTest only if you run
test coverage for all modules combinely if you just run test for all modules without
coverage then it will not fail. This issue is logged inside Robolectric library and
you can check it here
https://github.com/robolectric/robolectric/issues/3023

So if you want to test code coverage please check it separately. (One file at a time)


4. Images are coming of improper size from server. To display them completely I am making them centre fit.
Because of this if it's not coming of proper width then it will be shown as it's.

The second option was to force it to be shown as rectangle doing so will need to crop the images maintaining the aspect ratio henceforth image detail will lost. Hence, I had not used this option.

5. Few image URLs are coming null or are invalid when checked from browser. Hence for those I am showing placeholder image.


ARCHITECTURE USED
1. Kotlin with MVVM clean architecture, Lifecycle Extensions
2. Use Cases, Navigator, Couroutine, Extensions, Dagger, Synthetic properties
3. I had used kotlin Scripts instead of Groovy
4. Robolectrick, Android X
5. App Compat, Constraint Layout, Coordinator Layout
6. Leak Canary, Kluent, Build Variants created with production, internal, dev environment.

7. The Application uses navigator architecture in which we had created one launcher activity
and all the new screens as per client will be navigated from this activity using navigator.

8.
I had split gradle files, string files into smaller files to make them simpler and all
are connected inside build.gradle.kts. To see libraries used go to Dependencies.kt


Unit test cases 100% test coverage for:-
  a. View Models
  b. Repositories
  C. Request and Response POJO.
  d. Use Cases

Cases covered 
1. Check if live data is updated during API fail, success and no internet.
2. Check that proper response with all attributes are obtained from server.

Note:
Normally application always contains login screen. That's why I had created one test to show that auto login code is working properly or not using below point.

3. Check if user is logged in then canada fact screen should be shown.

4. Check if user is not logged in then login screen should be shown.
5. “Check if user is login” - method is working properly or not.

6. Check if view model is updated from response model or not.

7. Check that repository is returning network failure when no connection.

8. Check that when response comes null than UI should work properly. (App should not crash).

9. Check that if there is any unhandled exception during API call than it's handled by try catch and app should not crash.


Points followed
1.Ingests a json feed from https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json
  •The feed contains a title and a list of rows DONE
. •You can use a third party json parser to parse this if desired. DONE. I had used retrofit API with Gson

2.Displays the content (including image, title and description) in a ListView DONE (Displayed it inside recycler view)

  •The title in the ActionBar should be updated from the json data. DONE

  •Each row should be dynamically sized to the right height to display its content-no clipping, no extraneous white-space etc.This means some rows will be larger than others. DONE



3.Loads the images lazily(don’t download them all at once, but only as needed). DONE by default happens inside Recycler View

4.Implement a refresh functionall owing the data & view to be updated. DONE

  •Use either a refresh button or pull down to refresh. DONE I had used pull down refresh

5.Should not block UI when loading the data from the json feed. DONE

6.Each row of the table should look roughly like the following image: DONE



Additional Guidelines Done
1.Use a Github repository to manage the source code. A clear Git history showing your process is required. Commit your changes to git in small chunks with meaningful comments.DONE

2. The app should target Android version 7.1 (v25). Don’t worry about backwards compatibility for this task. DONE


3.The list should scroll smoothly. As much data as possible should be cached.DONE

4.Comment your code where necessary. DONE

5.Polish your code as much as possible. DONE

6.Feel free to use any best-practice open-source libraries/examples you need, just be sure to give credit. Do not use beta versions of any libraries. DONE

7.Include at least two UI unit tests; one that asserts the state of the screen when set up with all data present, and one that asserts the state of the screen when in an error state.
DONE

8.Handle screen rotation efficiently. DONE I had designed separate UI for landscape to use more space available during landscape mode

