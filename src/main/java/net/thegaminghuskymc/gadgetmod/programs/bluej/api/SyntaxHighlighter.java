package net.thegaminghuskymc.gadgetmod.programs.bluej.api;

import net.thegaminghuskymc.gadgetmod.programs.bluej.Project;
import net.thegaminghuskymc.gadgetmod.programs.bluej.ProjectFile;

import java.util.List;

public interface SyntaxHighlighter {

    public String getName();

    public List<List<Token>> parse(Project project, ProjectFile currentFile);

    public List<Problem> getProblems(ProjectFile file);

    public void reset();

}
