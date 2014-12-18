import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

/**
* Created by kreved on 17/12/14.
*/
class Nyan {
    private PApplet applet;
    final int STATE_HIDDEN = 0;
    final int STATE_DISPLAY = 1;
    final int STATE_FADE_OUT = 2;
    PImage img;
    int state;
    PVector origin;
    int fadeStart;
    final int fadeLength = 15;
    boolean respawn = true;
    int respawnTime;
    final Rct respawnArea = new Rct();

    Nyan(PApplet applet, int pos_x, int pos_y) {
        this.applet = applet;
        origin = new PVector(pos_x, pos_y);
        img = applet.loadImage("res/nyan.png");
        state = STATE_HIDDEN;
    }

    public void setRespawnArea(int x, int y, int w, int h) {
        respawnArea.origin.x = x;
        respawnArea.origin.y = y;
        respawnArea.size.x = w;
        respawnArea.size.y = h;
    }

    public void update() {
        switch (state) {
            case STATE_HIDDEN:
                if (respawn && applet.millis() > respawnTime) {
                    show();
                }
                break;
            case STATE_DISPLAY:
                drawNyan();
                break;
            case STATE_FADE_OUT:
                drawFade();
                break;
        }
    }

    public void show() {
        state = STATE_DISPLAY;
    }

    public void fade_out() {
        if (state == STATE_DISPLAY) {
            fadeStart = applet.frameCount;
            state = STATE_FADE_OUT;
        }
    }

    public void hide() {
        respawnTime = applet.millis() + (int) (applet.random(0, 0.5f) * 1000);
        state = STATE_HIDDEN;
    }

    public boolean is_hidden() {
        return state == STATE_HIDDEN;
    }

    public boolean is_showing() {
        return state == STATE_DISPLAY;
    }

    public boolean containsPoint(int x, int y) {
        return (x > origin.x - img.width / 2 &&
                x < origin.x + img.width / 2 &&
                y > origin.y - img.height / 2 &&
                y < origin.y + img.height / 2
        );
    }

    public void drawFade() {
        int fadeProgress = (applet.frameCount - fadeStart) % fadeLength;
        float s = fadeProgress / (float) fadeLength;
        applet.pushStyle();
        applet.pushMatrix();
        applet.imageMode(PConstants.CENTER);
        applet.translate(origin.x, origin.y);
        applet.scale(1 + 3 * s, 1 + 3 * s);
        applet.tint(255, 255 * (1 - s));
        applet.image(img, 0, 0);
        applet.popMatrix();
        applet.popStyle();
        if (fadeProgress == fadeLength - 1) {
            hide();
            origin.x = applet.random(respawnArea.origin.x, respawnArea.origin.x + respawnArea.size.x);
            origin.y = applet.random(respawnArea.origin.y, respawnArea.origin.y + respawnArea.size.y);
        }
    }

    public void drawNyan() {
        int d = (applet.frameCount % 60 >= 30 ? 2 : 0);
        applet.pushMatrix();
        applet.imageMode(PConstants.CENTER);
        applet.translate(origin.x + d, origin.y + d);
        applet.pushMatrix();
        applet.pushStyle();
        applet.translate(img.width / 2 - 20, -img.height / 2);
        applet.scale(0.16f, 0.12f);
        drawRainbow();
        applet.popStyle();
        applet.popMatrix();
        applet.image(img, 0, 0);
        applet.popMatrix();
    }

    private void drawRainbow() {
        boolean f = (applet.frameCount % 10) < 5;
        float w = 0;
        float h = 0;
        float d = 10;
        float l = 100;
        float hl = 40;

        applet.pushStyle();
        applet.noStroke();
        applet.colorMode(PConstants.HSB);
        int[] colors = {0, 25, 45, 90, 135, 180};
        for (int i = 0; i < 6; i++) {
            int hue = colors[i];
            applet.fill(hue, 255, 255);
            applet.pushMatrix();
            applet.translate(0, hl * i);
            for (int j = 0; j < 4; j++) {
                float dd = 0;
                float dw = 0;
                if(j % 2 == 0) dd = d;
                if(f) {
                    dd = d - dd;
                    dw = 15;
                }
                applet.rect(w + j * l + dw, h + dd, l + 1, hl);
            }
            applet.popMatrix();
        }
        applet.popStyle();
    }
}
