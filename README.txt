This is a simple application that tests movie playback under Java.
Developed by Paulo Ragonha at 2008.
Blog post regarding this implementation: http://blog.pirelenito.org/2008/08/java-movie-playback-jogl-fobs4jmf/

You might use this code as you wish, and I would appretiate if you quote me as a reference.

Bellow are all the dependecies to make this work.

= JOGL libraries =

 * Download it from https://jogl.dev.java.net/
 * Copy the JOGL's lib folder into this project's lib floder, naming it jogl.
 
= FOBS =

 * Download the bin for your O.S. from http://fobs.sourceforge.net/
 * copy all contents into this project's lib floder, naming it jmf.
 
= Installing the plugin =

You can do it under eclipse. Just needs to have all the dependecies worked out.

 * Create a new run configuration;
 * Set the main class as: JMFRegistry;
 * Start and go to the Plugins tab then Renderer;
 * Add the org.pirelenito.multimedia.jmf.plugin.RGBGLTextureRenderer;
 * Move it to the top of the list;
 * Push Commit and you are good to go!