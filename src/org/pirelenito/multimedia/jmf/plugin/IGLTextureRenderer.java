package org.pirelenito.multimedia.jmf.plugin;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;

/**
 * Interface for different renderers
 * 
 * @author Paulo Ragonha
 */
public interface IGLTextureRenderer {

	/**
	 * @return the movie texture
	 * @throws Exception no render call wore yet performed
	 */
	public Texture getTexture () throws Exception;
	
	/**
	 * Tries to render the movie
	 * 
	 * @return success. (resources might not be ready) don't imply error, simply that the texture was updated
	 */
	public boolean render (GL gl);
	
}
