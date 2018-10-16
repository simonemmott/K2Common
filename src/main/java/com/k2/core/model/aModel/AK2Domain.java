package com.k2.core.model.aModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.common.io.Files;
import com.k2.Util.StringUtil;
import com.k2.Util.classes.ClassUtil;
import com.k2.common.annotation.DomainManager;
import com.k2.common.annotation.DomainManagerAware;
import com.k2.common.annotation.MetaComponent;
import com.k2.common.domain.K2DomainManager;
import com.k2.core.K2CoreDomainManager;
import com.k2.core.model.K2Component;

@DomainManagerAware
@MetaComponent()
public abstract class AK2Domain {

	public abstract String getName();
	public abstract String getRootPackageName();
	
	public abstract List<K2Component> getDomainComponents();
	
	public void generateSource(String repoPath) throws IOException {	
		if (domainManager instanceof K2CoreDomainManager)
			((K2CoreDomainManager)domainManager).generateSource(repoPath);	
	}
	
	@DomainManager
	private static K2DomainManager domainManager;
	
	public static void setDomainManager(K2DomainManager dm) {
		domainManager = dm;
	}
	
	public K2DomainManager getDomainManager() { return domainManager; }
	
	public Path getRootPackagePath() {
		return ClassUtil.getPackageNameToJavaPath(getRootPackageName());
	}
	public String getSequencesClassName() {
		return getRootPackageName()+"."+getSequencesClassSimpleName();
	}

	public String getSequencesClassSimpleName() {
		return StringUtil.classCase(getName())+"Sequences";
	}

	public String getSequenceSourceFileName() {
		return getSequencesClassSimpleName()+".java";
	}
	
	public String getSequenceByteCodeFileName() {
		return getSequencesClassSimpleName()+".class";
	}
	
	public Path getSequenceSourceFile(Path repo) {
		return repo.resolve(getRootPackagePath()).resolve(getSequenceSourceFileName());
	}
	
	public File getSequenceSourceFile(File repo) {
		
		return getSequenceSourceFile(repo.toPath()).toFile();
	}
	
	public Writer getSequenceFileWriter(File repo) throws IOException {
		
		File sourceFile = getSequenceSourceFile(repo);
		if (! sourceFile.exists()) {
			Files.createParentDirs(sourceFile);
			sourceFile.createNewFile();
		}
		return new FileWriter(sourceFile);
	}

	
	
}
