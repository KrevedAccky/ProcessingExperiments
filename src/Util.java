import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.Map;

/**
 * Created by kreved on 17/12/14.
 */
public class Util {
    public static void circle(PApplet applet, float x, float y, float r) {
        applet.ellipse(x, y, r * 2, r * 2);
    }

    public static void star(PApplet applet, float x, float y, float radius1, float radius2, int npoints, int clr) {
        applet.pushMatrix();
        applet.fill(clr);
        applet.translate(x, y);
        applet.rotate(-PConstants.PI / 2);
        float angle = PConstants.TWO_PI / npoints;
        float halfAngle = angle / 2.0f;
        applet.beginShape();
        for (float a = 0; a < PConstants.TWO_PI; a += angle) {
            float sx = PApplet.cos(a) * radius2;
            float sy = PApplet.sin(a) * radius2;
            applet.vertex(sx, sy);
            sx = PApplet.cos(a + halfAngle) * radius1;
            sy = PApplet.sin(a + halfAngle) * radius1;
            applet.vertex(sx, sy);
        }
        applet.endShape(PConstants.CLOSE);
        applet.popMatrix();
    }

    public static void polygon(PApplet applet, float x, float y, float radius, int npoints, int clr) {
        applet.pushMatrix();
        applet.fill(clr);
        applet.translate(x, y);
        applet.rotate(-PConstants.PI / 2);
        float angle = PConstants.TWO_PI / npoints;
        applet.beginShape();
        for (float a = 0; a < PConstants.TWO_PI; a += angle) {
            applet.vertex(PApplet.cos(a) * radius, PApplet.sin(a) * radius);
        }
        applet.endShape(PConstants.CLOSE);
        applet.popMatrix();
    }

    public static void triangl(PApplet applet, float x, float y, float radius, int clr) {
        polygon(applet, x, y, radius, 3, clr);
    }

    public static void tri_shadow(PApplet applet, float x, float y, float radius, int clr, float fact) {
        applet.pushMatrix();
        applet.fill(clr);
        applet.translate(x, y);
        applet.rotate(-PConstants.PI / 2);

        applet.beginShape();
        float a = 0;
        float sx, sy;
        sx = PApplet.cos(a) * radius;
        sy = PApplet.sin(a) * radius;
        applet.vertex(sx, sy);
        a += PConstants.TWO_PI / 3;
        sx = PApplet.cos(a) * radius;
        sy = PApplet.sin(a) * radius;
        applet.vertex(sx, sy);
        a += PConstants.TWO_PI / 3;
        float osx = sx;
        float osy = sy;
        sx = PApplet.cos(a) * radius - osx;
        sy = PApplet.sin(a) * radius - osy;
        sx = osx + fact * sx;
        sy = osy + fact * sy;
        applet.vertex(sx, sy);
        applet.endShape(PConstants.CLOSE);

        applet.popMatrix();
    }

    public static void tree_part(PApplet applet, float x, float y, float radius) {
        triangl(applet, x, y, radius, 0xff44CC8E);
        tri_shadow(applet, x, y, radius, 0xff3DC185, 0.75f);
        tri_shadow(applet, x, y, radius, 0xff3EBA81, 0.3f);
    }

    public static void drawStars(Parallax applet) {
        if (applet.random(1) > 0.9f) {
            StarItem si = new StarItem();
            si.x = (int) PApplet.map(applet.random(1), 0, 1, 0, applet.width);
            si.y = (int) PApplet.map(applet.random(1), 0, 1, 0, applet.height);
            si.ttl = (int) (15 * applet.random(1, 4));
            applet.starItems.put(si, 1);
        }

        applet.pushStyle();
        applet.noStroke();
        for (Map.Entry me : applet.starItems.entrySet()) {
            StarItem si = (StarItem) me.getKey();
            if (si.ttl <= 0) {
                applet.starItems.put(si, null);
            } else {
                si.ttl--;
                star(applet, si.x, si.y, applet.random(1, 2), applet.random(8, 10), 4, 0xffD5D5FF);
            }
        }
        applet.popStyle();
    }

    public static void drawTree(Parallax applet) {
        applet.pushStyle();
        applet.noStroke();
        float sx = applet.width / applet.grad_len * 3.5f;
        float sy = applet.height / applet.grad_len * 3.5f;
        tree_part(applet, sx, sy + 240, 120);
        tree_part(applet, sx, sy + 160, 90);
        tree_part(applet, sx, sy + 80, 60);
        star(applet, sx, sy, 40, 80, 5, 0xffE5484A);
        float dr = 0;
        if (applet.frameCount % 30 < 7) dr = -3;
        else if (applet.frameCount % 30 < 15) dr = 0;
        else if (applet.frameCount % 30 < 22) dr = 3;
        else dr = 0;
        star(applet, sx, sy, 6 + dr, 60 + dr, 5, 0xffD54545);
        drawEyes(applet, sx, sy + 80);
        applet.popStyle();
    }

    public static void drawEyes(Parallax applet, float sx, float sy) {
        PVector m = new PVector(applet.mouseX, applet.mouseY);
        float r = 10;
        PVector c1 = new PVector(sx - (r + 2), sy);
        PVector c2 = new PVector(sx + (r + 2), sy);
        applet.fill(220);
        circle(applet, c1.x, c1.y, r);
        circle(applet, c2.x, c2.y, r);
        PVector d1 = m.get();
        d1.sub(c1);
        d1.normalize();
        d1.mult(0.5f * r);
        PVector d2 = m.get();
        d2.sub(c2);
        d2.normalize();
        d2.mult(0.5f * r);
        PVector cc1 = c1.get();
        cc1.add(d1);
        PVector cc2 = c2.get();
        cc2.add(d2);
        applet.fill(40);
        circle(applet, cc1.x, cc1.y, 0.5f * r);
        circle(applet, cc2.x, cc2.y, 0.5f * r);

        applet.beam1.origin.x = cc1.x;
        applet.beam1.origin.y = cc1.y;
        applet.beam2.origin.x = cc2.x;
        applet.beam2.origin.y = cc2.y;

        if (applet.mousePressed) {
            applet.beam1.drawBeam(new PVector(applet.mouseX, applet.mouseY));
            applet.beam2.drawBeam(new PVector(applet.mouseX, applet.mouseY));
        }
    }
}
