package net.husky.device.programs.social_medias;

import codechicken.lib.colour.ColourRGBA;
import net.husky.device.api.app.*;
import net.husky.device.api.app.component.Button;
import net.husky.device.api.app.component.ButtonTab;
import net.husky.device.api.app.listener.ClickListener;
import net.husky.device.api.io.File;
import net.husky.device.core.Laptop;
import net.husky.device.core.NeonOS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.*;
import java.util.List;

public class ApplicationDiscord extends Application
{

	private Layout serverList;
    private Layout messages;
    private Layout nameAndInfo;
    private Layout profile;
    private Layout search;
    private Layout searchBar;
    private Layout usersDM;
    private Layout userInfo;
    private Button DMButton;

    private Button[] serversButtons = new Button[5];
    private ButtonTab[] tabs = new ButtonTab[5];
    private Layout[] servers = new Layout[5];

    private Icons[] stacks = new Icons[] {
            Icons.CHEESE,
            Icons.GAME_CONTROLLER,
            Icons.CAMERA,
            Icons.HEADPHONES,
            Icons.TELEVISION
    };

    private Icons[] stacks2 = new Icons[] {
            Icons.CHEESE,
            Icons.GAME_CONTROLLER,
            Icons.CAMERA,
            Icons.HEADPHONES,
            Icons.TELEVISION
    };

    private String[] names = new String[] {
            "The Amazing Cheese Server",
            "Gaming Channel",
            "Art Channel",
            "Music Channel",
            "TV Channel"
    };

    private String[] tooltips = new String[] {
            "We spam Cheese emojies!",
            "We are gamers from the whole world!",
            "All of the art you need!",
            "We are all music lovers!",
            "We play TV-Shows the whole time"
    };

    int activeTab = 0;

    public ApplicationDiscord() {
        this.setDefaultWidth(270);
        this.setDefaultHeight(140);
    }

	@Override
	public void init() {
        ClickListener cl = new ClickListener() {
            @Override
            public void onClick(Component c, int mouseButton) {
                activeTab = ((ButtonTab)c).getTabIndex();
            }
        };

        serverList = new Layout(60, 130);
        serverList.setBackground(new Layout.Background() {
            @Override
            public void render(Gui gui, Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, boolean windowActive) {
                gui.drawRect(x, y, x + width, y + height, new ColourRGBA(22,23,25, 255).argb());
            }
        });
        super.addComponent(serverList);

        messages = new Layout(40, 20, 20, 50);
        messages.setBackground(new Layout.Background() {
            @Override
            public void render(Gui gui, Minecraft mc, int x, int y, int width, int height, int mouseX, int mouseY, boolean windowActive) {
                gui.drawRect(x, y, x + width, y + height, new ColourRGBA(32,34,37, 255).argb());
            }
        });
//        super.addComponent(messages);

        for(int i = 0; i < 4; i++) {
            servers[i] = serverList;
            serversButtons[i] = new Button(3, 27 + (i * 24), 23, 23, stacks[i]);
            serversButtons[i].setToolTip(names[i], tooltips[i]);
//            serversButtons[i].setClickListener(cl);
            servers[i].addComponent(serversButtons[i]);
        }

        for(int i = 0; i < 4; i++) {
            servers[i] = serverList;
            serversButtons[i] = new Button(28, 27 + (i * 24), 23, 23, stacks[i]);
            serversButtons[i].setToolTip(names[i], tooltips[i]);
//            serversButtons[i].setClickListener(cl);
            servers[i].addComponent(serversButtons[i]);
        }

        DMButton = new Button(20, 6, Icons.COMMUNITY);
        super.addComponent(DMButton);

	}

    @Override
    public void render(NeonOS laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean active, float partialTicks) {
        try {
            servers[activeTab].render(laptop, mc, x+10, y+10, mouseX, mouseY, active, partialTicks);
        } catch(Exception e) {
            // Do we really need to do something here?
        }
        super.render(laptop, mc, x, y, mouseX, mouseY, active, partialTicks);
    }

    @Override
	public void load(NBTTagCompound tagCompound)
	{
		
	}

	@Override
	public void save(NBTTagCompound tagCompound)
	{
		
	}

	public static class MessageManager
	{
		public static final MessageManager INSTANCE = new MessageManager();

		@SideOnly(Side.CLIENT)
		private List<Message> inbox;

		private Map<UUID, String> uuidToName = new HashMap<>();
		private Map<String, List<Message>> uuidToInbox = new HashMap<>();

		public boolean addMessagetoAllMessages(Message email, String to)
		{
			if (uuidToInbox.containsKey(to))
			{
				uuidToInbox.get(to).add(0, email);
				return true;
			}
			return false;
		}

		@SideOnly(Side.CLIENT)
		public List<Message> getAllMessages()
		{
			if(inbox == null)
			{
				inbox = new ArrayList<>();
			}
			return inbox;
		}

		public List<Message> getEmailsForAccount(EntityPlayer player)
		{
			if (uuidToName.containsKey(player.getUniqueID()))
			{
				return uuidToInbox.get(uuidToName.get(player.getUniqueID()));
			}
			return new ArrayList<Message>();
		}

