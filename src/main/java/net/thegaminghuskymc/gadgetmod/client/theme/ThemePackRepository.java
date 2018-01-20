package net.thegaminghuskymc.gadgetmod.client.theme;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thegaminghuskymc.gadgetmod.client.theme.data.MetadataSerializer;
import net.thegaminghuskymc.gadgetmod.client.theme.data.PackMetadataSection;
import net.thegaminghuskymc.gadgetmod.util.ThemeLocation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
public class ThemePackRepository {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final FileFilter RESOURCE_PACK_FILTER = p_accept_1_ -> {
        boolean flag = p_accept_1_.isFile() && p_accept_1_.getName().endsWith(".zip");
        boolean flag1 = p_accept_1_.isDirectory() && (new File(p_accept_1_, "pack.mcmeta")).isFile();
        return flag || flag1;
    };
    private static final Pattern SHA1 = Pattern.compile("^[a-fA-F0-9]{40}$");
    private static final ThemeLocation UNKNOWN_PACK_TEXTURE = new ThemeLocation("textures/misc/unknown_pack.png");
    public final IThemePack rprDefaultThemePack;
    public final MetadataSerializer rprMetadataSerializer;
    private final File dirThemePacks;
    private final File dirServerThemePacks;
    private final ReentrantLock lock = new ReentrantLock();
    private final List<ThemePackRepository.Entry> repositoryEntries = Lists.newArrayList();
    private IThemePack serverThemePack;
    /**
     * ResourcesPack currently beeing downloaded
     */
    private ListenableFuture<Object> downloadingPacks;
    private List<Entry> repositoryEntriesAll = Lists.newArrayList();

    public ThemePackRepository(File dirThemePacksIn, File dirServerThemePacksIn, IThemePack rprDefaultThemePackIn, MetadataSerializer rprMetadataSerializerIn, GameSettings settings) {
        this.dirThemePacks = dirThemePacksIn;
        this.dirServerThemePacks = dirServerThemePacksIn;
        this.rprDefaultThemePack = rprDefaultThemePackIn;
        this.rprMetadataSerializer = rprMetadataSerializerIn;
        this.fixDirThemePacks();
        this.updateRepositoryEntriesAll();
        Iterator<String> iterator = settings.resourcePacks.iterator();

        while (iterator.hasNext()) {
            String s = iterator.next();

            for (ThemePackRepository.Entry ThemePackrepository$entry : this.repositoryEntriesAll) {
                if (ThemePackrepository$entry.getThemePackName().equals(s)) {
                    if (ThemePackrepository$entry.getPackFormat() == 3 || settings.incompatibleResourcePacks.contains(ThemePackrepository$entry.getThemePackName())) {
                        this.repositoryEntries.add(ThemePackrepository$entry);
                        break;
                    }

                    iterator.remove();
                    LOGGER.warn("Removed selected theme pack {} because it's no longer compatible", ThemePackrepository$entry.getThemePackName());
                }
            }
        }
    }

    public static Map<String, String> getDownloadHeaders() {
        Map<String, String> map = Maps.newHashMap();
        map.put("X-Minecraft-Username", Minecraft.getMinecraft().getSession().getUsername());
        map.put("X-Minecraft-UUID", Minecraft.getMinecraft().getSession().getPlayerID());
        map.put("X-Minecraft-Version", "1.12.2");
        return map;
    }

    private void fixDirThemePacks() {
        if (this.dirThemePacks.exists()) {
            if (!this.dirThemePacks.isDirectory() && (!this.dirThemePacks.delete() || !this.dirThemePacks.mkdirs())) {
                LOGGER.warn("Unable to recreate themepack folder, it exists but is not a directory: {}", this.dirThemePacks);
            }
        } else if (!this.dirThemePacks.mkdirs()) {
            LOGGER.warn("Unable to create themepack folder: {}", this.dirThemePacks);
        }
    }

    private List<File> getThemePackFiles() {
        return this.dirThemePacks.isDirectory() ? Arrays.asList(this.dirThemePacks.listFiles(RESOURCE_PACK_FILTER)) : Collections.emptyList();
    }

    private IThemePack getThemePack(File p_191399_1_) {
        IThemePack iThemePack;

        if (p_191399_1_.isDirectory()) {
            iThemePack = new FolderThemePack(p_191399_1_);
        } else {
            iThemePack = new FileThemePack(p_191399_1_);
        }

        try {
            PackMetadataSection packmetadatasection = (PackMetadataSection) iThemePack.getPackMetadata(this.rprMetadataSerializer, "pack");

            if (packmetadatasection != null && packmetadatasection.getPackFormat() == 2) {
                return new LegacyV2Adapter(iThemePack);
            }
        } catch (Exception var4) {
            ;
        }

        return iThemePack;
    }

