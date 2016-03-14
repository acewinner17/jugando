package model;

import java.io.Serializable;
import java.util.List;

public class UploadesFiles implements Serializable {

	private static final long serialVersionUID = -8276333516365745883L;
	
	private List<UploadedFile> files;

	public List<UploadedFile> getFiles() {
		return files;
	}

	public void setFiles(List<UploadedFile> files) {
		this.files = files;
	}
	
	
}
