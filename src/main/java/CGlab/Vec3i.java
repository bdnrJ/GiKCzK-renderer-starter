package CGlab;

import static java.lang.Math.*;

public class Vec3i {
    public int x;
    public int y;
    public int z;

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }

    public int get(int a ){
        if (a == 0){
            return x;
        }else if (a == 1){
            return y;
        }else if (a == 2){
            return z;
        }else{
            return -1;
        }
    }



}