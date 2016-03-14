package model;

import java.util.List;
import java.io.Serializable;

public class FilesMeta implements Serializable {

	private static final long serialVersionUID = -8402249023703714182L;

	private List<FileMeta> files;

	public List<FileMeta> getFiles() {
		return files;
	}

	public void setFiles(List<FileMeta> files) {
		this.files = files;
	}
	
}
