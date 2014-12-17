import processing.core.PApplet;
import processing.core.PVector;

/**
* Created by kreved on 17/12/14.
*/
class LaserBeam {
    private Parallax parallax;
    float phase = 0;
    float freq[];
    float amp[];

    PVector origin;
    int sines;
    float dphase;

    LaserBeam(Parallax parallax, int x, int y, int _sines, float _dphase) {
        this.parallax = parallax;
        origin = new PVector(x, y);
        sines = _sines;
        dphase = _dphase;
        freq = new float[sines];
        amp = new float[sines];
        for (int i = 0; i < sines; i++) {
            freq[i] = parallax.random(0.001f, 0.1f);
            amp[i] = 4;
        }
    }

    public void drawBeam(PVector target) {
        phase += dphase;
        parallax.pushMatrix();
        parallax.translate(origin.x, origin.y);
        PVector d = target.get();
        d.sub(origin);
        float angle = PApplet.atan2(d.y, d.x);
        parallax.rotate(angle);
        float l = d.mag();
        parallax.pushStyle();
        parallax.stroke(0xffF74514);
        parallax.strokeWeight(2);
        for (int i = 0; i < l; i++) {
            parallax.point(i, sineSum(i) / sines);
        }
        parallax.popStyle();
        parallax.popMatrix();
    }

    public float sineSum(float t) {
        float sum = 0;
        for (int i = 0; i < sines; i++) {
            sum += amp[i] * PApplet.sin(freq[i] * t + phase);
        }
        return sum;
    }
}
