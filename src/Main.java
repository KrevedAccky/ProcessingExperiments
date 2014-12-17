import processing.core.PApplet;

/**
 * Created by kreved on 17/12/14.
 */
public class Main {
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"Parallax"};
        if (passedArgs != null) {
            PApplet.main(PApplet.concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
