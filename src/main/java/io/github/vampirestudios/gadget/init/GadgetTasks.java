package io.github.vampirestudios.gadget.init;

import net.minecraft.util.ResourceLocation;
import io.github.vampirestudios.gadget.Reference;
import io.github.vampirestudios.gadget.api.print.PrintingManager;
import io.github.vampirestudios.gadget.api.task.TaskManager;
import io.github.vampirestudios.gadget.core.io.task.*;
import io.github.vampirestudios.gadget.core.network.task.TaskConnect;
import io.github.vampirestudios.gadget.core.network.task.TaskGetDevices;
import io.github.vampirestudios.gadget.core.network.task.TaskPing;
import io.github.vampirestudios.gadget.core.print.task.TaskPrint;
import io.github.vampirestudios.gadget.core.tasks.TaskInstallApp;
import io.github.vampirestudios.gadget.programs.ApplicationPixelShop;
import io.github.vampirestudios.gadget.programs.email.task.*;
import io.github.vampirestudios.gadget.programs.system.task.*;

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
