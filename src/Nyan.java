import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

/**
* Created by kreved on 17/12/14.
*/
class Nyan {
    private Parallax parallax;
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

    Nyan(Parallax parallax, int pos_x, int pos_y) {
        this.parallax = parallax;
        origin = new PVector(pos_x, pos_y);
        img = parallax.loadImage("res/nyan.png");
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
                if (respawn && parallax.millis() > respawnTime) {
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
            fadeStart = parallax.frameCount;
            state = STATE_FADE_OUT;
        }
    }

    public void hide() {
        respawnTime = parallax.millis() + (int) (parallax.random(0, 0.5f) * 1000);
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
        int fadeProgress = (parallax.frameCount - fadeStart) % fadeLength;
        float s = fadeProgress / (float) fadeLength;
        parallax.pushStyle();
        parallax.pushMatrix();
        parallax.imageMode(PConstants.CENTER);
        parallax.translate(origin.x, origin.y);
        parallax.scale(1 + 3 * s, 1 + 3 * s);
        parallax.tint(255, 255 * (1 - s));
        parallax.image(img, 0, 0);
        parallax.popMatrix();
        parallax.popStyle();
        if (fadeProgress == fadeLength - 1) {
            hide();
            origin.x = parallax.random(respawnArea.origin.x, respawnArea.origin.x + respawnArea.size.x);
            origin.y = parallax.random(respawnArea.origin.y, respawnArea.origin.y + respawnArea.size.y);
        }
    }

    public void drawNyan() {
        int d = (parallax.frameCount % 60 >= 30 ? 2 : 0);
        parallax.pushMatrix();
        parallax.imageMode(PConstants.CENTER);
        parallax.translate(origin.x + d, origin.y + d);
        parallax.image(img, 0, 0);
        parallax.popMatrix();
    }
}
