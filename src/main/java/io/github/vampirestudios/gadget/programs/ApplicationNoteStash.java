package io.github.vampirestudios.gadget.programs;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import io.github.vampirestudios.gadget.api.app.Application;
import io.github.vampirestudios.gadget.api.app.Dialog;
import io.github.vampirestudios.gadget.api.app.Layout;
import io.github.vampirestudios.gadget.api.app.annontation.DeviceApplication;
import io.github.vampirestudios.gadget.api.app.component.*;
import io.github.vampirestudios.gadget.api.app.emojie_packs.Icons;
import io.github.vampirestudios.gadget.api.io.File;
import io.github.vampirestudios.gadget.core.io.FileSystem;

import javax.annotation.Nullable;
import java.util.function.Predicate;

import static io.github.vampirestudios.gadget.Reference.MOD_ID;

@DeviceApplication(modId = MOD_ID, appId = "note_stash")
public class ApplicationNoteStash extends Application {

    private static final Predicate<File> PREDICATE_FILE_NOTE = file -> !file.isFolder()
            && file.getData().hasKey("title", Constants.NBT.TAG_STRING)
            && file.getData().hasKey("content", Constants.NBT.TAG_STRING);

    /* Main */
    private Layout layoutHistory;
    private ItemList<Note> notes;
    private Button btnNew;
    private Button btnShow;
    private Button btnRemove;

    /* Add Note */
    private Layout layoutCreateNote;
    private TextField title;
    private TextArea textArea;
    private Button btnSave;
    private Button btnHistory;

    /* View Note */
    private Layout layoutShowNote;
    private Label noteTitle;
    private Text noteContent;
    private Button btnBack;

    @Override
    public void init(@Nullable NBTTagCompound intent) {
        /* Main */

        layoutHistory = new Layout(180, 80);
        layoutHistory.setInitListener(() ->
        {
            notes.getItems().clear();
            FileSystem.getApplicationFolder(this, (folder, success) ->
            {
                if (success) {
                    folder.search(file -> file.isForApplication(this)).forEach(file ->
                    {
                        notes.addItem(Note.fromFile(file));
                    });
                } else {
                    //TODO error dialog
                }
            });
        });

        notes = new ItemList<>(5, 5, 100, 5);
        notes.setItemClickListener((e, index, mouseButton) ->
        {
            btnShow.setEnabled(true);
            btnRemove.setEnabled(true);
        });
        layoutHistory.addComponent(notes);

        btnNew = new Button(124, 5, "Create");
        btnNew.setSize(50, 20);
        btnNew.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutCreateNote));
        layoutHistory.addComponent(btnNew);

        btnShow = new Button(124, 30, "Show");
        btnShow.setSize(50, 20);
        btnShow.setEnabled(false);
        btnShow.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (notes.getSelectedIndex() != -1) {
                Note note = notes.getSelectedItem();
                noteTitle.setText(note.getTitle());
                noteContent.setText(note.getContent());
                setCurrentLayout(layoutShowNote);
            }
        });
        layoutHistory.addComponent(btnShow);

        btnRemove = new Button(124, 55, "Remove");
        btnRemove.setSize(50, 20);
        btnRemove.setEnabled(false);
        btnRemove.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (notes.getSelectedIndex() != -1) {
                if (notes.getSelectedIndex() != -1) {
                    Note note = notes.getSelectedItem();
                    File file = note.getSource();
                    if (file != null) {
                        file.delete((o, success) ->
                        {
                            if (success) {
                                notes.removeItem(notes.getSelectedIndex());
                                btnShow.setEnabled(false);
                                btnRemove.setEnabled(false);
                            } else {
                                //TODO error dialog
                            }
                        });
                    } else {
                        //TODO error dialog
                    }
                }
            }
        });
        layoutHistory.addComponent(btnRemove);


        /* Add Note */

        layoutCreateNote = new Layout(195, 130);

		/*title = new TextField(5, 5, 114);
		layoutCreateNote.addComponent(title);*/

        textArea = new TextArea(5, 5, 135, 100);
        textArea.setFocused(true);
        textArea.setPadding(2);
        layoutCreateNote.addComponent(textArea);

        btnSave = new Button(175, 110, Icons.SAVE);
        btnSave.setToolTip("Save", "Saves this file");
        btnSave.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            NBTTagCompound data = new NBTTagCompound();
            data.setString("content", textArea.getText());

            Dialog.SaveFile dialog = new Dialog.SaveFile(ApplicationNoteStash.this, data);
            dialog.setFolder(getApplicationFolderPath());
            dialog.setResponseHandler((success, file) ->
            {
                textArea.clear();
                setCurrentLayout(layoutHistory);
                return true;
            });
            openDialog(dialog);
        });
        layoutCreateNote.addComponent(btnSave);

        btnHistory = new Button(158, 110, Icons.CLOCK);
        btnHistory.setToolTip("History", "Look on other older edited notes");
        btnHistory.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            textArea.clear();
            setCurrentLayout(layoutHistory);
        });
        layoutCreateNote.addComponent(btnHistory);


        /* Show Note */
        layoutShowNote = new Layout(180, 80);

        noteTitle = new Label("", 5, 5);
        layoutShowNote.addComponent(noteTitle);

        noteContent = new Text("", 5, 18, 110);
        layoutShowNote.addComponent(noteContent);

        btnBack = new Button(124, 5, "Back");
        btnBack.setSize(50, 20);
        btnBack.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutHistory));
        layoutShowNote.addComponent(btnBack);

        setCurrentLayout(layoutCreateNote);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {
    }

    @Override
    public void save(NBTTagCompound tagCompound) {
    }

    @Override
    public void onClose() {
        super.onClose();
        notes.removeAll();
    }

    @Override
    public boolean handleFile(File file) {
        if (!PREDICATE_FILE_NOTE.test(file))
            return false;

        NBTTagCompound data = file.getData();
        noteTitle.setText(data.getString("title"));
        textArea.setText(data.getString("content"));
        setCurrentLayout(layoutCreateNote);
        return true;
    }

    private static class Note {
        private File source;
        private String title;
        private String content;

        public Note(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public static Note fromFile(File file) {
            Note note = new Note(file.getData().getString("title"), file.getData().getString("content"));
            note.source = file;
            return note;
        }

        public File getSource() {
            return source;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
