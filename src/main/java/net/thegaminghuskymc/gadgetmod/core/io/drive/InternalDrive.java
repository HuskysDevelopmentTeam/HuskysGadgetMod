package net.thegaminghuskymc.gadgetmod.core.io.drive;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.thegaminghuskymc.gadgetmod.core.io.ServerFolder;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public final class InternalDrive extends AbstractDrive {
    public InternalDrive(String name) {
        super(name);
    }

    @Nullable
    public static AbstractDrive fromTag(NBTTagCompound driveTag) {
        AbstractDrive drive = new InternalDrive(driveTag.getString("name"));
        if (driveTag.hasKey("root", Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound folderTag = driveTag.getCompoundTag("root");
            drive.root = ServerFolder.fromTag(folderTag.getString("file_name"), folderTag.getCompoundTag("data"));
        }
        return drive;
    }

    @Override
    public NBTTagCompound toTag() {
        NBTTagCompound driveTag = new NBTTagCompound();
        driveTag.setString("name", name);

        NBTTagCompound folderTag = new NBTTagCompound();
        folderTag.setString("file_name", root.getName());
        folderTag.setTag("data", root.toTag());
        driveTag.setTag("root", folderTag);

        return driveTag;
    }

    @Override
    public Type getType() {
        return Type.INTERNAL;
    }
}