    public void updateRepositoryEntriesAll() {
        List<ThemePackRepository.Entry> list = Lists.<ThemePackRepository.Entry>newArrayList();

        for (File file1 : this.getThemePackFiles()) {
            ThemePackRepository.Entry ThemePackrepository$entry = new ThemePackRepository.Entry(file1);

            if (this.repositoryEntriesAll.contains(ThemePackrepository$entry)) {
                int i = this.repositoryEntriesAll.indexOf(ThemePackrepository$entry);

                if (i > -1 && i < this.repositoryEntriesAll.size()) {
                    list.add(this.repositoryEntriesAll.get(i));
                }
            } else {
                try {
                    ThemePackrepository$entry.updateThemePack();
                    list.add(ThemePackrepository$entry);
                } catch (Exception var6) {
                    list.remove(ThemePackrepository$entry);
                }
            }
        }

        this.repositoryEntriesAll.removeAll(list);

        for (ThemePackRepository.Entry ThemePackrepository$entry1 : this.repositoryEntriesAll) {
            ThemePackrepository$entry1.closeThemePack();
        }

        this.repositoryEntriesAll = list;
    }

    @Nullable
    public ThemePackRepository.Entry getThemePackEntry() {
        if (this.serverThemePack != null) {
            ThemePackRepository.Entry ThemePackrepository$entry = new ThemePackRepository.Entry(this.serverThemePack);

            try {
                ThemePackrepository$entry.updateThemePack();
                return ThemePackrepository$entry;
            } catch (IOException var3) {
                ;
            }
        }

        return null;
    }

    public List<ThemePackRepository.Entry> getRepositoryEntriesAll() {
        return ImmutableList.copyOf(this.repositoryEntriesAll);
    }

    public List<ThemePackRepository.Entry> getRepositoryEntries() {
        return ImmutableList.copyOf(this.repositoryEntries);
    }

    public void setRepositories(List<ThemePackRepository.Entry> repositories) {
        this.repositoryEntries.clear();
        this.repositoryEntries.addAll(repositories);
    }

    public File getDirThemePacks() {
        return this.dirThemePacks;
    }

    public ListenableFuture<Object> downloadThemePack(String url, String hash) {
        String s = DigestUtils.sha1Hex(url);
        final String s1 = SHA1.matcher(hash).matches() ? hash : "";
        final File file1 = new File(this.dirServerThemePacks, s);
        this.lock.lock();

        try {
            this.clearThemePack();

            if (file1.exists()) {
                if (this.checkHash(s1, file1)) {
                    ListenableFuture listenablefuture1 = this.setServerThemePack(file1);
                    return listenablefuture1;
                }

                LOGGER.warn("Deleting file {}", file1);
                FileUtils.deleteQuietly(file1);
            }

            this.deleteOldServerResourcesPacks();
            final GuiScreenWorking guiscreenworking = new GuiScreenWorking();
            Map<String, String> map = getDownloadHeaders();
            final Minecraft minecraft = Minecraft.getMinecraft();
            Futures.getUnchecked(minecraft.addScheduledTask(() -> minecraft.displayGuiScreen(guiscreenworking)));
            final SettableFuture<Object> settablefuture = SettableFuture.create();
            this.downloadingPacks = HttpUtil.downloadResourcePack(file1, url, map, 52428800, guiscreenworking, minecraft.getProxy());
            Futures.addCallback(this.downloadingPacks, new FutureCallback<Object>() {
                public void onSuccess(@Nullable Object p_onSuccess_1_) {
                    if (ThemePackRepository.this.checkHash(s1, file1)) {
                        ThemePackRepository.this.setServerThemePack(file1);
                        settablefuture.set(null);
                    } else {
                        ThemePackRepository.LOGGER.warn("Deleting file {}", (Object) file1);
                        FileUtils.deleteQuietly(file1);
                    }
                }

                public void onFailure(Throwable p_onFailure_1_) {
                    FileUtils.deleteQuietly(file1);
                    settablefuture.setException(p_onFailure_1_);
                }
            });
            ListenableFuture listenablefuture = this.downloadingPacks;
            return listenablefuture;
        } finally {
            this.lock.unlock();
        }
    }

