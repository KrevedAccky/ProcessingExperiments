import processing.core.PApplet;

class ParallaxPoints {
    private PApplet applet;
    boolean b[][];
    float mpfactor;
    int shx;
    int shy;

    ParallaxPoints(PApplet applet, float pfactor, float sparsity) {
        this.applet = applet;
        mpfactor = pfactor;
        shx = (int) (applet.width * pfactor);
        shy = (int) (applet.height * pfactor);
        b = new boolean[applet.height + 2 * shy][applet.width + 2 * shx];
        for (int i = 0; i < applet.height + 2 * shy; i++) {
            for (int j = 0; j < applet.width + 2 * shx; j++) {
                b[i][j] = (applet.random(1) > sparsity);
            }
        }
    }

    public void drawPoints() {
        float t = applet.frameCount;
        t /= 360; t *= PApplet.TWO_PI;
        float r = PApplet.sin(1.75f * t);
        float x = r * PApplet.cos(t);
        float y = r * PApplet.sin(t);
        x = applet.width / 2 + applet.width / 2 * x * 0.3f;
        y = applet.height / 2 + applet.height / 2 * y * 0.3f;

        int shiftx = shx - (int) ((x - applet.width / 2) * mpfactor);
        int shifty = shy - (int) ((y - applet.height / 2) * mpfactor);
        for (int i = 0; i < applet.height; i++) {
            for (int j = 0; j < applet.width; j++) {
                if (b[i + shifty][j + shiftx]) {
                    applet.pushStyle();
                    if (applet.random(1) > 0.999f) {
                        applet.strokeWeight(2);
                    }
                    applet.point(j, i);
                    applet.popStyle();
                }
            }
        }
    }
}
