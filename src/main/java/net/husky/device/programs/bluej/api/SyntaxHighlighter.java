package net.husky.device.programs.bluej.api;

import java.util.List;

import net.husky.device.programs.bluej.Project;
import net.husky.device.programs.bluej.ProjectFile;

public interface SyntaxHighlighter {
	
	public String getName();
	public List<List<Token>> parse(Project project, ProjectFile currentFile);
	public List<Problem> getProblems(ProjectFile file);
	public void reset();
	
}