    private boolean checkHash(String p_190113_1_, File p_190113_2_) {
        try {
            String s = DigestUtils.sha1Hex((InputStream) (new FileInputStream(p_190113_2_)));

            if (p_190113_1_.isEmpty()) {
                LOGGER.info("Found file {} without verification hash", (Object) p_190113_2_);
                return true;
            }

            if (s.toLowerCase(java.util.Locale.ROOT).equals(p_190113_1_.toLowerCase(java.util.Locale.ROOT))) {
                LOGGER.info("Found file {} matching requested hash {}", p_190113_2_, p_190113_1_);
                return true;
            }

            LOGGER.warn("File {} had wrong hash (expected {}, found {}).", p_190113_2_, p_190113_1_, s);
        } catch (IOException ioexception) {
            LOGGER.warn("File {} couldn't be hashed.", p_190113_2_, ioexception);
        }

        return false;
    }

    private boolean validatePack(File p_190112_1_) {
        ThemePackRepository.Entry ThemePackrepository$entry = new ThemePackRepository.Entry(p_190112_1_);

        try {
            ThemePackrepository$entry.updateThemePack();
            return true;
        } catch (Exception exception) {
            LOGGER.warn("Server themepack is invalid, ignoring it", exception);
            return false;
        }
    }

    /**
     * Keep only the 10 most recent resources packs, delete the others
     */
    private void deleteOldServerResourcesPacks() {
        try {
            List<File> list = Lists.newArrayList(FileUtils.listFiles(this.dirServerThemePacks, TrueFileFilter.TRUE, null));
            Collections.sort(list, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            int i = 0;

            for (File file1 : list) {
                if (i++ >= 10) {
                    LOGGER.info("Deleting old server theme pack {}", (Object) file1.getName());
                    FileUtils.deleteQuietly(file1);
                }
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            LOGGER.error("Error while deleting old server theme pack : {}", (Object) illegalargumentexception.getMessage());
        }
    }

    public ListenableFuture<Object> setServerThemePack(File resourceFile) {
        if (!this.validatePack(resourceFile)) {
            return Futures.<Object>immediateFailedFuture(new RuntimeException("Invalid themepack"));
        } else {
            this.serverThemePack = new FileThemePack(resourceFile);
            return Minecraft.getMinecraft().scheduleResourcesRefresh();
        }
    }

    /**
     * Getter for the IThemePack instance associated with this ThemePackRepository
     */
    @Nullable
    public IThemePack getServerThemePack() {
        return this.serverThemePack;
    }

    public void clearThemePack() {
        this.lock.lock();

        try {
            if (this.downloadingPacks != null) {
                this.downloadingPacks.cancel(true);
            }

            this.downloadingPacks = null;

            if (this.serverThemePack != null) {
                this.serverThemePack = null;
                Minecraft.getMinecraft().scheduleResourcesRefresh();
            }
        } finally {
            this.lock.unlock();
        }
    }

    @SideOnly(Side.CLIENT)
    public class Entry {
        private final IThemePack reThemePack;
        private PackMetadataSection rePackMetadataSection;
        private ThemeLocation locationTexturePackIcon;

        private Entry(File ThemePackFileIn) {
            this(ThemePackRepository.this.getThemePack(ThemePackFileIn));
        }

        private Entry(IThemePack reThemePackIn) {
            this.reThemePack = reThemePackIn;
        }

        public void updateThemePack() throws IOException {
            this.rePackMetadataSection = this.reThemePack.getPackMetadata(ThemePackRepository.this.rprMetadataSerializer, "pack");
            this.closeThemePack();
        }

        public void closeThemePack() {
            if (this.reThemePack instanceof Closeable) {
                IOUtils.closeQuietly((Closeable) this.reThemePack);
            }
        }

        public IThemePack getThemePack() {
            return this.reThemePack;
        }

        public String getThemePackName() {
            return this.reThemePack.getPackName();
        }

        public String getTexturePackDescription() {
            return this.rePackMetadataSection == null ? TextFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)" : this.rePackMetadataSection.getPackDescription().getFormattedText();
        }

        public int getPackFormat() {
            return this.rePackMetadataSection == null ? 0 : this.rePackMetadataSection.getPackFormat();
        }

        public boolean equals(Object p_equals_1_) {
            if (this == p_equals_1_) {
                return true;
            } else {
                return p_equals_1_ instanceof ThemePackRepository.Entry ? this.toString().equals(p_equals_1_.toString()) : false;
            }
        }

        public int hashCode() {
            return this.toString().hashCode();
        }

        public String toString() {
            return String.format("%s:%s", this.reThemePack.getPackName(), this.reThemePack instanceof FolderThemePack ? "folder" : "zip");
        }
    }
}