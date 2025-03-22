package org.schlunzis.vigilia.cli.ui;

public class SpinnerAnimation extends LoopingAnimation {

    public SpinnerAnimation(String prefix) {
        this(prefix, 500);
    }

    public SpinnerAnimation(String prefix, int frameDelay) {
        super(prefix, new String[]{"|", "/", "-", "\\"}, frameDelay);
    }

}
