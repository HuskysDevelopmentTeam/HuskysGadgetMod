package net.thegaminghuskymc.gadgetmod.programs.bluej.api;

import net.husky.device.programs.bluej.Project;
import net.husky.device.programs.bluej.ProjectFile;

import java.util.List;

public interface SyntaxHighlighter {

    public String getName();

    public List<List<Token>> parse(Project project, ProjectFile currentFile);

    public List<Problem> getProblems(ProjectFile file);

    public void reset();

}
