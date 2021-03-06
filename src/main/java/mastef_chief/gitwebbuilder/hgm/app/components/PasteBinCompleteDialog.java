package mastef_chief.gitwebbuilder.hgm.app.components;

import mastef_chief.gitwebbuilder.hgm.app.tasks.TaskNotificationCopiedLink;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import io.github.vampirestudios.gadget.api.app.Dialog;
import io.github.vampirestudios.gadget.api.app.component.Button;
import io.github.vampirestudios.gadget.api.app.component.Text;
import io.github.vampirestudios.gadget.api.app.listener.ClickListener;
import io.github.vampirestudios.gadget.api.task.TaskManager;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class PasteBinCompleteDialog extends Dialog {

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Clipboard clipboard = toolkit.getSystemClipboard();

    private String messageText;

    private ClickListener positiveListener;
    private Button openLinkButton;

    public PasteBinCompleteDialog(String messageText)
    {
        this.messageText = messageText;
    }

    @Override
    public void init(@Nullable NBTTagCompound nbtTagCompound)
    {
        super.init(nbtTagCompound);

        int lines = Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth("Link: " + messageText, getWidth() - 10).size();
        defaultLayout.height += (lines - 1) * 9;

        defaultLayout.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> Gui.drawRect(x, y, x + width, y + height, Color.LIGHT_GRAY.getRGB()));

        Text message = new Text("Link: " + messageText, 5, 5, getWidth() - 10);
        this.addComponent(message);

        Button buttonPositive = new Button(getWidth() - 41, getHeight() - 20, "Close");
        buttonPositive.setSize(36, 16);
        buttonPositive.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if(positiveListener != null)
            {
                positiveListener.onClick(mouseX, mouseY, mouseButton);
            }
            close();
        });
        this.addComponent(buttonPositive);

        /*openLinkButton = new Button(getWidth() - 145, getHeight() - 20, "Open");
        openLinkButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0){
                GuiConfirmOpenLink gui = new GuiConfirmOpenLink((res, id) ->{

                }, messageText, 0, false);
                gui.disableSecurityWarning();
                Minecraft.getMinecraft().displayGuiScreen(gui);
            }
        });
        this.addComponent(openLinkButton);*/

        Button copyToClipboard = new Button(getWidth() - 104, getHeight() - 20, "Copy Link");
        copyToClipboard.setClickListener((mouseX, mouseY, mouseButton) -> {
            if(mouseButton == 0){
                StringSelection code = new StringSelection(messageText);
                clipboard.setContents(code, null);
                TaskManager.sendTask(new TaskNotificationCopiedLink());
            }
        });
        this.addComponent(copyToClipboard);

    }

}
