import processing.core.PVector;

/**
* Created by kreved on 17/12/14.
*/
class Rct {
    PVector origin;
    PVector size;

    Rct() {
        origin = new PVector(0, 0);
        size = new PVector(0, 0);
    }

    Rct(int x, int y, int w, int h) {
        origin = new PVector(x, y);
        size = new PVector(w, h);
    }
}
