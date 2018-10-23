package com.k2.common.model.aModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.io.Files;
import com.k2.Util.FileUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.common.annotation.MetaComponent;

@MetaComponent(id=2)
public abstract class AK2Component {

	public abstract String getName();
	
	public String getPackageName() {
		return ClassUtil.getPackageNameFromCanonicalName(getName());
	}
	
	public String getSimpleName() {
		return ClassUtil.getBasenameFromCanonicalName(getName());
	}
	
	public Path getPackagePath() {
		
		String[] nodes = getPackageName().split("\\.");			
		Path path = Paths.get(nodes[0]);
		for (int i=1; i<nodes.length; i++) 
			path = path.resolve(nodes[i]);
		
		return path;
	}
	
	public String getSourceFileName() {
		return getSimpleName()+".java";
	}
	
	public String getByteCodeFileName() {
		return getSimpleName()+".class";
	}
	
	public Path getSourceFile(Path repo) {
		return repo.resolve(getPackagePath()).resolve(getSourceFileName());
	}
	
	public File getSourceFile(File repo) {
		
		return getSourceFile(repo.toPath()).toFile();
	}
	
	public Writer getSourceFileWriter(File repo) throws IOException {
		
		File sourceFile = getSourceFile(repo);
		if (! sourceFile.exists()) {
			Files.createParentDirs(sourceFile);
			sourceFile.createNewFile();
		}
		return new FileWriter(sourceFile);
	}
	
}
