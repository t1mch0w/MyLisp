public class Node {
	private Node left, right, parent;
	private int type;
	private int subType; // 0 means Int, 1 means String
	private String value;
	private String result;
	private boolean isList = false;

	public Node(int type, String value) {
		this.type = type;
		this.value = value;
		left = right = parent = null;
		if (type == 4) {
			if (isInteger(value)) {
				subType = 0;
			}
			else {
				subType = 1;
			}
		}
	}

	public Node(int type) {
		this.type = type;
		left = right = parent = null;
	}

	public void setLeft(Node n) {
		this.left = n;
	}

	public void setRight(Node n) {
		this.right = n;
	}

	public void setParent(Node n) {
		this.parent = n;
	}

	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}

	public int getType() {
		return type;
	}

	public int getIntValue() {
		return Integer.parseInt(value);
	}

	public String getValue() {
		return value;
	}

	public boolean getList() {
		return isList;
	}

  public void setList(boolean isList) {
		this.isList = isList;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public boolean hasLeft() {
		if (left == null) return false;
		return true;
	}

	public boolean hasRight() {
		if (right == null) return false;
		return true;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}
	
	public int getSubType() {
		return subType;
	}
	public boolean isInteger( String input ) {
		try {
			Integer.parseInt( input );
			return true;
			}
			catch( Exception e ) {
				return false;
				}
		}
}
