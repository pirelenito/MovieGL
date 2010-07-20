package org.pirelenito.multimedia.jmf;

import java.net.URL;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.Player;
import javax.media.Time;

import org.pirelenito.multimedia.jmf.plugin.IGLTextureRenderer;

/**
 * Interface to JMF player and has helper functions to work
 * with the GL renderer
 * 
 * @author Paulo Ragonha
 */
public class MoviePlayer implements ControllerListener  {
	
	/**
	 * JMF player
	 */
	private Player player;
	
	/**
	 * Loop flag
	 */
	private boolean loop;

	public MoviePlayer (String filename) throws Exception {
		Manager.setHint(Manager.PLUGIN_PLAYER, true);
		
		// open the file.
		player = Manager.createRealizedPlayer( new URL("file:" + filename) );
		player.addControllerListener(this);
		player.prefetch();
		
		// wait for it to be done.
		while(player.getTargetState() != Player.Prefetched );
	}
	
	/**
	 * @return GL texture renderer
	 */
	public IGLTextureRenderer getRenderer () {
		return (IGLTextureRenderer) player.getControl("javax.media.renderer.VideoRenderer");
	}

	public void play() {
		player.start();
		while(player.getTargetState() != Player.Started );
	}
	
	public void pause() {
		player.stop();
	}
	
	public void stop() {
		pause();
		rewind();
	}
	
	public void rewind() {
		player.setMediaTime(new Time(0));
	}
	
	/**
	 * Set player to auto loop
	 * @param loop
	 */
	public void setLoop (boolean loop) {
		this.loop = loop;
	}
	
	/**
	 * Check current loop condition
	 * @return
	 */
	public boolean isLoop () {
		return loop;
	}

	public void controllerUpdate(ControllerEvent event) {
		// this players is auto loop!
		if (event instanceof EndOfMediaEvent && loop) {
			rewind();
			play();
		}
	}

}
