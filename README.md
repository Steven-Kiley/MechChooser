# MechChooser
Repository for mech-chooser application.

Hello and thanks for taking a look at Mech Chooser.

This is a simple stand alone application built using Java SE that chooses a random mech for the MechWarrior Online video game. 
This series of games has been a favorite of mine for a long time and I had quite a bit of fun creating this application. 
Unfortunately, palyer moddingis not allowed so this application cannot link up with the game. Rather, it merely intakes the users 
owned mechs and gives a few simple options for making a random choice. Here's how the application breaks down by package.

initializer package:
     This package is concerned with launching the application. It checks to see if there is a saved list of user owned mechs. If there is
     it launches the standard UI. If not, it fires up a simple web server and uses dynamic HTML pages to build the user's list and save it
     for future runs of the application. 
     
mechdata package:
     This encapsulates the backend of the application. It keeps track of everything needed for the modular choices. A mech is identified by
     its hashcode. The hashcode looks like this:
          Thousands: 1 for IS faction, 2 for Clan faction.
          Hundreds: 1 for Light weight class, 2 for Medium weight class, 3 for Heavy, 4 for Assault
          Tens: This number represents which mech it was in the order in which it was added. For example, the third IS Light would be 03.
     Putting it all together, we can say that the third added IS Light would have a HashCode of 1103. If we want a random IS Light we can 
     generate a random number between 1101 and 11XX where XX is the total number if IS Lights added. This allows the random choices to be 
     fully modular. We can choose any combination of faction and weight classes and get a random result from within those choices.
     
mircoserver package: 
     In the instance that the user has not run the program and input their mechs, this webserver will be spun off in a new thread
     and will listen on port 8080. A series of dynamic HTML pages will open with the users default browser and they will be guided 
     through inputing their mechs. The webserver has a counter that tracks the number of responses it receives from the user and shuts
     itself down after the final page has been navigated through.
     
serializer package:
     Simple code for writing user responses to a serialized file and reading them back out for use in running the application.
     
sounds package:
      A single Java file for playing sound files. Also contains the two .wav files that it will play. One for when the application launches
      and the other for when the single interactive button on the UI is pressed.
      
userinterface package:
     As the name implies, this package contains the code for the UI. It's realtively straightforward JFrame and JPanel stuff with some
     dynamically changing pictures on the right side of the window.
    
    
Everything else outside of those packages is just documentation files and pictures for use in that dynamically changing UI panel. 

Enjoy!
-Steven Kiley
