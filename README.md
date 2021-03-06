# PepTalkPal

[Link to Google Play Store] (https://play.google.com/store/apps/details?id=owlslubic.peptalkapp)

####_Purpose, function, and design:_
The purpose of this app is to provide a resource for what to do when you’re feeling badly. It offers pep talks on demand - because sometimes it’s hard to reach out to friends and family, or to console yourself sufficiently in the moment. The app encourages you to keep a strategy of what to do when it’s hard to remember where to begin, and it aims to empower the user to help themselves -- not preaching how they should live their lives, but offering a platform where they can keep resources that help them manage stress/deal with general moods. By being customizable, the goal is to be actually useful for the user, but not just a blank slate which can be intimidating (this is “a starting place”).

This app is intended for use when a person is feeling stressed in the moment, and so I wanted the pep talks to be easily accessible, which is why a tap on the main screen launches a fragment that allows the user to easily browse through all their pep talks rather than needing to choose one specifically and bring it up. However, if they do know that they want a particular pep talk, they can easily access them by title from the Pep Talk List activity. I decided to pre-populate pep talks because I wanted to provide a template for the user to encourage them to write their own.
OnClick displays the full title and body, and it can be edited from there. Long click allows user to delete.

Again, because this app is intended for use when the user is in acute anxiety/panic/stress, I included a checklist of things that the user can do to try to control their symptoms, because it can be difficult to think of those things objectively in the moment. When you're freaking out, your first thought might not be "Hmm when was the last time I ate? Would getting my blood sugar up help me get over this?" Again, I populated it with suggestions, and a little notes section that explains why I chose that idea. OnClick displays the notes, and it can be edited from there. Long click allows user to delete.

For the same reason, I included the home screen widget, for discreet and immediate pep talks. The user can choose what text will be displayed from the main activity's overflow menu, and once added to the home screen they can adjust the size of the widget to make space for a big pep talk or just a little "go get 'em tiger!". The widget says "Emergency Pep Talk, and they can quickly tap the widget to display their chosen text, and tap the "I'm good, set it back" text to hide the text (for their eyes only).

In the navigation drawer, I included a link to more resources for if the user wants inspiration on what to include in their pep talks or checklist, and I have a little blurb at the bottom that encourages the user to reach out to their friends if pep talks just don't suffice, because it can be hard to reach out to friends for fear of feeling like a burden, or being embarrassed. That's why I figured the user could use a little nudge that is always present (in the nav drawer) rather than being hidden and needing to be specifically sought. The icons open the user's facebook app (if installed), default sms app, and email app (I figured those were the most common go-to platforms to reach out).

My main goal in the design process was to keep it SIMPLE, clean, and intuitive, because from user research I reasoned that if the point of the app is to help you manage stress, being confronted with a confusing design would defeat the purpose. That menans not needing too many taps to navigate around, and implementing gestural features where possible. I decided to keep all the features accessible from the navigation drawer, to use a clean and inoffensive color scheme, and to balance out all the text with nice little icons whenever possible. I always wanted to implement material design stuff, so I have a bottomsheet for the information that isn't really important enough to warrant its own activity, and I used a FAB menu to make that a little more exciting. 


####_Technologies:_
Firebase was used for storing user info and their specific pep talks and checklist items. I used the FirebaseUI library for the FirebaseRecyclerAdapter and the AuthUI features. I used a third-party library for my main activity FloatingActionMenu.

####_Unsolved problems and major hurdles:_
An unsolved problem in my app is that when I insert the pep talks and checklist items to pre-populate the app for a new user, I have it set so that it will insert it if a boolean in SharedPreferences asserts that it is the "FIRST_RUN". However, if you uninstall and reinstall, the SharedPreferences don't have the boolean and so the data is inserted again. I had to do it this way because I had two alternative ideas: one would be to have the peptalks and checklists readable to all users and then if they wanted to edit it it would be inserted especially into their child objects so that they would have write access, but because of the way I set up the database, by the time I had that idea it was no longer feasible. The other plan was a simple checkIfExists method that would see if the user id had been inserted into the database yet, and if not, it would add the info (because when I observed the database in the Firebase console, it didn't add the UID until you added the first child object of that). However, that jsut didn't work. So SharedPreferences was the way to go.

Smaller unsolved problem is that I cannot get my FloatingActionsMenu to work with the Snackbar in a coordinator layout -- I've tried to define a specific class for it's behavior, but I couldn't get it to work. Alas.

The biggest hurdle was just getting through this emotionally.
I had lots of plans for this app, lots of features I wanted to implement, but from the beginning I kept my focus on the core functionality of the app and I'm glad I did so. The result is that this is my MVP, and I look forward to tinkering and adding all the features I want. But honestly, the hardest part is trying not to be disappointed in myself for not magically banging out some awesome project. But I walk away with what really counts... the knowledge!

<p align="center">
  <img src="./Screenshots/Screenshot_20160912-163238.png" width="220" height="400" > 
  <img src="./Screenshots/Screenshot_20160912-174120.png" width="220" height="400" >
  <img src="./Screenshots/Screenshot_20160912-163245.png" width="220" height="400" > 
  <img src="./Screenshots/Screenshot_20160912-163304.png" width="220" height="400" >
</p>
