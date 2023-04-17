package CGlab;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import javax.imageio.ImageIO;

public class Renderer {

    public enum LineAlgo { NAIVE, DDA, BRESENHAM, BRESENHAM_INT; }

    public BufferedImage render;
    public  int h = 200;
    public  int w = 200;

    private String filename;
    private LineAlgo lineAlgo = LineAlgo.NAIVE;

    public Renderer(String filename) {
        render = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        this.filename = filename;
    }

    public Renderer(String filename, int w, int h) {
        render = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        this.filename = filename;
    }



    public Vec3f barycentric(Vec2f A, Vec2f B, Vec2f C, Vec2f P) {
        Vec3f v1 = new Vec3f((B.x - A.x), (C.x - A.x), (A.x - P.x));
        Vec3f v2 = new Vec3f((B.y - A.y), (C.y - A.y), (A.y - P.y));


        Vec3f cross = cross(v1,v2);

        Vec2f uv = new Vec2f(cross.x / cross.z, cross.y / cross.z);

        Vec3f barycentric = new Vec3f(uv.x, uv.y, 1 - uv.x - uv.y);

        return barycentric;
    }

    public Vec3f barycentric(Vec2i A, Vec2i B, Vec2i C, Vec2i P) {
        Vec3f v1 = new Vec3f((B.x - A.x), (C.x - A.x), (A.x - P.x));
        Vec3f v2 = new Vec3f((B.y - A.y), (C.y - A.y), (A.y - P.y));
        Vec3f cross = cross(v1, v2);
        Vec2f uv = new Vec2f(cross.x / cross.z, cross.y / cross.z);

        Vec3f barycentric = new Vec3f(uv.x, uv.y, 1 - uv.x - uv.y);

        return barycentric;
    }

    public void drawTriangle(Vec2f A, Vec2f B, Vec2f C, Color color) {
        Float[] xs = {A.x, B.x, C.x};
        Float[] ys = {A.y, B.y, C.y};
        int minx = Math.round(Collections.min(Arrays.asList(xs)));
        int maxx = Math.round(Collections.max(Arrays.asList(xs))+ 0.5f);
        int miny = Math.round(Collections.min(Arrays.asList(ys)));
        int maxy = Math.round(Collections.max(Arrays.asList(ys))+ 0.5f);

        for (int i = minx; i <= maxx; i++) {
            for (int j = miny; j <= maxy; j++) {
                Vec2f P = new Vec2f((float) i, (float) j);
                if ((barycentric(A, B, C, P).x > 0 && barycentric(A, B, C, P).x < 1 && barycentric(A, B, C, P).y < 1 && barycentric(A, B, C, P).y > 0 && barycentric(A, B, C, P).z > 0 && barycentric(A, B, C, P).z < 1)) {
                    drawPoint(i, j, color);
                }
            }
        }
    }

    public void drawTriangle(Vec2i A, Vec2i B, Vec2i C, Color color) {
        Integer[] xs = {A.x, B.x, C.x};
        Integer[] ys = {A.y, B.y, C.y};
        int minx = Collections.min(Arrays.asList(xs));
        int maxx = Collections.max(Arrays.asList(xs));
        int miny = Collections.min(Arrays.asList(ys));
        int maxy = Collections.max(Arrays.asList(ys));

        for (int i = minx; i <= maxx; i++) {
            for (int j = miny; j <= maxy; j++) {
                Vec2i P = new Vec2i( i,j);
                if ((barycentric(A, B, C, P).x > 0 && barycentric(A, B, C, P).x < 1 && barycentric(A, B, C, P).y < 1 && barycentric(A, B, C, P).y > 0 && barycentric(A, B, C, P).z > 0 && barycentric(A, B, C, P).z < 1)) {
                    drawPoint(i, j, color);
                }
            }
        }
    }


    public Vec3f cross(Vec3f w1,Vec3f w2){
        float x = w1.y * w2.z - w1.z * w2.y;
        float y = w1.z * w2.x - w1.x * w2.z;
        float z = w1.x * w2.y - w1.y * w2.x;
        return new Vec3f(x, y, z);
    }





    public void drawPoint(int x, int y, Color color) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);
        render.setRGB(x, y, color.getRGB());
    }

    public void drawLine(int x0, int y0, int x1, int y1, LineAlgo lineAlgo) {
        if(lineAlgo == LineAlgo.NAIVE) drawLineNaive(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.DDA) drawLineDDA(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.BRESENHAM) drawLineBresenham(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.BRESENHAM_INT) drawLineBresenhamInt(x0, y0, x1, y1);
    }

    public void drawLineNaive(int x0, int y0, int x1, int y1) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);
        int dy = y1 - y0;
        int dx = x1 - x0;
        int m = dy/dx;
        int y = y0;

        for (int i = x0; i < x1; i++) {
            render.setRGB(i,y,white);
            y = y + m;
        }

    }

    public void drawLineDDA(int x0, int y0, int x1, int y1) {
        // TODO: zaimplementuj
    }

    public void drawLineBresenham(int x0, int y0, int x1, int y1) {
        // TODO: zaimplementuj
    }

    public void drawLineBresenhamInt(int x0, int y0, int x1, int y1) {
        // TODO: zaimplementuj
    }

    public void save() throws IOException {
        File outputfile = new File(filename);
        render = Renderer.verticalFlip(render);
        ImageIO.write(render, "png", outputfile);
    }

    public void clear() {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int black = 0 | (0 << 8) | (0 << 16) | (255 << 24);
                render.setRGB(x, y, black);
            }
        }
    }

    public static BufferedImage verticalFlip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage flippedImage = new BufferedImage(w, h, img.getColorModel().getTransparency());
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return flippedImage;
    }
}
