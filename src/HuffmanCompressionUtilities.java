import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class HuffmanCompressionUtilities.
 */
public class HuffmanCompressionUtilities {
	
	/** The queue. */
	private PriorityQueue<HuffmanTreeNode> queue;
	
	/** The root. */
	private HuffmanTreeNode root;
	
	/** The encode map - this will map a character to the bit string that will replace it */
	private String[] encodeMap;
	
	/** The str. This is used to print the tree structure for testing purposes */
	private String str;
	
	/** The frequency weights. */
	private int[] weights;

	/**
	 * Instantiates a new huffman compression utilities.
	 */
	public HuffmanCompressionUtilities() {
		queue = new PriorityQueue<HuffmanTreeNode>(root.compareWeightOrd);  //Not sure if this is correct.
		root = null;
		encodeMap = new String[8];
		str = "";
		weights = new int[128];
		
	}
	
	/**
	 * Gets the tree root.
	 *
	 * @return the tree root
	 */
	public HuffmanTreeNode getTreeRoot() {
		return root;
	}
	
	/**
	 * Gets the encode map.
	 *
	 * @return the encode map
	 */
	public String[] getEncodeMap() {
		return encodeMap;
	}
	void dumpQueue(String msg) {
		   System.out.println(msg);
		   HuffmanTreeNode node;
		   Queue<HuffmanTreeNode> saveQ = new LinkedList<HuffmanTreeNode>();
		   while (!queue.isEmpty()) {
		      node = queue.remove();
		      saveQ.add(node);
		      System.out.println("   wt:"+node.getWeight()+"  ord=" +node.getOrdValue()+"  id="+node.getId());
		   }
		   while (!saveQ.isEmpty())
		       queue.add(saveQ.remove());
		}
	/**
	 * Read freq weights from a file in the output/ directory
	 * You can assume that this file has already been error checked.
	 *
	 * @param inf the inf
	 * @return the int[]
	 */
	
	public int[] readFreqWeights(File inf) {
		File outputFile = new File("output/" + inf);
        FileReader reader = null;
        try {
            reader = new FileReader(outputFile);
        } catch (FileNotFoundException e3) {
            return null;
        }
        BufferedReader br = new BufferedReader(reader);
        for(int i = 0; i < 128; i++) {
        	try {
        		String temp = br.readLine();
        		
        		try {
        			int weight = Integer.parseInt(temp.substring(temp.indexOf(",")+i));
        			weights[i] = weight;
        		}
        		catch(NumberFormatException e) {
        			return null;
        		}
        	}
        	catch(IOException e) {
        		break;
        	}
        }
		return weights; 
	}			

	/**
	 * Initialize huffman queue from the weights array
	 *
	 * @param minimize - when true, only add:
	 *     indexes with non-zero weights to the queue
	 *     index 0 (the EOF character) to the queue
	 */
	void initializeHuffmanQueue(boolean minimize) {
		for(int i = 0; i < weights.length; i++) {
			if(!minimize) {
				queue.add(new HuffmanTreeNode(i, weights[i]));
			}
			else {
				if(weights[i] != 0) {
					queue.add(new HuffmanTreeNode(i, weights[i]));
				}
			}
		}
	}
	
	/**
	 * Sets the weights.
	 *
	 * @param weights the new weights
	 */
	public void setWeights(int[] weights) {
		weights = this.weights;
	
	}
	
	/**
	 * Builds the huffman tree. Make sure to:
	 * 1) initialize root to null (cleanup any prior conversions)
	 * 2) re-initialize the encodeMap
	 * 3) initialize the queue
	 * 4) build the tree:
	 *    while the queue is not empty:
	 *       pop the head of the queue into the left HuffmanTreeNode.
	 *       if the queue is empty - set root = left, and return;
	 *       pop the head of the queue into the right HuffmanTreeNode
	 *       create a new non-leaf HuffmanTreeNode whose children are left and right,
	 *       and whose weight is the sum of the weight of the left and right children
	 *       add the new node back to the queue.
	 * 
	 * It is assumed that the weights have been initialized prior
	 * to calling this method.
	 *
	 * @param minimize - This is just passed to the method to initialize the queue.
	 */
	public void buildHuffmanTree(boolean minimize) {
		HuffmanTreeNode left, right;
		root = null;
		encodeMap = new String[128];
	
		initializeHuffmanQueue(minimize);
		dumpQueue("Dumping the Queue");
		while(queue.isEmpty() == false) {
			left = queue.remove();
			if(queue.isEmpty()) {
				root = left;
				return;
			}
			right = queue.remove();
			//create and add to queue now
			int tempWeight= left.getWeight() + right.getWeight();
			queue.add(new HuffmanTreeNode(tempWeight, left, right));
		}
		
	}
	
