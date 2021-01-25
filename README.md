# Currency_Exchange_Android_MVVM_Clean_WIth_Room


FOR RUNNING THE PROJECT
    Need to use JDK 9 for Robolectric unit test cases.

Please note:
1. Screen refresh timer is set as 10 minute. You can toggle it from IApiConstant file

2. I am using free plan for currency api. In free plan source currency is fixed. Henceforth as instructed inside API if currency data is unavaiable we need to calculate currency rate 
inside app. So I had created dummy currency list for unavailable currency and am showing it if user select any currency other than USD

3. I had use Robolectric library and this library has issue right now which
could be fixed by using JaCoco as Code Coverage inside android studio.

If you don't set it then one test will fail inside CurrencyFactsViewModelTest only if you run
test coverage for all modules combinely if you just run test for all modules without
coverage then it will not fail. This issue is logged inside Robolectric library and
you can check it here
https://github.com/robolectric/robolectric/issues/3023

So if you want to test code coverage please check it separately. (One file at a time)



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

3. Check if user is logged in then Currency list screen should be shown.

4. Check if user is not logged in then login screen should be shown.
5. “Check if user is login” - method is working properly or not.

6. Check if view model is updated from response model or not.

7. Check that repository is returning network failure when no connection.

8. Check that when response comes null than UI should work properly. (App should not crash).

9. Check that if there is any unhandled exception during API call than it's handled by try catch and app should not crash.


Points followed
 Exchange rates must be fetched from: https://currencylayer.com/documentation - Done
 Use free API Access Key for using the API  - Done
 User must be able to select a currency from a list of currencies provided by the API(for currencies that are not available, convert them on the app side. When converting, floating-point error is accpetable)  - Done
 User must be able to enter desired amount for selected currency  - Done
 User should then see a list of exchange rates for the selected currency  - Done
 Rates should be persisted locally and refreshed no more frequently than every 30 minutes (to limit bandwidth usage)  - Done
 Write unit testing  - Done

UI Suggestion:
 Some way to select a currency - Done
 Some text entry widget to enter the amount - Done
 A list/grid of exchange rates - Done
 It doesn’t need to be super pretty, but it shouldn’t be broken as well ;) - Done



What we're looking for:
 An App that meets the Functional Requirements above - Done
 Your coding style! Show us how you like to write your code - Done
 Architecture, how you've structured your code - Done
 Principles, how you belive code should be written- Done
 Qualities, how you guarantee your code is functioning - Done

