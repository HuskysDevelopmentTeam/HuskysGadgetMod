package net.thegaminghuskymc.gadgetmod.init;

import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.print.PrintingManager;
import net.thegaminghuskymc.gadgetmod.api.task.TaskManager;
import net.thegaminghuskymc.gadgetmod.core.io.task.*;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskConnect;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskGetDevices;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskPing;
import net.thegaminghuskymc.gadgetmod.core.print.task.TaskPrint;
import net.thegaminghuskymc.gadgetmod.core.tasks.TaskInstallApp;
import net.thegaminghuskymc.gadgetmod.programs.ApplicationPixelShop;
import net.thegaminghuskymc.gadgetmod.programs.email.task.*;
import net.thegaminghuskymc.gadgetmod.programs.system.task.*;

public class GadgetTasks {

    public static void register() {
        // Core
        TaskManager.registerTask(TaskInstallApp.class);
        TaskManager.registerTask(TaskUpdateApplicationData.class);
        TaskManager.registerTask(TaskPrint.class);
        TaskManager.registerTask(TaskUpdateSystemData.class);
        TaskManager.registerTask(TaskConnect.class);
        TaskManager.registerTask(TaskPing.class);
        TaskManager.registerTask(TaskGetDevices.class);

        // Bank
        TaskManager.registerTask(TaskDeposit.class);
        TaskManager.registerTask(TaskWithdraw.class);
        TaskManager.registerTask(TaskGetBalance.class);
        TaskManager.registerTask(TaskPay.class);
        TaskManager.registerTask(TaskAdd.class);
        TaskManager.registerTask(TaskRemove.class);

        // File browser
        TaskManager.registerTask(TaskSendAction.class);
        TaskManager.registerTask(TaskSetupFileBrowser.class);
        TaskManager.registerTask(TaskGetFiles.class);
        TaskManager.registerTask(TaskGetStructure.class);
        TaskManager.registerTask(TaskGetMainDrive.class);

        // Ender Mail
        TaskManager.registerTask(TaskUpdateInbox.class);
        TaskManager.registerTask(TaskSendEmail.class);
        TaskManager.registerTask(TaskCheckEmailAccount.class);
        TaskManager.registerTask(TaskRegisterEmailAccount.class);
        TaskManager.registerTask(TaskDeleteEmail.class);
        TaskManager.registerTask(TaskViewEmail.class);

        PrintingManager.registerPrint(new ResourceLocation(Reference.MOD_ID, "picture"), ApplicationPixelShop.PicturePrint.class);
    }

}
