public class Node {
	private Node left, right, parent;
	private int type;
	private int subType; // 0 means Int, 1 means String
	private int nat;
	private String value;
	private String result;
	private boolean isList = false;
	private boolean printList = false;

	public Node(int type, String value) {
		this.type = type;
		this.value = value;
		left = right = parent = null;
		if (type == 4) {
			if (isInteger(value)) {
				this.subType = 0;
			}
			else {
				this.subType = 1;
			}
		}
		else {
			this.subType = -1;
		}
	}

	public Node(int type) {
		this.type = type;
		this.subType = -1;
		left = right = parent = null;
		value = "";
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

	public boolean getPrintList() {
		return printList;
	}

  public void setPrintList(boolean printList) {
		this.printList = printList;
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

	public int getNat() {
		return nat;
	}
	public void setNat(int nat) {
		this.nat = nat;
	}
}
