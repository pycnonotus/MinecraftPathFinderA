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


            if(args.length<3){
                System.out.println(" bassa :>");
                return  false;
            }

            System.out.println(" blop a commands");

            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);

            pycnonotus.anton.PathFinder pathFinder = new pycnonotus.anton.PathFinder(
                    ((Player)sender).getLocation(),
                    new Location(((Player) sender).getWorld(),x,y,z)
            );

            pathFinder.find();

            return  true;
        }
        return false;
    }
}
