# Java OpenGL Movie

This is a simple application that tests OpenGL movie playback with Java.

You can check it working in [this YouTube video](http://www.youtube.com/watch?v=-zh6yDyasSo);

**Important:** I no longer maintain this code, so use at your own will.

## Dependencies

Bellow are all the dependencies to make this work.

JOGL libraries:

 * Download it from [https://jogl.dev.java.net/](https://jogl.dev.java.net/)
 * Copy the JOGL's lib folder into this project's lib folder, naming it `jogl`.
 
FOBS:

 * Download the bin for your O.S. from [http://fobs.sourceforge.net/](http://fobs.sourceforge.net/)
 * Copy all contents into this project's lib folder, naming it `jmf`.
 
## Installing the plugin

You can do it under [Eclipse](https://www.eclipse.org). Just needs to have all the dependencies worked out.

 * Create a new run configuration;
 * Set the main class as: `JMFRegistry`;
 * Start and go to the Plugins tab then Renderer;
 * Add the `org.pirelenito.multimedia.jmf.plugin.RGBGLTextureRenderer`;
 * Move it to the top of the list;
 * Push Commit and you are good to go!
 
## License

You might use this code as you wish, and I would appreciate if you quote me as a reference.
 
## Author

Developed by [Paulo Ragonha](https://github.com/pirelenito) in 2008.

For more details in the implementation, check the [related blog post](http://blog.pirelenito.org/2008/08/java-movie-playback-jogl-fobs4jmf/). 
