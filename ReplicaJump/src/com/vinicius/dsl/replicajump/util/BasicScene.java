package com.vinicius.dsl.replicajump.util;

import org.andengine.entity.scene.Scene;

public interface BasicScene {
	    abstract Scene LoadScene();
	    abstract void CleanScene();
	    abstract void BackPress();
	    abstract void OnPause();
	    abstract void OnResume();
}
