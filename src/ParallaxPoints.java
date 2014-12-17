/**
* Created by kreved on 17/12/14.
*/
class ParallaxPoints {
    private Parallax parallax;
    boolean b[][];
    float mpfactor;
    int shx;
    int shy;

    ParallaxPoints(Parallax parallax, float pfactor, float sparsity) {
        this.parallax = parallax;
        mpfactor = pfactor;
        shx = (int) (parallax.w * pfactor);
        shy = (int) (parallax.h * pfactor);
        b = new boolean[parallax.h + 2 * shy][parallax.w + 2 * shx];
        for (int i = 0; i < parallax.h + 2 * shy; i++) {
            for (int j = 0; j < parallax.w + 2 * shx; j++) {
                b[i][j] = (parallax.random(1) > sparsity);
            }
        }
    }

    public void drawPoints() {
        int shiftx = shx - (int) ((parallax.mouseX - parallax.width / 2) * mpfactor);
        int shifty = shy - (int) ((parallax.mouseY - parallax.height / 2) * mpfactor);
        for (int i = 0; i < parallax.height; i++) {
            for (int j = 0; j < parallax.width; j++) {
                if (b[i + shifty][j + shiftx]) {
                    parallax.pushStyle();
                    if (parallax.random(1) > 0.999f) {
                        parallax.strokeWeight(2);
                    }
                    parallax.point(j, i);
                    parallax.popStyle();
                }
            }
        }
    }
}
