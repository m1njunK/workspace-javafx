package c3_view_controls;

public class PhoneVO {
	
	private String name;
	private String path;
	
	public PhoneVO() {}

	public PhoneVO(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "PhoneVO [name=" + name + ", path=" + path + "]";
	}
	
	
}