		public boolean addAccount(EntityPlayer player, String name)
		{
			if (!uuidToName.containsKey(player.getUniqueID()))
			{
				if (!uuidToName.containsValue(name))
				{
					uuidToName.put(player.getUniqueID(), name);
					uuidToInbox.put(name, new ArrayList<Message>());
					return true;
				}
			}
			return false;
		}

		public boolean hasAccount(UUID uuid)
		{
			return uuidToName.containsKey(uuid);
		}

		public String getName(EntityPlayer player)
		{
			return uuidToName.get(player.getUniqueID());
		}

		public void readFromNBT(NBTTagCompound nbt)
		{
			uuidToInbox.clear();

			NBTTagList inboxes = (NBTTagList) nbt.getTag("all_messages");
			for (int i = 0; i < inboxes.tagCount(); i++)
			{
				NBTTagCompound inbox = inboxes.getCompoundTagAt(i);
				String name = inbox.getString("name");

				List<Message> emails = new ArrayList<Message>();
				NBTTagList emailTagList = (NBTTagList) inbox.getTag("messages");
				for (int j = 0; j < emailTagList.tagCount(); j++)
				{
					NBTTagCompound emailTag = emailTagList.getCompoundTagAt(j);
                    Message email = Message.readFromNBT(emailTag);
					emails.add(email);
				}
				uuidToInbox.put(name, emails);
			}

			uuidToName.clear();

			NBTTagList accounts = (NBTTagList) nbt.getTag("accounts");
			for (int i = 0; i < accounts.tagCount(); i++)
			{
				NBTTagCompound account = accounts.getCompoundTagAt(i);
				UUID uuid = UUID.fromString(account.getString("UUID"));
				String name = account.getString("name");
				uuidToName.put(uuid, name);
			}
		}

		public void writeToNBT(NBTTagCompound nbt)
		{
			NBTTagList inboxes = new NBTTagList();
			for (String key : uuidToInbox.keySet())
			{
				NBTTagCompound inbox = new NBTTagCompound();
				inbox.setString("name", key);

				NBTTagList emailTagList = new NBTTagList();
				List<Message> emails = uuidToInbox.get(key);
				for (Message email : emails)
				{
					NBTTagCompound emailTag = new NBTTagCompound();
					email.writeToNBT(emailTag);
					emailTagList.appendTag(emailTag);
				}
				inbox.setTag("messages", emailTagList);
				inboxes.appendTag(inbox);
			}
			nbt.setTag("all_messages", inboxes);

			NBTTagList accounts = new NBTTagList();
			for (UUID key : uuidToName.keySet())
			{
				NBTTagCompound account = new NBTTagCompound();
				account.setString("UUID", key.toString());
				account.setString("name", uuidToName.get(key));
				accounts.appendTag(account);
			}
			nbt.setTag("Accounts", accounts);
		}

		public void clear()
		{
			uuidToInbox.clear();
			uuidToName.clear();
			inbox.clear();
		}
	}


    public static class Message
    {
        private String author, message;
        private File attachment;
        private boolean read;

        private Message(String message, @Nullable File file)
        {
            this.message = message;
            this.attachment = file;
            this.read = false;
        }

        private Message(String author, String message, @Nullable File attachment)
        {
            this(message, attachment);
            this.author = author;
        }

        public String getAuthor()
        {
            return author;
        }

        public void setAuthor(String author)
        {
            this.author = author;
        }

        public String getMessage()
        {
            return message;
        }

        public File getAttachment()
        {
            return attachment;
        }

        public boolean isRead()
        {
            return read;
        }

        public void setRead(boolean read)
        {
            this.read = read;
        }

        public void writeToNBT(NBTTagCompound nbt)
        {
            if (author != null) nbt.setString("author", this.author);
            nbt.setString("message", this.message);
            nbt.setBoolean("read", this.read);

            if(attachment != null)
            {
                NBTTagCompound fileTag = new NBTTagCompound();
                fileTag.setString("file_name", attachment.getName());
                fileTag.setTag("data", attachment.toTag());
                nbt.setTag("attachment", fileTag);
            }
        }

        public static Message readFromNBT(NBTTagCompound nbt)
        {
            File attachment = null;
            if(nbt.hasKey("attachment", Constants.NBT.TAG_COMPOUND))
            {
                NBTTagCompound fileTag = nbt.getCompoundTag("attachment");
                attachment = File.fromTag(fileTag.getString("file_name"), fileTag.getCompoundTag("data"));
            }
            Message email = new Message(nbt.getString("author"), nbt.getString("message"), attachment);
            email.setRead(nbt.getBoolean("read"));
            return email;
        }
    }

    private static class User
    {
        private String nickname;
        private String email;

        public User(String nickname, String email)
        {
            this.nickname = nickname;
            this.email = email;
        }

        public String getNickname()
        {
            return nickname;
        }

        public String getEmail()
        {
            return email;
        }

        @Override
        public String toString()
        {
            return nickname;
        }
    }

}
