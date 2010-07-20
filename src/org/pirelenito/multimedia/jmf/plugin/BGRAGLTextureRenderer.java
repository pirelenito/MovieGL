package org.pirelenito.multimedia.jmf.plugin;

import java.awt.Component;
import java.awt.Rectangle;
import java.nio.IntBuffer;

import javax.media.Buffer;
import javax.media.Control;
import javax.media.Format;
import javax.media.ResourceUnavailableException;
import javax.media.format.RGBFormat;
import javax.media.format.VideoFormat;
import javax.media.opengl.GL;
import javax.media.renderer.VideoRenderer;

import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

/**
 * JMF OpenGL renderer to a texture.
 * Process the buffer data and renders to a BGRA texture
 * 
 * To use you must get it as a control and then use the getTexture method which is the movie texture 
 * 
 * @author Paulo Ragonha
 *
 */
public class BGRAGLTextureRenderer implements VideoRenderer, Control, IGLTextureRenderer {
	
	/**
	 * Target texture for the movie frames
	 */
	private Texture texture;
	
	/**
	 * Buffer containing the converted pixels ready to be ploted
	 */
	private IntBuffer byteBuffer;
	
	/**
	 * Flag indicating if the buffer was updated
	 */
	private boolean byteBufferUpdated;
	
	private int height;
	private int width;

	/**
	 * Creates the textures based on the byteBuffer data
	 * 
	 * @param gl
	 * @param byteBuffer
	 * @param width
	 * @param height
	 * @return
	 */
	private Texture createTexture(GL gl, IntBuffer byteBuffer, int width, int height) {
				
		// creating the texture JOGL way
		Texture texture = TextureIO.newTexture(new TextureData (GL.GL_RGBA8,width, height, 0, GL.GL_BGRA, GL.GL_UNSIGNED_INT_8_8_8_8_REV, false, false, true, byteBuffer, null));
		texture.enable();

		texture.setTexParameteri( GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST );
		texture.setTexParameteri( GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST );
		
		return texture;
	}
	
	/**
	 * Tries to render the movie
	 * 
	 * @return success. (resources might not be ready) don't imply error, simply that the texture was updated
	 */
	public boolean render(GL gl) {
		
		if (byteBuffer == null)
			return false;
		
		synchronized (byteBuffer) {

			if (!byteBufferUpdated) 
				return false;
				
			byteBufferUpdated = false;
			
			if (texture == null) {
				texture = createTexture(gl, byteBuffer, width, height);	
			} 
			else {
				texture.bind();		
				gl.glTexSubImage2D(texture.getTarget(), 0, 0, 0, width, height, GL.GL_BGRA, GL.GL_UNSIGNED_INT_8_8_8_8_REV, byteBuffer);
			}
			
			return true;
		}
	}
	
	/**
	 * @return the movie texture
	 * @throws Exception no render call wore yet performed
	 */
	public Texture getTexture () throws Exception {
		if (texture == null)
			throw new Exception("No render calls wore performed, texture not yet initialized.");
		
		return texture;
	}
	
	public int process(Buffer buffer) {
		
		// byteBuffer initialization
		if (byteBuffer == null) {
			byteBuffer = BufferUtil.newIntBuffer(buffer.getLength());
		
			width = ((VideoFormat) buffer.getFormat()).getSize().width;
			height = ((VideoFormat) buffer.getFormat()).getSize().height;
		}
		
		// byteBuffer processing
		synchronized (byteBuffer) {
			
			byteBuffer.rewind();
			
			byteBuffer.put((int[])buffer.getData());
			
			byteBuffer.rewind();
			
			byteBufferUpdated = true;
		}
		
		return BUFFER_PROCESSED_OK;
	}
	
	public Format[] getSupportedInputFormats() {
		// RGB is the easy one to work
		return new Format[] { new RGBFormat() };
	}
	
	public Format setInputFormat(Format format) {
		return format;
	}
	
	public String getName() {
		return "BGRA OpenGL Renderer to Texture";
	}
	
	public Object getControl(String control) {
		if( control.equals("javax.media.renderer.VideoRenderer") || 
			control.equals("org.pirelenito.multimedia.jmf.plugin.IGLTextureRenderer") )
			return this;
		
	    return null; 
	}
	
	public Object[] getControls() {
		Object[] obj = { this }; 
	    return obj; 
	}
	
	public Component getControlComponent() {
		return null;
	}
	
	public void open() throws ResourceUnavailableException {
	}
	
	public void reset() {
	}
	
	public void start() {
	}
	
	public void stop() {
	}
	
	public void close() {
	}
	
	public Rectangle getBounds() {
		return null;
	}
	
	public Component getComponent() {
		return null;
	}
	
	public void setBounds(Rectangle rectangle) {
	}

	public boolean setComponent(Component arg0) {
		return false;
	}

}
