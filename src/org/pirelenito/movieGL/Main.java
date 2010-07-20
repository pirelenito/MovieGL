package org.pirelenito.movieGL;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.pirelenito.multimedia.jmf.MoviePlayer;
import org.pirelenito.multimedia.jmf.plugin.IGLTextureRenderer;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;

/**
 * Simple application to open a movie file and render it using JMF + JOGL + FOBS4JMF
 * Check my site for more info: http://pirelenito.org
 * 
 * @author Paulo Ragonha (Nito)
 */
public class Main implements WindowListener, GLEventListener {

	/**
	 * The window witch will hold the render
	 */
	Frame frame;
	
	/**
	 * OpenGL canvas
	 */
	GLCanvas canvas;
	
	/**
	 * Used to do rendering calls at propper FPS.
	 */
	FPSAnimator animator;

	private boolean initialized = false;

	/**
	 * Renderer from which we get the texture
	 */
	private IGLTextureRenderer renderer;

	/**
	 * Here we play the movie!
	 */
	private MoviePlayer player;
	
	/**
	 * Used to draw the neat teapot
	 */
	private static GLUT glut = new GLUT();
	
	/**
	 * Get error strings!
	 */
	private static GLU glu = new GLU();
	
	/**
	 * @param args takes one argument: file to open and play.
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Specify the file to open!");
			return;
		}
		
		try {
			new Main(args[0]);
		} catch (Exception e) {
			System.err.println("Something got wrong!");
			e.printStackTrace();
		}
	}
	
	public Main(String movieFilename) throws Exception {

		// first thing is trying to open the movie
		player = new MoviePlayer (movieFilename);
		
		// we create a frame that will hold the rendering surface.
		frame = new Frame("Java + JOGL + JMF + Fobs4JMF + Teapot!");
		frame.setSize(640, 480);
		frame.setVisible(true);
		frame.addWindowListener(this);
		
		// create and attach the GL Canvas
		canvas = new GLCanvas();
		frame.add(canvas);
		frame.validate();

		// start the rendering!
		canvas.addGLEventListener(this);
		animator = new FPSAnimator(canvas, 30);
		animator.start();
		
		player.setLoop(true);
		player.play();
		renderer = player.getRenderer();
	}

	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		
		// we will need textures
		gl.glEnable(GL.GL_TEXTURE_2D);
		
		// Depth buffer
		gl.glClearDepth(1f);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glEnable(GL.GL_DEPTH_TEST);
	}
	
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		
		// for some odd reason, init() is not being called.
		if (!initialized )
			init(drawable);		

		// Clear color and depth buffers
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		// try to render the movie, and if succeed bind the texture
		if (renderer.render(gl))
			try {
				renderer.getTexture().bind();
			} catch (Exception e) {
				e.printStackTrace();
			}

		// rotating and rendering the teapot!
		gl.glRotatef(1, 0, 1, 0);
		glut.glutSolidTeapot(0.7);
		
		gl.glFlush();
		
		int error = gl.glGetError();
		if (error != GL.GL_NO_ERROR)
			System.out.println(glu.gluErrorString(error));
	}

	public void windowClosing(WindowEvent e) {
		animator.stop();
		System.exit(0);
	}
	
	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
		
	}

	public void windowDeiconified(WindowEvent e) {
		
	}

	public void windowIconified(WindowEvent e) {
		
	}

	public void windowOpened(WindowEvent e) {
		
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		
	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
	}
}
