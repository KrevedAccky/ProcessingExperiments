import processing.core.*;

import java.util.HashMap;

public class Parallax extends PApplet {

    int h = 600;
    int w = 800;
    float sprs = 0.9995f;

    ParallaxPoints pp0;
    ParallaxPoints pp1;
    ParallaxPoints pp2;
    QuantedGradient qg;

    int grad_len = 15;

    HashMap<StarItem, Integer> starItems;

    LaserBeam beam1;
    LaserBeam beam2;

    Nyan nyan;

    public void setup() {
        qg = new QuantedGradient(this, grad_len, 40, 140);
        qg.updateGrad(3, 3);
        starItems = new HashMap<StarItem, Integer>();
        randomSeed(0);
        size(w, h);
        frameRate(30);
        pp0 = new ParallaxPoints(this, 0.05f, sprs);
        pp1 = new ParallaxPoints(this, 0.2f, sprs);
        pp2 = new ParallaxPoints(this, 0.5f, sprs);
//  noCursor();
        stroke(238, 240, 211);

        beam1 = new LaserBeam(this, 0, 0, 4, -1.67f);
        beam2 = new LaserBeam(this, 0, 0, 4, -1.67f);

        nyan = new Nyan(this, 460, 200);
        nyan.setRespawnArea(320, 40, 440, 520);
        nyan.show();
    }

    public void draw() {
        qg.drawGradient();

        pp0.drawPoints();
        pp1.drawPoints();
        pp2.drawPoints();

        Util.drawStars(this);

        nyan.update();
        if (mousePressed && nyan.is_showing() && nyan.containsPoint(mouseX, mouseY)) {
            nyan.fade_out();
        }

        Util.drawTree(this);
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"Parallax"};
        if (passedArgs != null) {
            PApplet.main(PApplet.concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
