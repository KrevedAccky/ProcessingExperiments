import processing.core.PApplet;
import processing.core.PConstants;

/**
* Created by kreved on 17/12/14.
*/
class QuantedGradient {
    private Parallax parallax;
    int size;
    int grad[][];
    float min_value;
    float max_value;

    QuantedGradient(Parallax parallax, int _size, float _min_value, float _max_value) {
        this.parallax = parallax;
        updateSize(_size);
        updateRange(_min_value, _max_value);
    }

    public void updateRange(float _min_value, float _max_value) {
        min_value = _min_value;
        max_value = _max_value;
    }

    public void updateSize(int _size) {
        size = _size;
        grad = new int[size][size];
        updateGrad(0, 0);
    }

    public void updateGrad(int mx, int my) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int len = (int) PApplet.sqrt(PApplet.pow(PApplet.abs(mx - j), 2) + PApplet.pow(PApplet.abs(my - i), 2));
                grad[j][i] = (int) PApplet.map(len, 0, size * PApplet.sqrt(2), max_value, min_value);
            }
        }
    }

    public void updateGradWithMousePos() {
        int mx = (int) PApplet.map(parallax.mouseX, 0, parallax.width, 0, size);
        int my = (int) PApplet.map(parallax.mouseY, 0, parallax.height, 0, size);
        updateGrad(mx, my);
    }

    public void drawGradient() {
        parallax.pushStyle();
        parallax.colorMode(PConstants.HSB);
        parallax.loadPixels();
        for (int i = 0; i < parallax.height; i++) {
            for (int j = 0; j < parallax.width; j++) {
                int d = grad[(int) PApplet.map(j, 0, parallax.width, 0, size)][(int) PApplet.map(i, 0, parallax.height, 0, size)];
                int c = parallax.color(d, d, d);
                int idx = i * parallax.width + j;
                parallax.pixels[idx] = c;
            }
        }
        parallax.updatePixels();
        parallax.popStyle();
    }
}
