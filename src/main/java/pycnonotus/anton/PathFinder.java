package pycnonotus.anton;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class PathFinder {
    private final Location destinyLocation;
    private  final  Location playerLocation;
    private boolean isPathable = false;
    private boolean isPath = false;

    public PathFinder(Location destinyLocation, Location playerLocation) {
        this.destinyLocation = destinyLocation;
        this.playerLocation = playerLocation;
    }

    public void find(){
        System.out.println(" starting to clacing the way");
        HashMap<Integer,IBlock> space = new HashMap<Integer, IBlock>();
    }

    class IBlock{
        final boolean isAir;
        final Location location;

        public IBlock(boolean isAir, Location location) {
            this.isAir = isAir;
            this.location = location;
        }



    }


}
