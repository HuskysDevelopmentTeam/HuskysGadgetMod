package io.github.vampirestudios.gadget.api.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import io.github.vampirestudios.gadget.api.task.Callback;
import io.github.vampirestudios.gadget.api.task.TaskManager;
import io.github.vampirestudios.gadget.programs.system.object.Account;
import io.github.vampirestudios.gadget.programs.system.task.TaskAdd;
import io.github.vampirestudios.gadget.programs.system.task.TaskGetBalance;
import io.github.vampirestudios.gadget.programs.system.task.TaskPay;
import io.github.vampirestudios.gadget.programs.system.task.TaskRemove;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>The Bank is a built in currency system that you can use in your application.
 * You should definitely use this instead of writing your own as this would allow
 * people to use the same currency across applications, not just your own.</p>
 * <p>
 * <p>Please keep in mind that 1 unit equals 1 emerald. Players can withdraw currency
 * from their account into emeralds. Be nice, and don't abuse this system. Check out
 * the example applications to learn how you use this currency system.</p>
 */
public class BankUtil {
    public static final BankUtil INSTANCE = new BankUtil();

    private Map<UUID, Account> uuidToAccount = new HashMap<UUID, Account>();

    private BankUtil() {
    }

    /**
     * Sends a request to get the balance of this user's account. To actually get
     * the balance, you need to implement a {@link io.github.vampirestudios.gadget.api.task.Callback}
     * and get the integer with the key "balance" from the NBT parameter.
     *
     * @param callback he callback object to processing the response
     */
    public static void getBalance(Callback<NBTTagCompound> callback) {
        TaskManager.sendTask(new TaskGetBalance().setCallback(callback));
    }

    /**
     * <p>Sends a request for the user to pay x amount from their account. Use the callback
     * to check if payment was successful. You will also get returned their new balance. Use
     * the key "balance" to an integer from the NBT parameter in callback.</p>
     *
     * @param uuid     the UUID of the player you want to pay
     * @param amount   the amount to pay
     * @param callback the callback object to processing the response
     */
    public static void pay(String uuid, int amount, Callback<NBTTagCompound> callback) {
        TaskManager.sendTask(new TaskPay().setCallback(callback));
    }

    /**
     * <p>Sends a request to add x amount to the user's account. Use the callback
     * to check if addition was successful. You will also get returned their new balance. Use
     * the key "balance" to an integer from the NBT parameter in callback.</p>
     *
     * @param callback he callback object to processing the response
     */
    public static void add(int amount, Callback<NBTTagCompound> callback) {
        TaskManager.sendTask(new TaskAdd(amount).setCallback(callback));
    }

    /**
     * <p>Sends a request to remove x amount to the user's account. Use the callback
     * to check if removal was successful. You will also get returned their new balance. Use
     * the key "balance" to an integer from the NBT parameter in callback.</p>
     *
     * @param callback he callback object to processing the response
     */
    public static void remove(int amount, Callback<NBTTagCompound> callback) {
        TaskManager.sendTask(new TaskRemove(amount).setCallback(callback));
    }

    //TODO: Make private. Only the bank application should have access to these.

    public Account getAccount(EntityPlayer player) {
        if (!uuidToAccount.containsKey(player.getUniqueID())) {
            uuidToAccount.put(player.getUniqueID(), new Account(0));
        }
        return uuidToAccount.get(player.getUniqueID());
    }

    public Account getAccount(UUID uuid) {
        return uuidToAccount.get(uuid);
    }

    public void save(NBTTagCompound tag) {
        NBTTagList accountList = new NBTTagList();
        for (UUID uuid : uuidToAccount.keySet()) {
            NBTTagCompound accountTag = new NBTTagCompound();
            Account account = uuidToAccount.get(uuid);
            accountTag.setString("uuid", uuid.toString());
            accountTag.setInteger("balance", account.getBalance());
            accountList.appendTag(accountTag);
        }
        tag.setTag("accounts", accountList);
    }

    public void load(NBTTagCompound tag) {
        NBTTagList accountList = (NBTTagList) tag.getTag("accounts");
        for (int i = 0; i < accountList.tagCount(); i++) {
            NBTTagCompound accountTag = accountList.getCompoundTagAt(i);
            UUID uuid = UUID.fromString(accountTag.getString("uuid"));
            Account account = new Account(accountTag.getInteger("balance"));
            uuidToAccount.put(uuid, account);
        }
    }
}