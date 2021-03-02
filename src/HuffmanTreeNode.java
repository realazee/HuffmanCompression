import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * The Class HuffmanTreeNode. 
 */
public class HuffmanTreeNode {

	/** The weight. */
	private int weight;

	/** The ord value. */
	private int ordValue;

	/** The char value. */
	private char charValue;

	/** The left. */
	private HuffmanTreeNode left;

	/** The right. */
	private HuffmanTreeNode right;
	private static int ID = 0;
	private int id;
	

	/**
	 * Instantiates a new huffman tree node. This constructor is used to generate unconnected leaf 
	 * nodes.
	 *
	 * @param ordValue the ord value (the integer value of the character)
	 * @param weight the weight (the frequency of occurrence of the character)
	 */
	public HuffmanTreeNode(int ordValue, int weight) {
		this.weight = weight;
		this.ordValue = ordValue;
		this.charValue = (char) ordValue;
		left = null;
		right = null;
		id = ID;
		ID++;
	}

	/**
	 * Instantiates a new huffman tree node. This constructor is used to connect two nodes
	 * together, which may or may not be leaf nodes. This node will have two nodes - left and right.
	 * The weight of this node is the sum of the weights of the left and right nodes. Since this node
	 * is not a leaf node, the ordValue is -1 and the charValue is 0;
	 *
	 * @param weight the weight
	 * @param left the left
	 * @param right the right
	 */
	public HuffmanTreeNode(int weight, HuffmanTreeNode left, HuffmanTreeNode right) {
		this.weight = weight;
		this.ordValue = -1;
		this.charValue = 0;
		this.left = left;
		this.right = right;
		id = ID;
		ID++;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId() {
		this.id = id;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * Gets the ord value.
	 *
	 * @return the ord value
	 */
	public int getOrdValue() {
		return ordValue;
	}

	/**
	 * Sets the ord value.
	 *
	 * @param ordValue the new ord value
	 */
	public void setOrdValue(int ordValue) {
		this.ordValue = ordValue;
	}

	/**
	 * Gets the char value.
	 *
	 * @return the char value
	 */
	public char getCharValue() {
		return charValue;
	}

	/**
	 * Sets the char value.
	 *
	 * @param charValue the new char value
	 */
	public void setCharValue(char charValue) {
		this.charValue = charValue;
	}

	/**
	 * Gets the left.
	 *
	 * @return the left
	 */
	public HuffmanTreeNode getLeft() {
		return left;
	}

	/**
	 * Sets the left.
	 *
	 * @param left the new left
	 */
	public void setLeft(HuffmanTreeNode left) {
		this.left = left;
	}

	/**
	 * Gets the right.
	 *
	 * @return the right
	 */
	public HuffmanTreeNode getRight() {
		return right;
	}

	/**
	 * Sets the right.
	 *
	 * @param right the new right
	 */
	public void setRight(HuffmanTreeNode right) {
		this.right = right;
	}

	/**
	 * Checks if is leaf.
	 *
	 * @return true, if is leaf
	 */
	public boolean isLeaf() {
		return(ordValue != -1);
	}

	/** The comparator used to sort nodes by weight then by ordinal value. */
	public static Comparator<HuffmanTreeNode> compareWeightOrd = new Comparator<HuffmanTreeNode>() {
		@Override
		public int compare(HuffmanTreeNode ht1, HuffmanTreeNode ht2) {
			if(ht1.weight < ht2.weight) {
				return -1;
			}
			else if(ht1.weight > ht2.weight) {
				return 1;
			}
			else if(ht1.weight == ht2.weight){
				if(ht1.ordValue < ht2.ordValue){
					return -1;
				}
				else if(ht1.ordValue > ht2.ordValue) {
					return 1;
				}
				else {
					if(ht1.id > ht2.id) {
						return 1;
					}
					else {
						return -1;
					}
				}
			}
			return 0;
			
		}
	};

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ord = " + this.ordValue + "   weight = " + this.weight;
	}

}
