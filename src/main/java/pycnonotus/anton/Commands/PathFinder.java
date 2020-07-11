package pycnonotus.anton.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PathFinder implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        if( sender instanceof Player){


            if (args.length < 3) {
                System.out.println(" bassa :>");
                return false;
            }

            System.out.println(" blop a commands");
            double x;
            double y;
            double z;
            try {
                x = Double.parseDouble(args[0]);
                y = Double.parseDouble(args[1]);
                z = Double.parseDouble(args[2]);
            } catch (Exception e) {

                return false;
            }


            pycnonotus.anton.PathFinder pathFinder = new pycnonotus.anton.PathFinder(
                    new Location(((Player) sender).getWorld(), x, y, z),
                    ((Player) sender).getLocation(),
                    (Player) sender
            );

            System.out.println("im here");
            pathFinder.find();

            return  true;
        }
        return false;
    }
}
