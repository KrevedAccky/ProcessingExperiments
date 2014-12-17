import processing.core.PApplet;

/**
 * Created by kreved on 17/12/14.
 */
public class TestModule extends PApplet {

    public void setup() {
        size(640, 480);
    }

    public void draw() {
        background(millis());
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"TestModule"};
        if (passedArgs != null) {
            PApplet.main(PApplet.concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
