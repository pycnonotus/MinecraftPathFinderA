package pycnonotus.anton;
/**
 * TODO: add a aysync func
 * change it to work with more stream? cuz for are lazy and ugly code :|
 * make the function to check if player is moved
 * change from set to a faster collection?
 * <p>
 * TODO after: make an intrface \ api class
 * TODO after after: clean up code and make names ok (kind of ugly now :<)
 * TODO i : add doc!
 * <p>
 * TODO: add check if path can't be done in time
 */

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PathFinder {
    private final Location destinyLocation;
    private Location playerLocation;
    private final Player player;
    private final boolean isPathable = false;
    private final boolean isPath = false;
    private IBlock[][] space;

    public PathFinder(Location destinyLocation, Location playerLocation, Player player) {
        this.destinyLocation = destinyLocation;
        this.playerLocation = playerLocation;
        this.player = player;

    }

    public void find() {
        playerLocation = new Location(playerLocation.getWorld(), playerLocation.getBlockX(), playerLocation.getBlockY(), playerLocation.getBlockZ());
        HashMap<Integer, IBlock> space = new HashMap<Integer, IBlock>();
        player.spawnParticle(Particle.REDSTONE, playerLocation, 10, 0.001, 1, 0, 1, new Particle.DustOptions(Color.RED, 10));
        player.getWorld().spawnParticle(Particle.HEART, destinyLocation, 1, 1, 1, 1);
        System.out.println(" starting to clacing the way2");
        Set<IBlock> open = new HashSet<IBlock>();
        Set<IBlock> close = new HashSet<IBlock>();
        IBlock endBlock = null;
        open.add(new IBlock(playerLocation));
        boolean end = false;


        while (!end) {

            //TO DO: return if there same f so will do magic by h

            IBlock[] arrBlock = open.stream().toArray(IBlock[]::new);
            IBlock corent = arrBlock[0];
            //TODO change from for, for is ugly and slow
            for (int i = 1; i < arrBlock.length; i++) {

                if (arrBlock[i].fCost < corent.fCost) {
                    corent = arrBlock[i];
                }
            }
            System.out.println("i >>" + corent + " size " + arrBlock.length);
            open.remove(corent);
            close.add(corent);
            if (corent.isAt(destinyLocation)) {
                System.out.println("path is foundededededed");
                end = true;
                endBlock = corent;
                break;
            }

            for (IBlock sideBlock : corent.getSideBlocks()) {
                if (!sideBlock.isValid || close.stream().anyMatch(n -> n.isSameLock(sideBlock))) { // if block is in closed

                    continue;

                }

                IBlock peek = open.stream().filter(n -> n.isSameLock(sideBlock)).findFirst().orElse(null);

                if (peek == null) {
                    peek = sideBlock;
                }

                boolean shorter = sideBlock.isShorterThenDad(peek.location);
                IBlock finalPeek = peek;
                boolean isInOpen = open.stream().anyMatch(n -> n.isSameLock(finalPeek));
                if (shorter || !isInOpen) {
                    peek.dad = corent;
                    if (!isInOpen) {
                        open.add(peek);
                    }
                }

            }


        }
        IBlock ptBlock = endBlock;
        while (ptBlock != null) {
            System.out.println("  path is:: " + ptBlock.toString());
            Location loc = ptBlock.location;
            //TODO: clean this up make enum for this and move the trail to outer function
            final Particle.DustOptions white = new Particle.DustOptions(Color.WHITE, 0.22F);
            final Particle.DustOptions white_1 = new Particle.DustOptions(Color.fromRGB(181, 239, 247), 0.42F);
            final Particle.DustOptions white_2 = new Particle.DustOptions(Color.fromRGB(77, 133, 245), 0.55F);
            final Particle.DustOptions white_3 = new Particle.DustOptions(Color.fromRGB(36, 97, 218), 0.68F);
            final Particle.DustOptions white_4 = new Particle.DustOptions(Color.fromRGB(21, 66, 156), 0.80F);

            final Particle.DustOptions blue_1 = new Particle.DustOptions(Color.fromRGB(64, 97, 206), 0.35F);
            final Particle.DustOptions blue_2 = new Particle.DustOptions(Color.fromRGB(82, 134, 193), 0.60F);
            final Particle.DustOptions blue_3 = new Particle.DustOptions(Color.fromRGB(136, 190, 252), 10.85F);

            player.spawnParticle(Particle.REDSTONE, loc, 10, 0, 0, 0, 1, blue_3);
            ptBlock = ptBlock.dad;

        }


    }


    class IBlock {
        boolean isValid;
        final Location location;
        boolean checked;
        public int gCost; // how far is from player
        public int hCost; // how fair is from destiny
        public int fCost; // g+h combined;
        public IBlock dad = null;

        public IBlock(Location location) {

            this.location = location;
            gCost = calcDestinyCost(location, playerLocation);
            hCost = calcDestinyCost(location, destinyLocation);
            fCost = gCost + hCost;
            Block block = player.getWorld().getBlockAt(location);
            isValid = block.isPassable(); //need to check that it aint tunel
            if (isValid) {
                //TODO fix air check , idk why but the air check fails :O
                //isValid = !location.add(0, -1, 0).getBlock().isPassable()&&!location.add(0,-2,0).getBlock().isPassable();
            }
        }

        public int calcDestinyCost(Location a, Location b) {
            int x = (a.getBlockX() - b.getBlockX());
            x *= x;
            int y = a.getBlockY() - b.getBlockY();
            y *= y;
            int z = a.getBlockZ() - b.getBlockZ();
            z *= z;
            int total = x + y + z;
            int cost = (int) (10 * Math.sqrt(total));

            return cost;

        }

        public IBlock getOffsetBlock(int x, int y, int z) {

            Location loc = new Location(this.location.getWorld(),
                    this.location.getBlockX(),
                    this.location.getBlockY(),
                    this.location.getBlockZ());
            loc = loc.add(x, z, y);

            return new IBlock(loc);
        }

        public boolean isSameLock(IBlock block) {
            if (block.location.getBlockX() != this.location.getBlockX()) {
                return false;
            }
            if (block.location.getBlockZ() != this.location.getBlockZ()) {
                return false;
            }
            return block.location.getBlockY() == this.location.getBlockY();
        }

        public boolean isAt(Location location) {
            return location.getBlockX() == this.location.getBlockX()
                    && location.getBlockY() == this.location.getBlockY()
                    && location.getBlockZ() == this.location.getBlockZ();

        }


        public boolean isShorterThenDad(Location location) {
            if (dad == null) {
                return true;
            }
            return dad.location.distance(this.location) < location.distance(this.location);
        }

        public String toString() {
            return "f:" + fCost + "" + "loc:" + location.toVector().toString();
        }

        public IBlock[] getSideBlocks() {
            IBlock[] ret = new IBlock[(3 * 3 * 3) - 1];
            int i = 0;
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    for (int z = -1; z < 2; z++) {
                        if (x == 0 && x == y && x == z) {
                            continue;
                        }

                        ret[i] = this.getOffsetBlock(x, y, z);
                        i++;
                    }
                }
            }
            return ret;
        }


    }


}
