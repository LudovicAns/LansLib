package fr.ludovicans.lanslib.utils;

import fr.ludovicans.lanslib.LansLib;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("unused")
public class LuckPermsUtility {

    /**
     * Get Luckperms API.
     *
     * @return {@link LuckPerms} instance.
     * @throws DependencyError when Luckperms is not installed on the server.
     */
    public static @NotNull LuckPerms getLuckPermsAPI() throws DependencyError {
        if (LansLib.getINSTANCE().getLuckPermsAPI() == null)
            throw new DependencyError("You can't use " + LuckPermsUtility.class.getSimpleName() + " without installing plugin before.");
        else
            return LansLib.getINSTANCE().getLuckPermsAPI();
    }

    /**
     * Get primary group of a {@link User}.
     *
     * @param user that have a primary group.
     * @return primary {@link Group} of the user. Null user doesn't have a primary group.
     * @throws DependencyError when Luckperms is not installed on the server.
     */
    public static @Nullable Group getUserPrimaryGroup(final @NotNull User user) throws DependencyError {
        return getLuckPermsAPI().getGroupManager().getGroup(user.getPrimaryGroup());
    }

    /**
     * Get an {@link User} from an {@link UUID} of a player.
     *
     * @param uuid of the {@link Player}.
     * @return {@link User} associated to the uuid.
     * @throws DependencyError when Luckperms is not installed on the server.
     */
    public static @Nullable User getUser(final @NotNull UUID uuid) throws DependencyError {
        return getLuckPermsAPI().getUserManager().getUser(uuid);
    }

    /**
     * Get an {@link User} from a {@link Player}.
     *
     * @param player associated to the {@link User}.
     * @return {@link User} associated to the {@link Player}.
     * @throws DependencyError when Luckperms is not installed on the server.
     */
    public static @NotNull User getUser(final @NotNull Player player) throws DependencyError {
        return getLuckPermsAPI().getPlayerAdapter(Player.class).getUser(player);
    }


}