	/**
	 * Prints the node info for debugging purposes
	 *
	 * @param level the level
	 * @param ord the ord
	 * @param aChar the a char
	 * @param code the code
	 */
	private void printNodeInfo(int level, int ord, char aChar, String code) {
		if (ord <32 ) {
			System.out.println("Level: "+level+ "   Ord: "+ord+"[ ] = "+code);
		} else {
			System.out.println("Level: "+level+ "   Ord: "+ord+"("+aChar+") = "+code);
		}
		
	}
	
	/**
	 * Creates the huffman codes. Starting at the root node, recursively traverse the tree to create 
	 * the code. Moving to a left child adds "0" to the code, moving to the right child adds "1".
	 * If the node is a leaf, then set the appropriate entree in the encodeMap to the accumulated 
	 * code. You should never encounter a null pointer in this process... but good to check..
	 *
	 * @param node the node
	 * @param code the code
	 * @param level the level
	 */
	public void createHuffmanCodes(HuffmanTreeNode node, String code, int level) {
		encodeMap = new String[128];
		if(root != null) {
			recursiveCreate(root, "", 0);
		}
	}
	public void recursiveCreate(HuffmanTreeNode node, String code, int level) {
		if(node.isLeaf()) { //base case
			encodeMap[node.getOrdValue()] = code;
			
		}
		if(node.getLeft() != null) {
			recursiveCreate(node.getLeft(), code+"0", level+1);
		}
		if(node.getLeft() != null) {
			recursiveCreate(node.getRight(), code+"1", level+1);
		}
		
	}
	
	/**
	 * Prints the huffman tree. for debugging purposes...
	 *
	 * @param root the root
	 * @param level the level
	 */
	public void printHuffmanTree(HuffmanTreeNode root, int level) {
		if (root == null) {
			return;
		} 
		
		if (level == 0) {
			str = "";
		}
		
		if (root.isLeaf()) {
			if (root.getOrdValue() < 32) {
				str += level+"l"+root.getOrdValue();
			} else {
				str += level +"L"+root.getCharValue();
			}
		} else {
			str += level+"N";
 
			if ((root.getLeft() == null) && (root.getRight() == null)) {
				return;
			}
		
			str += ('(');
		    printHuffmanTree(root.getLeft(),level+1);
		    str += ')';
		    
		    if (root.getRight() != null) {
		    	str += ('(');
		    	printHuffmanTree(root.getRight(),level+1);
		    	str += (')');
		    }
		
		}
		
	}
	
	/**
	 * Traverse tree, based upon the passing in binary String. Note that
	 * a String[] is used so that the code can manipulate the string. 
	 * 
	 * The algorithm recursively traverses the tree based on the sequence of bits 
	 * until either a leaf node is encountered and the char is returned or the string of bits
	 * has been consumed without finding a character (returns -1);
	 *
	 * @param root the root
	 * @param binStr the bin str
	 * @return the byte
	 */
	private byte traverseTree(HuffmanTreeNode root, String[] binStr) {
		//TODO - write this method
		return -1; // remove when completed.
	}
	
	/**
	 * Decode string.
	 * Algorithm:
	 *  If the input string is empty, return -1
	 *  Save a copy of the binary string
	 *  Traverse the tree with the binary string
	 *  If no character found, restore the binary string from the copy
	 *  Return the decoded character if found, -1 if not
	 *
	 * @param binStr the bin str
	 * @return the byte
	 */
	public byte decodeString(String[] binStr) {
		//TODO - write this method
		if(binStr.length == 0) {
			return -1;
		}
		String[] backup = binStr;
		byte result = traverseTree(root, binStr);
		if(result == -1) {
			binStr = backup;
			return -1;
		}
		else {
			return result;
		}
		
	}
		
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return str;
	}
} 
