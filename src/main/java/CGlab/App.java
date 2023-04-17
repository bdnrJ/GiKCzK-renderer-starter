package CGlab;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    String version = "0.02";

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Niepoprawne argumenty! Użyj: java App [ścieżka] [szerokość] [wysokość]");
            return;
        }

        String filePath = args[0];
        Integer width = Integer.parseInt(args[1]);
        Integer height = Integer.parseInt(args[2]);

        System.out.println("Podane argumanety: ");
        System.out.println("Sciezka: "+filePath);
        System.out.println("Width: "+width);
        System.out.println("Height:"+height);

        RandomColorRenderer mainRenderer = new RandomColorRenderer(filePath, width, height);
        Model jelen = new Model();
        try {
            jelen.readOBJ("C:\\Users\\student\\IdeaProjects\\GiKCzK-renderer-starter\\img\\deer.obj");
            mainRenderer.clear();
            mainRenderer.render(jelen);
            mainRenderer.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        Renderer mainRenderer = new Renderer(filePath);
//        mainRenderer.clear();
////        mainRenderer.drawLineNaive(10, 50, 90, 30);
//        mainRenderer.drawTriangle(new Vec2f(10,40), new Vec2f(10,60), new Vec2f(90,10), new Color(255,0,0));
//        try {
//            mainRenderer.save();
//        } catch (IOException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public String getVersion() {
        return this.version;
    }
}
